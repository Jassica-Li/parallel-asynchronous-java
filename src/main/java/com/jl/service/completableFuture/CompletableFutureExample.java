package com.jl.service.completableFuture;

import com.jl.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.jl.util.CommonUtil.*;
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

    private String helloWorldUsingTypicalMethod(){
        String hello = helloWorldService.hello();
        String world = helloWorldService.world();
        return (hello + world).toUpperCase();
    }

    public String helloWorldUseCombine(){
        // only take the max time of one of its threads
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        String result = hello.thenCombine(world, (h, w) -> h + w).thenApply(String::toUpperCase).join();
        timeTaken();
        return result;
    }

    public String helloWorldCombine3Threads(){
        // use the max time of all its threads, in which is nearly 2 seconds
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            return "hi completableFuture";
        });
        String result = hello
                .thenCombine(world, (h, w) -> h + w)
                .thenCombine(completableFuture, (helloWorld, future) -> helloWorld + future)
                        .thenApply(String::toUpperCase).join();
        timeTaken();
        return result;
    }
}
