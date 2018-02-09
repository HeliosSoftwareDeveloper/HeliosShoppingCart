package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model;

import java.util.ArrayList;
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
        return listCart;
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
