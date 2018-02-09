package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Impl;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Impl.CartInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Interactor.Interface.CartInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Impl.TransactionInteractorImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Interactor.Interface.TransactionInteractor;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface.CheckOutPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.CheckOutView;

import java.util.ArrayList;

/**
 * Created by rngrajo on 09/02/2018.
 */

public class CheckOutPresenterImpl implements CheckOutPresenter{

    private CheckOutView checkOutView;
    private CartInteractor cartInteractor;
    private TransactionInteractor transactionInteractor;

    public CheckOutPresenterImpl(CheckOutView checkOutView){
        this.checkOutView = checkOutView;
        this.cartInteractor = new CartInteractorImpl();
        this.transactionInteractor = new TransactionInteractorImpl();
    }
    @Override
    public void getItemCountAndTotalAmount() {
        if(checkOutView == null)
            return;
        checkOutView.updateTotalAmountAndItemCount(cartInteractor.getTotalAmount(), cartInteractor.getItemCount());
    }

    @Override
    public void cancelTransaction() {
        if(checkOutView == null)
            return;
        cartInteractor.clearCart();
        checkOutView.showMainScreen();
    }

    @Override
    public ArrayList<CartItem> getCartItems() {
        if(checkOutView == null)
            return null;
        return cartInteractor.getCartList();
    }

    @Override
    public void saveTransaction(UserTransaction userTransactions) {
        if(checkOutView == null)
            return;
        transactionInteractor.saveTransaction(userTransactions);
        cartInteractor.clearCart();
        checkOutView.showMainScreen();
    }

    @Override
    public void onDestroy() {
        if(checkOutView == null)
            return;
        checkOutView = null;
    }
}
