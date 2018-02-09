package com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interface;


import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;

/**
 * Created by rngrajo on 01/02/2018.
 */

public interface ProductListPresenter {
    void getProductList();
    void addProductToCart(Product product, int size, String message, String message2);
    void onDestroy();
}
