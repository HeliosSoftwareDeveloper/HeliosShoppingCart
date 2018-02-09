package com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interface;


import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;

/**
 * Created by rngrajo on 01/02/2018.
 */

public interface ProductDetailsPresenter {
    void addRemoveProductTofavorite(Product product, String message, String message2);
    void addProductToCart(Product product, int size, String message, String message2);
    boolean isProductFavorite(Product product);
    void onDestroy();
}
