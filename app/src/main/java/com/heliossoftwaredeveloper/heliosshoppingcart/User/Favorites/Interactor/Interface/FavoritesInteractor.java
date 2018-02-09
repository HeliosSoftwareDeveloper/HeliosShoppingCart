package com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Interactor.Interface;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;

import java.util.ArrayList;

/**
 * Created by rngrajo on 07/02/2018.
 */

public interface FavoritesInteractor {

    ArrayList<Product> getFavoritesList();
    boolean addRemoveProductFromFavorites(Product product);
    boolean isFavoritesListEmpty();
    void clearFavorites();
    boolean isProductFavorite(Product product);
    boolean isFavoriteshasDataChanges();
}
