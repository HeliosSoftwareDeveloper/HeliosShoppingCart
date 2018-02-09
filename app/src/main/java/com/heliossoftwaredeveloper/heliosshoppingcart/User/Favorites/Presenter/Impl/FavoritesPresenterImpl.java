package com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Presenter.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Interactor.Impl.FavoritesInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Interactor.Interface.FavoritesInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Presenter.Interface.FavoritesPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.View.FavoritesView;

import java.util.ArrayList;

/**
 * Presenter impl class for MovieListFragment
 *
 * Created by rngrajo on 30/01/2018.
 */

public class FavoritesPresenterImpl implements FavoritesPresenter{

    private FavoritesView favoritesView;
    private FavoritesInteractor favoritesInteractor;

    public FavoritesPresenterImpl(FavoritesView favoritesView){
        this.favoritesView = favoritesView;
        this.favoritesInteractor = new FavoritesInteractorImpl();
    }

    @Override
    public void getFavoritesList() {
        if(favoritesView == null)
            return;
        ArrayList<Product> productArrayList = favoritesInteractor.getFavoritesList();
        if(productArrayList.isEmpty())
            favoritesView.showEmptyFavoritesView();
        else
            favoritesView.updateProductViewList(productArrayList);
    }

    @Override
    public void onDestroy() {
        if(favoritesView == null)
            return;
        favoritesView.onDestroy();
    }

}
