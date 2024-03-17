package com.jl.service.completableFuture;

import com.jl.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompletableFutureExceptionHandlingTest {

    @Mock
    private HelloWorldService helloWorldService = mock(HelloWorldService.class);

    private final CompletableFutureExceptionHandling completableFutureExceptionHandling = new CompletableFutureExceptionHandling(helloWorldService);

    @Test
    void should_handle_hello_exception() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("error for hello function"));
        when(helloWorldService.world()).thenCallRealMethod();
        String result = completableFutureExceptionHandling.helloWorldUsingHandle();

        assertEquals(" WORLD!", result);
    }

    @Test
    void should_handle_both_exceptions() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("error for hello function"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("error for world function"));
        String result = completableFutureExceptionHandling.helloWorldUsingHandle();

        assertEquals("WRONG", result);
    }

    @Test
    void should_return_right_result() {

        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();
        String result = completableFutureExceptionHandling.helloWorldUsingHandle();

        assertEquals("HELLO WORLD!", result);
    }

    @Test
    void should_handle_using_exceptionally() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("error for hello function"));
        when(helloWorldService.world()).thenCallRealMethod();
        String result = completableFutureExceptionHandling.helloWorldUsingExceptionally();

        assertEquals(" WORLD!", result);
    }

    @Test
    void should_handle_using_when_complete() {

        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();
        String result = completableFutureExceptionHandling.helloWorldUsingWhenComplete();

        assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE", result);
    }

    @Test
    void should_handle_exception_using_when_complete() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("error for hello function"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("error for hello function"));
        String result = completableFutureExceptionHandling.helloWorldUsingWhenComplete();

        assertEquals("HI COMPLETABLEFUTURE", result);
    }
}