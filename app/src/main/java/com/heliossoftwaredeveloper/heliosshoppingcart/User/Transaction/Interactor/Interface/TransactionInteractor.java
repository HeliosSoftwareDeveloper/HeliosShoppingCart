package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Interface;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import java.util.ArrayList;

/**
 * Created by rngrajo on 07/02/2018.
 */

public interface TransactionInteractor {

    ArrayList<UserTransaction> getUserTransactions();
    ArrayList<Object> getUserTransactionHistoryItems();
    ArrayList<Object> getTransactionHistoryDetailsItems(UserTransaction userTransaction);
    void saveTransaction(UserTransaction userTransaction);
    int getTotalPurchases();
    int getTransactionCount();
}
