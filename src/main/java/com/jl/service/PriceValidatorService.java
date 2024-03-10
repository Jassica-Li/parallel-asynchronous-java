package com.jl.service;


import com.jl.domain.checkout.CartItem;

import static com.jl.util.CommonUtil.delay;
import static com.jl.util.LoggerUtil.log;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        log("CartItem:" + cartItem);
        delay(500);
        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;
        }
        return false;
    }
}
