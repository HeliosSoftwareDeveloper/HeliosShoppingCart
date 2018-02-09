package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Interface;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import java.util.ArrayList;

/**
 * Created by rngrajo on 07/02/2018.
 */

public interface TransactionInteractor {

    ArrayList<UserTransaction> getUserTransactions();
    void saveTransaction(UserTransaction userTransaction);
    int getTotalPurchases();
    int getTransactionCount();
}
