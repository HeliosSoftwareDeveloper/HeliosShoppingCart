package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View;

/**
 * Created by rngrajo on 09/02/2018.
 */

public interface CheckOutView {

    void showMainScreen();
    void showConfirmCheckOutDialog();
    void updateTotalAmountAndItemCount(int totalAmount, int itemCount);
    void onDestroy();
}
