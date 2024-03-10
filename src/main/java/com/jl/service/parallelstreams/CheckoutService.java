package com.jl.service.parallelstreams;

import com.jl.domain.checkout.Cart;
import com.jl.domain.checkout.CartItem;
import com.jl.domain.checkout.CheckoutResponse;
import com.jl.domain.checkout.CheckoutStatus;
import com.jl.service.PriceValidatorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jl.util.CommonUtil.*;

@RequiredArgsConstructor
public class CheckoutService {

    private final PriceValidatorService priceValidatorService;

    public CheckoutResponse checkout(Cart cart) {
        stopWatchReset();
        startTimer();
        List<CartItem> expiredCartItemList = cart.getCartItemList()
                .parallelStream()
                .peek(cartItem -> cartItem.setExpired(priceValidatorService.isCartItemInvalid(cartItem)))
                .filter(CartItem::isExpired)
                .toList();

        timeTaken();
        if (!expiredCartItemList.isEmpty()) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, expiredCartItemList);
        }

        return new CheckoutResponse(CheckoutStatus.SUCCESS);
    }
}
