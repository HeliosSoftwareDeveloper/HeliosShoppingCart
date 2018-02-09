package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.View;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import java.util.ArrayList;

/**
 * interface for CartListFragment Callback
 *
 * Created by rngrajo on 30/01/2018.
 */

public interface CartView {
    void updateCartViewList(ArrayList<CartItem> cartItemArrayList);
    void showSnackBarMessage(String message);
    void notifyAdapterDataSetChanged();
    void notifyAdapterDataSetRemoved(int position);
    void updateTotalAmountAndItemCount(int totalAmount, int itemCount);
    void showEmptyCartView();
    void hideEmptyCartView();
    void onDestroy();
}
