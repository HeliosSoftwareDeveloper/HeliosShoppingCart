package com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.Model;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by rngrajo on 02/02/2018.
 */

public class FavoritesContainer {

    private static FavoritesContainer instance = null;
    private HashMap<String, Product> favoritesProductHashMap = new HashMap<>();
    private boolean dataChanges;
    /**
     * method to get FavoritesContainer instance
     *
     * @return FavoritesContainer
     */
    public static FavoritesContainer getInstance() {
        if (instance == null) {
            instance = new FavoritesContainer();
        }
        return(instance);
    }

    public ArrayList<Product> getFavorites(){
        setDataChanges(false);
        ArrayList<Product> listCart = new ArrayList<>();
        listCart.addAll(favoritesProductHashMap.values());
        Collections.shuffle(listCart);
        return listCart;
    }

    public boolean addRemoveProduct(Product product){
        setDataChanges(true);
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

    public boolean hasDataChanges() {
        return dataChanges;
    }

    public void setDataChanges(boolean dataChanges) {
        this.dataChanges = dataChanges;
    }
}
