package com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rngrajo on 01/02/2018.
 */

public class Product implements Serializable{

    private String itemName, itemCategory, itemcode, releaseDate,itemDescription;
    private int itemPrice;
    private ArrayList<String> colorSchemeArrayList, imageArrayList;
    private ArrayList<Integer> sizes;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ArrayList<String> getColorSchemeArrayList() {
        return colorSchemeArrayList;
    }

    public void setColorSchemeArrayList(ArrayList<String> colorSchemeArrayList) {
        this.colorSchemeArrayList = colorSchemeArrayList;
    }

    public ArrayList<String> getImageArrayList() {
        return imageArrayList;
    }

    public void setImageArrayList(ArrayList<String> imageArrayList) {
        this.imageArrayList = imageArrayList;
    }

    public ArrayList<Integer> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<Integer> sizes) {
        this.sizes = sizes;
    }
}
