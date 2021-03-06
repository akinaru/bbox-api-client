package fr.bmartel.bboxapi.router.javasample;

import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.github.kittinunf.result.Result;
import fr.bmartel.bboxapi.router.BboxApiRouter;
import kotlin.Triple;

import java.util.concurrent.CountDownLatch;

public class Logout {

    public static void main(String args[]) throws InterruptedException {
        BboxApiRouter bboxapi = new BboxApiRouter();
        bboxapi.init();
        bboxapi.setPassword("admin");

        //asynchronous call
        CountDownLatch latch = new CountDownLatch(1);
        bboxapi.logout(new Handler<byte[]>() {
            @Override
            public void failure(Request request, Response response, FuelError error) {
                error.printStackTrace();
                latch.countDown();
            }

            @Override
            public void success(Request request, Response response, byte[] data) {
                System.out.println(response.getStatusCode());
                latch.countDown();
            }
        });
        latch.await();

        //synchronous call
        Triple<Request, Response, Result<byte[], FuelError>> data = bboxapi.logoutSync();
        Request request = data.getFirst();
        Response response = data.getSecond();
        Result<byte[], FuelError> obj = data.getThird();
        System.out.println(response.getStatusCode());
    }
}