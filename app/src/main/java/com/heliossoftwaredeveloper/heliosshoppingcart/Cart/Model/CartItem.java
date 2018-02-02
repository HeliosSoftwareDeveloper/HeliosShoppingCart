package com.heliossoftwaredeveloper.heliosshoppingcart.Cart.Model;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;

import java.io.Serializable;

/**
 * Created by rngrajo on 02/02/2018.
 */

public class CartItem implements Serializable{
    private Product item;
    private int itemQuantity = 1, itemSize;

    public CartItem(Product item, int itemSize){
        this.item = item;
        this.itemSize = itemSize;
    }

    public int getItemSize() {
        return itemSize;
    }
    public Product getItem() {
        return item;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
