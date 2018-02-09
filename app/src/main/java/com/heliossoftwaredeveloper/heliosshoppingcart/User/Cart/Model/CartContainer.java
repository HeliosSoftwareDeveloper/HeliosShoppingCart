package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rngrajo on 02/02/2018.
 */

public class CartContainer {

    private static CartContainer instance = null;
    private HashMap<String, CartItem> cartItemHashMap = new HashMap<>();
    private int currentItemCount, currentTotalAmount;
    /**
     * method to get CartContainer instance
     *
     * @return CartContainer
     */
    public static CartContainer getInstance() {
        if (instance == null) {
            instance = new CartContainer();
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
        currentTotalAmount += product.getItemPrice();
        currentItemCount += 1;
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
        if(cartItemHashMap.containsKey(itemKey)){
            currentTotalAmount -= product.getItemPrice() * cartItemHashMap.get(itemKey).getItemQuantity();
            currentItemCount -= cartItemHashMap.get(itemKey).getItemQuantity();
            cartItemHashMap.remove(itemKey);
        }
    }

    public void setItemQuantity(Product product, int size, int newQuantity){
        String itemKey = product.getItemcode()+Integer.toString(size);
        if(cartItemHashMap.containsKey(itemKey)){
            int lastQuantityByItemAndSize = cartItemHashMap.get(itemKey).getItemQuantity();
            int lastTotalAmountByItemAndSize = lastQuantityByItemAndSize * product.getItemPrice();

            cartItemHashMap.get(itemKey).setItemQuantity(newQuantity);

            currentTotalAmount -= lastTotalAmountByItemAndSize;
            currentItemCount -= lastQuantityByItemAndSize;

            currentTotalAmount += (newQuantity * product.getItemPrice());
            currentItemCount += newQuantity;
        }
    }

    public void reduceQuantity(Product product, int size){
        String itemKey = product.getItemcode()+Integer.toString(size);
        if(cartItemHashMap.containsKey(itemKey)){
            int quantity =  cartItemHashMap.get(itemKey).getItemQuantity() - 1;
            if(quantity > 0){
                cartItemHashMap.get(itemKey).setItemQuantity(quantity);
                currentTotalAmount -= product.getItemPrice();
                currentItemCount -=1;
            }
        }
    }

    public int getCurrentTotalAmount(){
        return currentTotalAmount;
    }

    public int getCurrentItemCount(){
        return currentItemCount;
    }

    public boolean isCartEmpty(){
        return cartItemHashMap.isEmpty();
    }

    public void clearCart(){
        cartItemHashMap.clear();
        currentItemCount = 0;
        currentTotalAmount = 0;
    }

}
