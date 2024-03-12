package com.jl.service.completableFuture;

import com.jl.domain.Product;
import com.jl.service.InventoryService;
import com.jl.service.ProductInfoService;
import com.jl.service.ReviewService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductServiceUsingCompletableFutureTest {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    InventoryService inventoryService = new InventoryService();

    ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);

    @Test
    public void test_product_service_using_completable_future() {

        String productId = "testProductId";

        Product product = productService.retrieveProductDetails(productId);

        assertEquals(product.getProductInfo().getProductOptions().size(), 2);
        assertEquals(product.getProductInfo().getProductOptions().get(0).getSize(), "64GB");
        assertNull(product.getProductInfo().getProductOptions().get(0).getInventory());

    }

    @Test
    public void test_product_service_using_completable_future_approach_2() {

        String productId = "testProductId";

        productService.retrieveProductDetailsApproach2(productId).thenAccept((product -> {
            assertEquals(product.getProductInfo().getProductOptions().size(), 2);
            assertEquals(product.getProductInfo().getProductOptions().get(0).getSize(), "64GB");
        })).join();

    }

    @Test
    public void test_product_service_with_inventory_info(){
        String productId = "testProductId";

        Product product = productService.retrieveProductDetailsWithInventory(productId);

        assertEquals(product.getProductInfo().getProductOptions().size(), 2);
        assertEquals(product.getProductInfo().getProductOptions().get(0).getSize(), "64GB");
        assertEquals(product.getProductInfo().getProductOptions().get(0).getInventory().getCount(), 2);
    }

    @Test
    public void test_product_service_with_inventory_info_approach_2(){
        String productId = "testProductId";

        Product product = productService.retrieveProductDetailsWithInventoryApproach2(productId);

        assertEquals(product.getProductInfo().getProductOptions().size(), 2);
        assertEquals(product.getProductInfo().getProductOptions().get(0).getSize(), "64GB");
        assertEquals(product.getProductInfo().getProductOptions().get(0).getInventory().getCount(), 2);
    }

}