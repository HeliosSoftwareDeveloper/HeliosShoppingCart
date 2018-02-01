package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;

import java.util.ArrayList;

/**
 * interface for MovieListFragment Callback
 *
 * Created by rngrajo on 30/01/2018.
 */

public interface ProductListView {
    void updateProductViewList(ArrayList<Product> movieArrayList);
    void showLoading();
    void hideLoading();
    void showUserPrompt(String title, String message);
    void onDestroy();
}
