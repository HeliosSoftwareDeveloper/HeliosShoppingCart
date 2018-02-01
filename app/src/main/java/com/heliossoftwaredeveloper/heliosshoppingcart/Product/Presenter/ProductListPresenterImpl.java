package com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interactor.ProductInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interactor.ProductInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.ProductListView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIParams;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.Constant;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.NetworkTransactionUtility;

import java.util.ArrayList;

/**
 * Presenter impl class for MovieListFragment
 *
 * Created by rngrajo on 30/01/2018.
 */

public class ProductListPresenterImpl implements ProductListPresenter, ProductInteractor.OnGetProductsCallback{

    private ProductListView productListView;
    ProductInteractor productInteractor;

    public ProductListPresenterImpl(ProductListView productListView){
        this.productListView = productListView;
        this.productInteractor = new ProductInteractorImpl();
    }

    @Override
    public void getProductList() {
        if(productListView == null)
            return;
        productListView.showLoading();
        APIParams apiParams = new APIParams();
        apiParams.setIsHTTPS(false);
        apiParams.setRequestUrl(Constant.API_URL_GET_PRODUCTS);
        apiParams.setRequestType(NetworkTransactionUtility.REQUEST_TYPE_GET);
        apiParams.setRequestCaller(Constant.API_MODE_GET_PRODUCTS);
        productInteractor.getProducts(apiParams,this);
    }

    @Override
    public void onDestroy() {
        if(productListView == null)
            return;
        productListView.onDestroy();
        productInteractor.onDestroy();
    }

    @Override
    public void onGetProductsSuccess(ArrayList<Product> movieArrayList) {
        if(productListView == null)
            return;
        productListView.hideLoading();
        productListView.updateProductViewList(movieArrayList);
    }

    @Override
    public void onGetProductsError(String title, String errorMessage, APIParams apiParams) {
        if(productListView == null)
            return;
        productListView.hideLoading();
        productListView.showUserPrompt(title, errorMessage);
    }

    @Override
    public void onGetProductsCanceled() {
        if(productListView == null)
            return;
        productListView.hideLoading();
    }
}
