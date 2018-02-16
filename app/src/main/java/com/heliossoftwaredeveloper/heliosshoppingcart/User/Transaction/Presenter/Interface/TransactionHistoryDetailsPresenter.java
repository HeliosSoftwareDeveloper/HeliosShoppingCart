package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;

/**
 * Created by rngrajo on 08/02/2018.
 */

public interface TransactionHistoryDetailsPresenter {
    void getTransactionDetailsItems(UserTransaction userTransaction);
    void onDestroy();
}
