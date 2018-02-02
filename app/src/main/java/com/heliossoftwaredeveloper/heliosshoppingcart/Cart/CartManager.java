package com.heliossoftwaredeveloper.heliosshoppingcart.Cart;

import com.heliossoftwaredeveloper.heliosshoppingcart.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rngrajo on 02/02/2018.
 */

public class CartManager {

    private static CartManager instance = null;
    private HashMap<String, CartItem> cartItemHashMap = new HashMap<>();
    /**
     * method to get ImageLazyLoaderManager instance
     *
     * @return ImageLazyLoaderManager
     */
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return(instance);
    }

    public ArrayList<CartItem> getCart(){
        ArrayList<CartItem> listCart = new ArrayList<CartItem>();
        listCart.addAll(cartItemHashMap.values());
        return listCart;
    }

    public boolean addItem(Product product, int size){
        String itemKey = product.getItemcode()+Integer.toString(size);
        if(cartItemHashMap.containsKey(itemKey)){
           int quantity =  cartItemHashMap.get(itemKey).getItemQuantity() + 1;
            cartItemHashMap.get(itemKey).setItemQuantity(quantity);
            return false;
        }
        else{
            cartItemHashMap.put(itemKey,new CartItem(product, size));
            return true;
        }
    }

    public void removeItem(Product product, int size){
        String itemKey = product.getItemcode()+Integer.toString(size);
        if(cartItemHashMap.containsKey(itemKey))
            cartItemHashMap.remove(itemKey);
    }

    public void reduceQuantity(Product product, int size){
        String itemKey = product.getItemcode()+Integer.toString(size);
        if(cartItemHashMap.containsKey(itemKey)){
            int quantity =  cartItemHashMap.get(itemKey).getItemQuantity() - 1;
            if(quantity > 0)
                cartItemHashMap.get(itemKey).setItemQuantity(quantity);
            else
                removeItem(product,size);
        }
    }

    public void clearCart(){
        cartItemHashMap.clear();
    }

}
