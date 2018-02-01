package com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interactor;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIParams;

import java.util.ArrayList;

/**
 * Created by rngrajo on 01/02/2018.
 */

public interface ProductInteractor {

    interface OnGetProductsCallback{
        void onGetProductsSuccess(ArrayList<Product> productArrayList);
        void onGetProductsError(String title, String errorMessage, APIParams apiParams);
        void onGetProductsCanceled();
    }

    void getProducts(APIParams apiParams, OnGetProductsCallback callback);
    void onDestroy();
}
