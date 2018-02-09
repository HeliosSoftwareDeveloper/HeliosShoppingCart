package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Interface.TransactionInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransactionsContainer;

import java.util.ArrayList;

/**
 * Created by rngrajo on 09/02/2018.
 */

public class TransactionInteractorImpl implements TransactionInteractor {

    @Override
    public ArrayList<UserTransaction> getUserTransactions() {
        return UserTransactionsContainer.getInstance().getList();
    }

    @Override
    public void saveTransaction(UserTransaction userTransaction) {
        UserTransactionsContainer.getInstance().save(userTransaction);
    }

    @Override
    public int getTotalPurchases() {
        return UserTransactionsContainer.getInstance().getCurrentTotalPurchases();
    }

    @Override
    public int getTransactionCount() {
        return UserTransactionsContainer.getInstance().getCurrentTransactionCount();
    }
}
