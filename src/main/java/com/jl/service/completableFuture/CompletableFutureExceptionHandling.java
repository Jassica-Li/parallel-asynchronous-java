package com.jl.service.completableFuture;

import com.jl.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.jl.util.LoggerUtil.log;

public class CompletableFutureExceptionHandling {

    private final HelloWorldService helloWorldService;

    public CompletableFutureExceptionHandling(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }


    public String helloWorldUsingHandle() {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

        return hello
                .handle((res, exception) -> {
                    if (exception != null) {
                        log("exception is " + exception);
                        return "";
                    }
                    return res;

                })
                .thenCombine(world, (h, w) -> h + w)
                .handle((res, exception) -> {
                    if (exception != null) {
                        log("exception is " + exception);
                        return "wrong";

                    }
                    return res;
                })
                .thenApply(String::toUpperCase)
                .join();
    }


    public String helloWorldUsingExceptionally() {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

        return hello
                .exceptionally(exception -> {
                    log("exception is " + exception);
                    return "";

                })
                .thenCombine(world, (h, w) -> h + w)
                .exceptionally(exception -> {
                    log("exception is " + exception);
                    return "wrong";

                })
                .thenApply(String::toUpperCase)
                .join();
    }

    public String helloWorldUsingWhenComplete() {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hi CompletableFuture");

        return hello
                .whenComplete((res,exception) -> {
                    log("exception is " + exception);

                })
                .thenCombine(world, (h, w) -> h + w)
                .whenComplete((res,exception) -> {
                    log("exception is " + exception);

                })
                .exceptionally(exception -> {
                    log("exception is " + exception);
                    return "";
                })
                .thenCombine(completableFuture, (pre, res) -> pre + res)
                .thenApply(String::toUpperCase)
                .join();
    }

}
