package com.jl.service.completableFuture;

import com.jl.domain.Product;
import com.jl.service.ProductInfoService;
import com.jl.service.ReviewService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceUsingCompletableFutureTest {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();

    ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);

    @Test
    public void test_product_service_using_completable_future() {

        String productId = "testProductId";

        Product product = productService.retrieveProductDetails(productId);

        assertEquals(product.getProductInfo().getProductOptions().size(), 2);
        assertEquals(product.getProductInfo().getProductOptions().get(0).getSize(), "64GB");

    }

    @Test
    public void test_product_service_using_completable_future_approach_2() {

        String productId = "testProductId";

        productService.retrieveProductDetailsApproach2(productId).thenAccept((product -> {
            assertEquals(product.getProductInfo().getProductOptions().size(), 2);
            assertEquals(product.getProductInfo().getProductOptions().get(0).getSize(), "64GB");
        })).join();

    }

}