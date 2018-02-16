package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface;

/**
 * Created by rngrajo on 08/02/2018.
 */

public interface TransactionHistoryListPresenter {
    void getTransactionHistoryList();
    int getTransactionTotalAmount();
    int getTransactionCount();
    void onDestroy();
}
