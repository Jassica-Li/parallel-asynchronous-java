package com.jl.service.thread;


import com.jl.domain.Product;
import com.jl.domain.ProductInfo;
import com.jl.domain.Review;
import com.jl.service.ProductInfoService;
import com.jl.service.ReviewService;
import lombok.Getter;

import static com.jl.util.CommonUtil.stopWatch;
import static com.jl.util.LoggerUtil.log;

public class ProductServiceUsingThread {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();
        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);

        Thread productThread = new Thread(productInfoRunnable);
        Thread reviewThread = new Thread(reviewRunnable);


        productThread.start();
        reviewThread.start();

        productThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    private class ProductInfoRunnable implements Runnable {

        private final String productId;

        @Getter
        private ProductInfo productInfo;

        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
           productInfo =  productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewRunnable implements Runnable {

        private final String productId;

        @Getter
       private Review review;

        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
            review =  reviewService.retrieveReviews(productId);
        }
    }
}
