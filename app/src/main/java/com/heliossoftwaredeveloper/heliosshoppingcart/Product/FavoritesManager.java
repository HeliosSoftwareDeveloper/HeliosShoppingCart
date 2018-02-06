package com.heliossoftwaredeveloper.heliosshoppingcart.Product;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rngrajo on 02/02/2018.
 */

public class FavoritesManager {

    private static FavoritesManager instance = null;
    private HashMap<String, Product> favoritesProductHashMap = new HashMap<>();
    /**
     * method to get FavoritesManager instance
     *
     * @return FavoritesManager
     */
    public static FavoritesManager getInstance() {
        if (instance == null) {
            instance = new FavoritesManager();
        }
        return(instance);
    }

    public ArrayList<Product> getFavorites(){
        ArrayList<Product> listCart = new ArrayList<>();
        listCart.addAll(favoritesProductHashMap.values());
        return listCart;
    }

    public boolean addRemoveProduct(Product product){
        String itemKey = product.getItemcode();
        if(favoritesProductHashMap.containsKey(itemKey)){
            favoritesProductHashMap.remove(itemKey);
            return false;
        }
        else{
            favoritesProductHashMap.put(itemKey,product);
            return true;
        }
    }

    public boolean isProductFavorite(Product product){
        return favoritesProductHashMap.containsKey(product.getItemcode());
    }

    public boolean isFavoritesEmpty(){
        return favoritesProductHashMap.isEmpty();
    }

    public void clearFavorites(){
        favoritesProductHashMap.clear();
    }

}
