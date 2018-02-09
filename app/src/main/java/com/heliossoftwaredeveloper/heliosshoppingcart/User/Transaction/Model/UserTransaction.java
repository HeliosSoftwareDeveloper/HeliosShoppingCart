package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rngrajo on 09/02/2018.
 */

public class UserTransaction {

    private String fullName, phoneNumber, email, deliveryAddress, transactionId;
    private ArrayList<CartItem> cartItemArrayList;
    private int totalAmount, totalItemCount;
    private Date transactionDate;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public ArrayList<CartItem> getCartItemArrayList() {
        return cartItemArrayList;
    }

    public void setCartItemArrayList(ArrayList<CartItem> cartItemArrayList) {
        this.cartItemArrayList = cartItemArrayList;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }
}
