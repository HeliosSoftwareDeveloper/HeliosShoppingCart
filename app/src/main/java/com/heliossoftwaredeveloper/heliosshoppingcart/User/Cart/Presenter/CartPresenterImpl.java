package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Presenter;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Interface.CartInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Impl.CartInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.View.CartView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;

import java.util.ArrayList;

/**
 * Created by rngrajo on 07/02/2018.
 */

public class CartPresenterImpl implements CartPresenter{

    private CartInteractor cartInteractor;
    private CartView cartView;

    public CartPresenterImpl(CartView cartView){
        cartInteractor = new CartInteractorImpl();
        this.cartView  = cartView;
    }

    @Override
    public void getCartList() {
        if(cartView == null)
            return;
        ArrayList<CartItem> cartItemArrayList = cartInteractor.getCartList();
        if(cartItemArrayList.isEmpty()){
            cartView.showEmptyCartView();
        }
        else{
            cartView.hideEmptyCartView();
            cartView.updateCartViewList(cartItemArrayList);
        }
    }

    @Override
    public void removeItemFromCart(Product product, int size, int position, String message) {
        if(cartView == null)
            return;
        cartInteractor.removeItemFromCart(product, size);
        cartView.notifyAdapterDataSetRemoved(position);
        cartView.updateTotalAmountAndItemCount(cartInteractor.getTotalAmount(),cartInteractor.getItemCount());
        cartView.showSnackBarMessage(product.getItemName()+ " "+ message);

        if(cartInteractor.isCartEmpty())
            cartView.showEmptyCartView();
    }

    @Override
    public void addItemToCart(Product product, int size, String message) {
        if(cartView == null)
            return;
        cartInteractor.addItemToCart(product, size);
        cartView.notifyAdapterDataSetChanged();
        cartView.updateTotalAmountAndItemCount(cartInteractor.getTotalAmount(),cartInteractor.getItemCount());
        cartView.showSnackBarMessage(product.getItemName()+ " "+ message);
    }

    @Override
    public void reduceItemQuantityInCart(Product product, int size, String message) {
        if(cartView == null)
            return;
        cartInteractor.reduceItemQuantityInCart(product, size);
        cartView.notifyAdapterDataSetChanged();
        cartView.updateTotalAmountAndItemCount(cartInteractor.getTotalAmount(),cartInteractor.getItemCount());
        cartView.showSnackBarMessage(product.getItemName() + " "+ message);
    }

    @Override
    public void setItemQuantityFromCart(Product product, int size, int newQuantity, String message) {
        cartInteractor.setItemQuantity(product, size, newQuantity);
        cartView.notifyAdapterDataSetChanged();
        cartView.updateTotalAmountAndItemCount(cartInteractor.getTotalAmount(),cartInteractor.getItemCount());
        cartView.showSnackBarMessage(product.getItemName() + " "+ message);
    }

    @Override
    public void getItemCountAndTotalAmount() {
        cartView.updateTotalAmountAndItemCount(cartInteractor.getTotalAmount(),cartInteractor.getItemCount());
    }

    @Override
    public void onDestroy() {
        if(cartView == null)
            return;
        cartView = null;
    }
}
