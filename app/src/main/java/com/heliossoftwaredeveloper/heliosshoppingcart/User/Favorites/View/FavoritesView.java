package com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.View;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import java.util.ArrayList;

/**
 * Created by rngrajo on 08/02/2018.
 */

public interface FavoritesView {
    void updateProductViewList(ArrayList<Product> productArrayList);
    void showEmptyFavoritesView();
    void hideEmptyFavoritesView();
    void onDestroy();
}
