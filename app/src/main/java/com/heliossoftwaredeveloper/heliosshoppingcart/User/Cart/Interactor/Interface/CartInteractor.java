package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Interface;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import java.util.ArrayList;

/**
 * Created by rngrajo on 07/02/2018.
 */

public interface CartInteractor {

    ArrayList<CartItem> getCartList();
    void removeItemFromCart(Product product, int size);
    boolean addItemToCart(Product product, int size);
    void reduceItemQuantityInCart(Product product, int size);
    void setItemQuantity(Product product, int size, int newQuantity);
    boolean isCartEmpty();
    void clearCart();
    int getTotalAmount();
    int getItemCount();
}
