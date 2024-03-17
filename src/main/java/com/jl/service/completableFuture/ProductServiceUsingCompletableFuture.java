package com.jl.service.completableFuture;


import com.jl.domain.*;
import com.jl.service.InventoryService;
import com.jl.service.ProductInfoService;
import com.jl.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.jl.util.CommonUtil.*;
import static com.jl.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    private final InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        resetAndStart();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review)).join();
        timeTaken();
        return product;
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
       resetAndStart();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(this::updateInventoryInfo);
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review)).join();
        timeTaken();
        return product;
    }

    private ProductInfo updateInventoryInfo(ProductInfo productInfo) {
        List<ProductOption> productOptionList =
                productInfo
                        .getProductOptions()
                        //.stream()
                        .parallelStream()
                        .peek(productOption -> productOption.setInventory(inventoryService.addInventory(productOption)))
                        .toList();
        productInfo.setProductOptions(productOptionList);
        return productInfo;
    }

    public Product retrieveProductDetailsWithInventoryApproach2(String productId) {
        resetAndStart();
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(result -> {
                    List<ProductOption> productOptions = updateInventoryInfoApproach2(result);
                    result.setProductOptions(productOptions);
                    return result;
                });
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review)).join();
        timeTaken();
        return product;
    }

    private List<ProductOption> updateInventoryInfoApproach2(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> completableFutureList = productInfo
                .getProductOptions()
                .stream()
                .map(productOption -> CompletableFuture
                        .supplyAsync(() -> inventoryService.addInventory(productOption))
                        .exceptionally(exception -> Inventory.builder()
                                .count(1).build())
                        .thenApply((inventory) -> {
                                    productOption.setInventory(inventory);
                                    return productOption;
                                }
                        )).toList();
        return completableFutureList.stream().map(CompletableFuture::join).toList();
    }

    public CompletableFuture<Product> retrieveProductDetailsApproach2(String productId) {
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        return productInfoCompletableFuture.thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review));
    }
}
