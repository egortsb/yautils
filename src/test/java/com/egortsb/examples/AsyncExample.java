package com.egortsb.examples;

import com.egortsb.async.Async;
import com.egortsb.async.Result;

public class AsyncExample {

    public static void main(String[] args) throws Exception {

        //Initialize
        Async async = new Async();

        //submit simple task
        async.submit(() -> System.out.println("execute asynchronously"));

        //submit repeatable task
        async.submitWithInterval(() -> {
            System.out.println("sending heartbeat....");
        }, 1000L);


        //run task which returns value
        Result<String> result = async.submit(()-> "result");
        assert result.get() == "result";

        Thread.sleep(2000L);

        //shutdown executor
        async.shutdown();
    }
}
