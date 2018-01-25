# BboxApi Router client library #

[![Build Status](https://travis-ci.org/bertrandmartel/bboxapi-router.svg)](https://travis-ci.org/bertrandmartel/bboxapi-router)
[![Download](https://api.bintray.com/packages/bertrandmartel/maven/bboxapi-router/images/download.svg) ](https://bintray.com/bertrandmartel/maven/bboxapi-router/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.bmartel/bboxapi-router/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.bmartel/bboxapi-router)
[![Javadoc](http://javadoc-badge.appspot.com/fr.bmartel/bboxapi-router.svg?label=javadoc)](http://javadoc-badge.appspot.com/fr.bmartel/bboxapi-router)
[![License](http://img.shields.io/:license-mit-blue.svg)](LICENSE.md)

Java/Android client library for [Bbox Router API](https://api.bbox.fr/doc/apirouter/index.html)

These APIs are used by Bbox management interface on : http://bbox.lan

## List of API implemented

| description     | api          |       
|--------------|---------|
| get information about Bbox | [`getDeviceSummary()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/Summary.java) |
| get voip data | [`getVoipData()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/Voip.java) |   
| get specific information about box | [`getDeviceInfo()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/DeviceInfo.java) | 
| get call log  | [`getCallLog()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/CallLog.java) |
| get last 5 call log  | [`getLastCallLog()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/LastCallLog.java) |
| get list of known hosts  | [`getHosts()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/Hosts.java) |  
| set wifi state | [`setWifiState(boolean state)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/Wifi.java) |  
| set led state | [`setBboxDisplayState(boolean state)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/Display.java) | 
| dial a phone number | [`voipDial(int lineNumber, String phone)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/Dial.java) | 
| get wireless info | [`getWirelessData()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/Wireless.java) |
| reboot | [`reboot()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/Reboot.java) |
| get XDSL information | [`getXdslInfo()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/WanXdsl.java) | 
| get info about wan type, state and ip  | [`getIpInfo()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/WanIp.java) | 
| get consumption info (invoice) | [`getConsumptionData()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/Consumption.java) | 
| get voice mail | [`getVoiceMailData()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/VoiceMail.java) | 
| delete voice mail | [`deleteVoiceMail(int id)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/VoiceMailDelete.java) | 
| read voice mail | [`readVoiceMail(int id)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/VoiceMailRead.java) | 
| get wifi mac filters | [`getWifiMacFilterInfo()`](./examples/src/main/java/fr/bmartel/bboxapi/examples/request/WirelessAclInfo.java) |
| delete wifi mac filter | [`deleteMacFilterRule(int id)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/WifiMacFilteringRuleRemove.java) | 
| create wifi mac filter | [`createWifiMacFilterRule(boolean state,String mac,String ip)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/WifiMacFilteringCreate.java) |
| enable/disable wifi mac filter | [`setWifiMacFilter(boolean state)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/WifiMacFilteringEnable.java) |
| update wifi mac filter | [`updateWifiMacFilterRule(int id,boolean state,String mac,String ip)`](./examples/src/main/java/fr/bmartel/bboxapi/examples/action/WifiMacFilteringUpdate.java) |

All APIs need authentication via `setPassword(String password)` except for the following (some info may be missing when unauthenticated) : 

* get summary
* get XDSL
* get hosts
* get wan ip info
* get device info

The following type of API needs a special token be refresh with `refreshProfile(RefreshAction type)` : 

| API type     | refresh action type          | refresh flow example     
|--------------|---------|-------------------------------|
| voicemail | `RefreshAction.VOICEMAIL` |  [voicemail refresh flow](./examples/src/main/java/fr/bmartel/bboxapi/examples/refresh/VoiceMailRefresh.java) |
| call log | `RefreshAction.CALL_LOG` | [call log refresh flow](./examples/src/main/java/fr/bmartel/bboxapi/examples/refresh/CallLogRefresh.java) |
| consumption | `RefreshAction.ALL` | [consumption refresh flow](./examples/src/main/java/fr/bmartel/bboxapi/examples/refresh/ConsumptionRefresh.java) |

This refresh flow consists in polling `getConsumptionData()` and check for `getConsumptionData().getProfileList().get(0).getProfile().getState()`. If this field returns a non zero value, the refresh process is not finished. When the state reaches 0, the referenced API bound to the refresh action type are up-to-date.

Note that `RefreshAction.ALL` will refresh voicemail, call log and consumption (there is no refresh action for refreshing only consumption api type). 

## Include into your project

* with Gradle, from JCenter or MavenCentral :

```java
compile 'fr.bmartel:bboxapi-router:1.61.3'
```

## Usage

### Summary 

Retrieve some basic information about Bbox :

```java
BboxApi api = new BboxApi();

SummaryResponse summaryResponse = api.getDeviceSummary();
```

This API is public (doesn't require authentication), for more precise info see Device Info API

## Device info

Get more information about Bbox : 

```java
BboxApi api = new BboxApi();

api.setPassword("password");

DeviceInfoResponse deviceInfoResponse = api.getDeviceInfo();
```

## Hosts

Get list of all hosts known by Bbox :

```java
BboxApi api = new BboxApi();

api.setPassword("password");

HostsResponse hostResponse = api.getHosts();
```

## Wireless

Get information about wireless :

```java
BboxApi api = new BboxApi();

api.setPassword("password");

WirelessResponse wirelessResponse = api.getWirelessData();
```

## Voip

Get voip information :

```java
BboxApi api = new BboxApi();

api.setPassword("password");

VoipResponse voipResponse = api.getVoipData();
```

## Call Log list

Get full list of call log since last reboot :

```java
BboxApi api = new BboxApi();

api.setPassword("password");

CallLogResponse callLogResponse = api.getCallLog();
```

## Last 5 Call Log

```java
BboxApi api = new BboxApi();

api.setPassword("password");

CallLogResponse callLogResponse = api.getLastCallLog();
```

## Dial a phone number

Dial a specified phone number. Phone will ring and call will be processed once user hookoff

```java
BboxApi api = new BboxApi();

api.setPassword("password");

api.voipDial(1, "0123456789");
```

Input  :

* line number (int) : 1 or 2 according to the line on which you plugged your phone
* phone number (String) : number to call

## Set Bbox led state

Switch led display to ON / OFF on Bbox 

```java
BboxApi api = new BboxApi();

api.setPassword("password");

// turn off led display
api.setBboxDisplayState(false);

// turn on led display
api.setBboxDisplayState(true);
```

## Set Wifi state

Switch Wifi to ON/OFF 

```java
BboxApi api = new BboxApi();

api.setPassword("password");

// turn on Wifi
api.setWifiState(true);

// turn off Wifi
api.setWifiState(false);
```

## Reboot Bbox

```java
BboxApi api = new BboxApi();

api.setPassword("password");

// reboot Bbox
api.reboot();
```

## Get XDSL information

```java
BboxApi api = new BboxApi();

WanXdslResponse xdslResponse = api.getXdslInfo();
```

## Android integration

* add `bboxapi-router` dependency to `build.gradle` :

```java
compile 'fr.bmartel:bboxapi-router:1.61.3'
```

* add Internet permission to manifest :

```java
<uses-permission android:name="android.permission.INTERNET" />
```

* use an AsyncTask to call Bbox Router API :

```java
public class BboxApiTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
        try {
            BboxApi api = new BboxApi();
            SummaryResponse summaryResponse = api.getDeviceSummary(false);

            if (summaryResponse.getStatus() == HttpStatus.OK) {
                // print summary JSON result (deserialized)
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.setPrettyPrinting().create();
                Type listOfTestObject = new TypeToken<List<ApiSummary>>() {
                }.getType();
                String summary = gson.toJson(summaryResponse.getSummary(), listOfTestObject);

                Log.v("bboxapi", summary);
            } else {
                Log.e("bboxapi", "http error  : " + summaryResponse.getStatus());
            }
        } catch (IOException e) {
            Log.e("bboxapi", Log.getStackTraceString(e));
        }
        return null;
    }
}
```

Execute it with : `new BboxApiTask().execute();`

* proguard config (keep model & response packages) :

```
-keep class fr.bmartel.bboxapi.model.** { *; }
-keep class fr.bmartel.bboxapi.response.** { *; }
```

## Examples

The following examples will prompt user for password and execute specified request (except for summary which doesnt require authentication) :

* Call log :
```
./gradlew callLog
```
* Device info :
```
./gradlew deviceInfo
```
* Dial phone number (phone number will be asked) :
```
./gradlew dial
```
* Toggle ON/OFF led display for 10sec :
```
./gradlew display
```
* list hosts :
```
./gradlew hosts
```
* logout :
```
./gradlew logout
```
* summary request :
```
./gradlew summary
```
* voip request :
```
./gradlew voip
```
* set wifi to ON :
```
./gradlew wifi
```
* request wireless info :
```
./gradlew wireless
```

You can test Bbox API with a Linux Bash script ``bboxapi-curl.sh`` script performing authentication, request voip data and dial a number.

Usage :
```
./bboxapi-curl.sh <your_password> <phone_number>
```

## Issues

If you can't reach http://bbox.lan interface (see picture below) on your local network (Bbox), it means your box doesn't have the latest version and you can't use these APIs right now

![interface](https://user-images.githubusercontent.com/5183022/35354989-30c43cc4-014c-11e8-8258-34b090c81610.png)

## External Library

* [GSON](https://github.com/google/gson)

## API documentation

https://api.bbox.fr/doc/apirouter/index.html

## License

The MIT License (MIT) Copyright (c) 2017-2018 Bertrand Martel
