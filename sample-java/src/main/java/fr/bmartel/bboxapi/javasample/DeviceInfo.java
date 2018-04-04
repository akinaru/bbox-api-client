package fr.bmartel.bboxapi.javasample;

import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.github.kittinunf.result.Result;
import fr.bmartel.bboxapi.BboxApi;
import fr.bmartel.bboxapi.model.Device;
import kotlin.Triple;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DeviceInfo {

    public static void main(String args[]) throws InterruptedException {
        BboxApi bboxapi = new BboxApi();

        //asynchronous call
        CountDownLatch latch = new CountDownLatch(1);
        bboxapi.getDeviceInfo(new Handler<List<Device>>() {
            @Override
            public void failure(Request request, Response response, FuelError error) {
                error.printStackTrace();
                latch.countDown();
            }

            @Override
            public void success(Request request, Response response, List<Device> data) {
                System.out.println(data);
                latch.countDown();
            }
        });
        latch.await();

        //synchronous call
        Triple<Request, Response, Result<List<Device>, FuelError>> data = bboxapi.getDeviceInfoSync();
        Request request = data.getFirst();
        Response response = data.getSecond();
        Result<List<Device>, FuelError> obj = data.getThird();
        System.out.println(obj.get());
    }
}
