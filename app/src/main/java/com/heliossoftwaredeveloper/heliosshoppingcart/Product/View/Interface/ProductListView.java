package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;

import java.util.ArrayList;

/**
 * interface for MovieListFragment Callback
 *
 * Created by rngrajo on 30/01/2018.
 */

public interface ProductListView {
    void updateProductViewList(ArrayList<Product> productArrayList);
    void showLoading();
    void hideLoading();
    void showSnackBarMessage(String message);
    void showUserPrompt(String title, String message);
    void onDestroy();
}
