package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Impl.TransactionInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Interface.TransactionInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface.TransactionHistoryDetailsPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.TransactionHistoryDetailsView;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.TransactionListHistoryView;

import java.util.ArrayList;

/**
 * Presenter impl class for Transaction History List Fragment
 *
 * Created by rngrajo on 30/01/2018.
 */

public class TransactionHistoryDetailsPresenterImpl implements TransactionHistoryDetailsPresenter {

    private TransactionHistoryDetailsView transactionHistoryDetailsView;
    private TransactionInteractor transactionInteractor;

    public TransactionHistoryDetailsPresenterImpl(TransactionHistoryDetailsView transactionHistoryDetailsView){
        this.transactionHistoryDetailsView = transactionHistoryDetailsView;
        this.transactionInteractor = new TransactionInteractorImpl();
    }

    @Override
    public void getTransactionDetailsItems(UserTransaction userTransaction) {
        if(userTransaction == null)
            return;
        transactionHistoryDetailsView.updateTransactionDetails(transactionInteractor.getTransactionHistoryDetailsItems(userTransaction));
    }

    @Override
    public void onDestroy() {
        if(transactionHistoryDetailsView == null)
            return;
        transactionHistoryDetailsView.onDestroy();
    }

}
