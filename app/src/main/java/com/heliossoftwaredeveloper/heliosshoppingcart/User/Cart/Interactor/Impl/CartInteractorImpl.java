package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Interface.CartInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartContainer;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import java.util.ArrayList;

/**
 * Created by rngrajo on 07/02/2018.
 */

public class CartInteractorImpl implements CartInteractor {

    @Override
    public ArrayList<CartItem> getCartList() {
        return CartContainer.getInstance().getCart();
    }

    @Override
    public void removeItemFromCart(Product product, int size) {
        CartContainer.getInstance().removeItem(product,size);
    }

    @Override
    public boolean addItemToCart(Product product, int size) {
        return CartContainer.getInstance().addItem(product, size);
    }

    @Override
    public void reduceItemQuantityInCart(Product product, int size) {
        CartContainer.getInstance().reduceQuantity(product, size);
    }

    @Override
    public boolean isCartEmpty() {
        return CartContainer.getInstance().isCartEmpty();
    }

    @Override
    public void clearCart() {
        CartContainer.getInstance().clearCart();
    }

    @Override
    public int getTotalAmount() {
        return CartContainer.getInstance().getCurrentTotalAmount();
    }

    @Override
    public int getItemCount() {
        return CartContainer.getInstance().getCurrentItemCount();
    }

    @Override
    public void setItemQuantity(Product product, int size, int newQuantity) {
        CartContainer.getInstance().setItemQuantity(product, size, newQuantity);
    }
}
