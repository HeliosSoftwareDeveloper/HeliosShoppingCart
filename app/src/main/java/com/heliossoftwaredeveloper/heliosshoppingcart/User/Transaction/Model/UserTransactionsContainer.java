package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by rngrajo on 02/02/2018.
 */

public class UserTransactionsContainer {

    private static UserTransactionsContainer instance = null;
    private HashMap<String, UserTransaction> transactionHashMap = new HashMap<>();
    private int currentTransactionCount, currentTotalPurchases;
    /**
     * method to get CartContainer instance
     *
     * @return CartContainer
     */
    public static UserTransactionsContainer getInstance() {
        if (instance == null) {
            instance = new UserTransactionsContainer();
        }
        return(instance);
    }

    public ArrayList<UserTransaction> getList(){
        ArrayList<UserTransaction> listCart = new ArrayList<>();
        listCart.addAll(transactionHashMap.values());
        Collections.sort(listCart,Collections.reverseOrder());
        return listCart;
    }

    public ArrayList<Object> getTransactionHistoryItemList(){
        ArrayList<UserTransaction> listTransaction = new ArrayList<>();
        listTransaction.addAll(getList());
        ArrayList<Object> transactionHistoryItemArrayList = new ArrayList<>();

        String formattedString = "";
        for(UserTransaction userTransaction : listTransaction){
            if(userTransaction.getFormattedDate().equals(formattedString)){
                transactionHistoryItemArrayList.add(userTransaction);
            }
            else{
                formattedString = userTransaction.getFormattedDate();
                transactionHistoryItemArrayList.add(formattedString);
                transactionHistoryItemArrayList.add(userTransaction);
            }
        }
        return transactionHistoryItemArrayList;
    }

    public ArrayList<Object> getTransactionHistoryDetails(UserTransaction userTransaction){
        ArrayList<Object> transactionHistoryDetailsArrayList = new ArrayList<>();
        ArrayList<String> transactionDetails = new ArrayList<>();

        transactionDetails.add(userTransaction.getFormattedTotalAmount());
        transactionDetails.add(userTransaction.getFormattedDateTime());
        transactionDetails.add(userTransaction.getTransactionId());
        transactionDetails.add(Integer.toString(userTransaction.getTotalItemCount()));

        transactionHistoryDetailsArrayList.add(transactionDetails);
        transactionHistoryDetailsArrayList.add(userTransaction.getUser());
        transactionHistoryDetailsArrayList.add(new String("Purchased Items"));
        transactionHistoryDetailsArrayList.addAll(userTransaction.getCartItemArrayList());

        return transactionHistoryDetailsArrayList;
    }


    public void save(UserTransaction userTransaction){
        Long tsLong = System.currentTimeMillis()/1000;
        String transactionId = tsLong.toString();

        while(transactionHashMap.containsKey(transactionId)){
            tsLong = System.currentTimeMillis()/1000;
            transactionId = tsLong.toString();
        }
        userTransaction.setTransactionDate(new Date());
        userTransaction.setTransactionId(transactionId);

        transactionHashMap.put(transactionId,userTransaction);
        currentTransactionCount+=1;
        currentTotalPurchases += userTransaction.getTotalAmount();
    }

    public int getCurrentTotalPurchases(){
        return currentTotalPurchases;
    }

    public int getCurrentTransactionCount(){
        return currentTransactionCount;
    }

    public boolean isTransactionEmpty(){
        return transactionHashMap.isEmpty();
    }

    public void clearCart(){
        transactionHashMap.clear();
        currentTransactionCount = 0;
        currentTotalPurchases = 0;
    }

}
