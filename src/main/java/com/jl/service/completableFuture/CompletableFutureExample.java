package com.jl.service.completableFuture;

import com.jl.service.HelloWorldService;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        resetAndStart();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        String result = hello
                .thenCombine(world, (h, w) ->
                {
                    log("combine hello and world");
                    return h + w;
                })
                .thenApply((it) -> {
                    log("to upper case");
                    return it.toUpperCase();
                })
                .join();
        timeTaken();
        return result;
    }

    public String helloWorldUsingCustomThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        return hello.thenCombineAsync(world, (h, w) -> h + w, executorService).thenApplyAsync(String::toUpperCase, executorService).join();
    }

    public String helloWorldUsingAsync(){
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        return hello
                .thenCombineAsync(world, (h, w) ->
                {
                    log("combine hello and world");
                    return h + w;
                })
                .thenApplyAsync((it) -> {
                    log("to upper case");
                    return it.toUpperCase();
                })
                .join();
    }


    public String helloWorldCombine3Threads(){
        // use the max time of all its threads, in which is nearly 2 seconds
        resetAndStart();
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

    public String helloWorldThenCompose(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        String result = hello
                .thenCompose(helloWorldService::worldFuture)
                .thenApply(String::toUpperCase).join();
        timeTaken();
        return result;
    }
}
