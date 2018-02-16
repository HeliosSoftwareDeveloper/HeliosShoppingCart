package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View;

import java.util.ArrayList;

/**
 * Created by rngrajo on 08/02/2018.
 */

public interface TransactionListHistoryView {
    void updateTransactionHistoryList(ArrayList<Object> userTransactionArrayList);
    void showEmptyTransactionHistoryView();
    void hideEmptyTransactionHistoryView();
    void onDestroy();
}
