package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View;

import java.util.ArrayList;

/**
 * Created by rngrajo on 08/02/2018.
 */

public interface TransactionHistoryDetailsView {
    void updateTransactionDetails(ArrayList<Object> transactionHistoryDetails);
    void onDestroy();
}
