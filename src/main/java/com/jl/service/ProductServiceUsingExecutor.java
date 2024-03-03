package com.jl.service;


import com.jl.domain.Product;
import com.jl.domain.ProductInfo;
import com.jl.domain.Review;

import java.util.concurrent.*;

import static com.jl.util.CommonUtil.stopWatch;
import static com.jl.util.LoggerUtil.log;

public class ProductServiceUsingExecutor {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException {
        stopWatch.start();
        Future<ProductInfo> productInfoFuture = executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));
        ProductInfo productInfo = productInfoFuture.get();
//        using timeout will throw an exception
//        ProductInfo productInfo = productInfoFuture.get(1, TimeUnit.SECONDS);
        Review review = reviewFuture.get();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
        executorService.shutdown();


    }
}
