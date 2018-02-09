package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Presenter;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;


/**
 * Created by rngrajo on 07/02/2018.
 */

public interface CartPresenter {

    void getCartList();
    void removeItemFromCart(Product product, int size, int position, String message);
    void addItemToCart(Product product, int size, String message);
    void reduceItemQuantityInCart(Product product, int size, String message);
    void setItemQuantityFromCart(Product product, int size, int newQuantity, String message);
    void getItemCountAndTotalAmount();
    void onDestroy();
}
