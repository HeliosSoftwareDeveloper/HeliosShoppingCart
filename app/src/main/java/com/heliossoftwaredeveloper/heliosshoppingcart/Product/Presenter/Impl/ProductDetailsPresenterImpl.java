package com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Interface.CartInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Impl.CartInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Presenter.Interface.ProductDetailsPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface.ProductDetailsView;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Interactor.Impl.FavoritesInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Interactor.Interface.FavoritesInteractor;

/**
 * Presenter impl class for MovieListFragment
 *
 * Created by rngrajo on 30/01/2018.
 */

public class ProductDetailsPresenterImpl implements ProductDetailsPresenter{

    private ProductDetailsView productDetailsView;
    private CartInteractor cartInteractor;
    private FavoritesInteractor favoritesInteractor;

    public ProductDetailsPresenterImpl(ProductDetailsView productDetailsView){
        this.productDetailsView = productDetailsView;
        this.cartInteractor = new CartInteractorImpl();
        this.favoritesInteractor = new FavoritesInteractorImpl();
    }

    @Override
    public void addRemoveProductTofavorite(Product product, String message, String message2) {
        if(productDetailsView == null)
            return;
        boolean isFavorite = favoritesInteractor.addRemoveProductFromFavorites(product);
        productDetailsView.showSnackBarMessage(product.getItemName()+ " "+(isFavorite ? message: message2));
    }

    @Override
    public void addProductToCart(Product product, int size, String message, String message2) {
        if(productDetailsView == null)
            return;

        boolean isNewlyAdded = cartInteractor.addItemToCart(product,size);;
        productDetailsView.showSnackBarMessage(product.getItemName()+ " "+ (isNewlyAdded ? message : message2));
    }

    @Override
    public boolean isProductFavorite(Product product) {
        if(productDetailsView == null)
            return false;
        return favoritesInteractor.isProductFavorite(product);
    }

    @Override
    public void onDestroy() {
        if(productDetailsView == null)
            return;
        productDetailsView = null;
    }
}
