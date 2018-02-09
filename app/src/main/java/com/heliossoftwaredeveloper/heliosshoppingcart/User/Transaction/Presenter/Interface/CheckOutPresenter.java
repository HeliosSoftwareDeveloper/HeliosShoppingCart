package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;

import java.util.ArrayList;

/**
 * Created by rngrajo on 09/02/2018.
 */

public interface CheckOutPresenter {

    void getItemCountAndTotalAmount();
    void saveTransaction(UserTransaction userTransaction);
    void cancelTransaction();
    ArrayList<CartItem> getCartItems();
    void onDestroy();
}
