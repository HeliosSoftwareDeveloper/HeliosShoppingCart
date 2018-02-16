package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rngrajo on 09/02/2018.
 */

public class UserTransaction implements Serializable, Comparable<UserTransaction> {

    DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMM dd, yyyy");

    private String transactionId;
    private int totalAmount, totalItemCount;
    private Date transactionDate;
    private User user;
    private ArrayList<CartItem> cartItemArrayList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getFormattedTotalAmount(){
        return numberFormat.format(getTotalAmount());
    }

    public String getFormattedDateTime(){
        return dateTimeFormat.format(getTransactionDate());
    }

    public String getFormattedDate(){
        return dateFormat.format(getTransactionDate());
    }

    @Override
    public int compareTo(UserTransaction o) {
        if (getTransactionDate() == null || o.getTransactionDate() == null)
            return 0;
        return getTransactionDate().compareTo(o.getTransactionDate());
    }
}
