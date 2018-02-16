package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Impl.TransactionInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Interface.TransactionInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface.TransactionHistoryListPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.TransactionListHistoryView;

import java.util.ArrayList;

/**
 * Presenter impl class for Transaction History List Fragment
 *
 * Created by rngrajo on 30/01/2018.
 */

public class TransactionHistoryListPresenterImpl implements TransactionHistoryListPresenter{

    private TransactionListHistoryView transactionListHistoryView;
    private TransactionInteractor transactionInteractor;

    public TransactionHistoryListPresenterImpl(TransactionListHistoryView transactionListHistoryView){
        this.transactionListHistoryView = transactionListHistoryView;
        this.transactionInteractor = new TransactionInteractorImpl();
    }

    @Override
    public int getTransactionTotalAmount() {
        return transactionInteractor.getTotalPurchases();
    }

    @Override
    public int getTransactionCount() {
        return transactionInteractor.getTransactionCount();
    }

    @Override
    public void getTransactionHistoryList() {
        if(transactionListHistoryView == null)
            return;
        ArrayList<Object> userTransactionArrayList = transactionInteractor.getUserTransactionHistoryItems();
        if(userTransactionArrayList.isEmpty())
            transactionListHistoryView.showEmptyTransactionHistoryView();
        else
            transactionListHistoryView.updateTransactionHistoryList(userTransactionArrayList);
    }

    @Override
    public void onDestroy() {
        if(transactionListHistoryView == null)
            return;
        transactionListHistoryView.onDestroy();
    }

}
