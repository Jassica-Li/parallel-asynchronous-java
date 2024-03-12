package com.jl.service.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class CompletableFutureExampleTest {

    CompletableFutureExample example = new CompletableFutureExample();

    // This will always return true since the thread takes more time than the test thread
    @Test
    public void test_hello_world_when_thread_is_still_running(){
        CompletableFuture<String> completableFuture = example.helloWorld();

        completableFuture.thenAccept((result) -> {
            assertEquals(result, "HELLO WORLD1111");
        });
    }

    // with join(), the test thread will wait for the result of this CompletableFuture
    @Test
    public void test_hello_world_when_waiting_for_the_result(){
        CompletableFuture<String> completableFuture = example.helloWorld();

        completableFuture.thenAccept((result) -> {
            assertNotEquals(result, "HELLO WORLD1111");
        }).join();
    }

    @Test
    public void test_hello_world_with_size(){
        CompletableFuture<String> completableFuture = example.helloWorldWithSize();

        completableFuture.thenAccept((result) -> {
            assertEquals(result, "11 - HELLO WORLD");
        }).join();
    }

    @Test
    public void test_use_combine(){
        String result = example.helloWorldUseCombine();
        assertEquals(result, "HELLO WORLD!");
    }

    @Test
    public void test_use_3_combine(){
        String result = example.helloWorldCombine3Threads();
        assertEquals(result, "HELLO WORLD!HI COMPLETABLEFUTURE");
    }

    @Test
    public void test_then_compose(){
        String result = example.helloWorldThenCompose();
        assertEquals(result, "HELLO WORLD!");
    }



}