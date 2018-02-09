package com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Interactor.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Interface.CartInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartContainer;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Interactor.Interface.FavoritesInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Model.FavoritesContainer;

import java.util.ArrayList;

/**
 * Created by rngrajo on 07/02/2018.
 */

public class FavoritesInteractorImpl implements FavoritesInteractor {

    @Override
    public ArrayList<Product> getFavoritesList() {
        return FavoritesContainer.getInstance().getFavorites();
    }

    @Override
    public boolean addRemoveProductFromFavorites(Product product) {
        return FavoritesContainer.getInstance().addRemoveProduct(product);
    }

    @Override
    public boolean isFavoritesListEmpty() {
        return FavoritesContainer.getInstance().isFavoritesEmpty();
    }

    @Override
    public void clearFavorites() {
        FavoritesContainer.getInstance().clearFavorites();
    }

    @Override
    public boolean isProductFavorite(Product product) {
        return FavoritesContainer.getInstance().isProductFavorite(product);
    }

    @Override
    public boolean isFavoriteshasDataChanges() {
        return FavoritesContainer.getInstance().hasDataChanges();
    }
}
