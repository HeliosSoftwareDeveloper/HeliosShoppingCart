package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Interface.ProductViewListener;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Presenter.CartPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Presenter.CartPresenterImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.View.Adapter.CartAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.View.CartView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.WrapContentLinearLayoutManager;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.CartFragmentCallback} interface
 * to handle interaction events.
 */
public class CartFragment extends Fragment implements CartAdapter.CartAdapterCallback, CartView{

    private ArrayList<CartItem> cartItemArrayList = new ArrayList<>();
    private CartFragmentCallback cartFragmentCallback;
    private ProductViewListener productViewListener;
    private CartAdapter cartAdapter;
    private TextView txtTotalAmount,txtItemCount;
    private CartPresenter presenter;
    private RelativeLayout layoutEmptyCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        presenter = new CartPresenterImpl(this);
        RecyclerView recyclerViewCart = (RecyclerView)view.findViewById(R.id.recyclerViewCart);
        Button btnCheckOut = (Button)view.findViewById(R.id.btnCheckOut);

        layoutEmptyCart = (RelativeLayout)view.findViewById(R.id.layoutEmptyCart);
        txtTotalAmount = (TextView)view.findViewById(R.id.txtTotalAmount);
        txtItemCount = (TextView)view.findViewById(R.id.txtItemCount);
        recyclerViewCart.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(this.cartItemArrayList,getActivity(),this);
        cartAdapter.setHasStableIds(true);
        recyclerViewCart.setAdapter(cartAdapter);

        presenter.getCartList();

        btnCheckOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cartFragmentCallback.onShowCheckOutScreenClicked();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartFragmentCallback) {
            cartFragmentCallback = (CartFragmentCallback) context;
            productViewListener  = (ProductViewListener) context;
        }
        if (context instanceof ProductViewListener) {
            productViewListener  = (ProductViewListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartFragmentCallback = null;
    }

    @Override
    public void onAddQuantityClicked(CartItem cartItem, int position) {
        presenter.addItemToCart(cartItem.getItem(),cartItem.getItemSize(), getString(R.string.msg_product_quantity_added));
    }

    @Override
    public void onReduceQuantityClicked(CartItem cartItem, int position) {
        presenter.reduceItemQuantityInCart(cartItem.getItem(),cartItem.getItemSize(), getString(R.string.msg_product_quantity_reduced));
    }

    @Override
    public void onRemoveFromCart(CartItem cartItem, int position) {
        presenter.removeItemFromCart(cartItem.getItem(),cartItem.getItemSize(),position, getString(R.string.msg_product_removed));
    }

    @Override
    public void onSetQuantity(CartItem cartItem, int position, int newQuantity) {
        presenter.setItemQuantityFromCart(cartItem.getItem(),cartItem.getItemSize(),newQuantity, getString(R.string.msg_product_quantity_adjusted));
    }

    @Override
    public void onShowProductDetails(Product product) {
        productViewListener.onProductClickedListener(product);
    }

    @Override
    public void updateCartViewList(ArrayList<CartItem> cartItemArrayList) {
        presenter.getItemCountAndTotalAmount();
        this.cartItemArrayList.addAll(cartItemArrayList);
        notifyAdapterDataSetChanged();
    }

    @Override
    public void showSnackBarMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }

    @Override
    public void notifyAdapterDataSetChanged() {
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyAdapterDataSetRemoved(int position) {
        cartItemArrayList.remove(position);
        cartAdapter.notifyItemRemoved(position);
    }

    @Override
    public void updateTotalAmountAndItemCount(int totalAmount, int itemCount) {
        txtTotalAmount.setText("$"+Integer.toString(totalAmount));
        txtItemCount.setText(Integer.toString(itemCount));
    }

    @Override
    public void showEmptyCartView() {
        layoutEmptyCart.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyCartView() {
        layoutEmptyCart.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface CartFragmentCallback {
        // TODO: Update argument type and name
        void onShowCheckOutScreenClicked();
    }
}
