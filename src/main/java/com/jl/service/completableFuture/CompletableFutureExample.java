package com.jl.service.completableFuture;

import com.jl.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.jl.util.CommonUtil.delay;
import static com.jl.util.LoggerUtil.log;

public class CompletableFutureExample {

    private static final HelloWorldService helloWorldService = new HelloWorldService();

    public CompletableFuture<String> helloWorld() {
        return  CompletableFuture.supplyAsync(helloWorldService::helloWorld).thenApply(String::toUpperCase)
                ;
    }

    public CompletableFuture<String> helloWorldWithSize(){
        return  helloWorld().thenApply(it -> String.format(it.length() + " - " + it));
    }

    private static void anotherMethod() {
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenAccept((result) -> {
                    log("the result is " + result);
                });

        log("Done!");
        delay(2000);

    }
}
