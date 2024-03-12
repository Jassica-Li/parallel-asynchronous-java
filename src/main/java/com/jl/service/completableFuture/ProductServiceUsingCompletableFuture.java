package com.jl.service.completableFuture;


import com.jl.domain.Product;
import com.jl.domain.ProductInfo;
import com.jl.domain.Review;
import com.jl.service.ProductInfoService;
import com.jl.service.ReviewService;

import java.util.concurrent.CompletableFuture;

import static com.jl.util.CommonUtil.stopWatch;
import static com.jl.util.CommonUtil.timeTaken;
import static com.jl.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->  productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() ->  reviewService.retrieveReviews(productId));
        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review)).join();
        timeTaken();
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetailsApproach2(String productId) {
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() ->  productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() ->  reviewService.retrieveReviews(productId));
        return productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review));
    }
}
