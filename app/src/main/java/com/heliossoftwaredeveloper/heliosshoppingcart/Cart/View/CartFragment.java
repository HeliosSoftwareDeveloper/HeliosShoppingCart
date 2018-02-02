package com.heliossoftwaredeveloper.heliosshoppingcart.Cart.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.Cart.View.Adapter.CartAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.WrapContentLinearLayoutManager;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.CartFragmentCallback} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartAdapter.CartAdapterCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<CartItem> cartItemArrayList;

    private CartFragmentCallback cartFragmentCallback;
    private CartAdapter cartAdapter;
    private TextView txtTotalAmount,txtItemCount;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cartItemArrayList Parameter 1.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(ArrayList<CartItem> cartItemArrayList) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, cartItemArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cartItemArrayList = (ArrayList<CartItem>)getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView recyclerViewCart = (RecyclerView)view.findViewById(R.id.recyclerViewCart);
        Button btnCheckOut = (Button)view.findViewById(R.id.btnCheckOut);

        txtTotalAmount = (TextView)view.findViewById(R.id.txtTotalAmount);
        txtItemCount = (TextView)view.findViewById(R.id.txtItemCount);
        recyclerViewCart.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(cartItemArrayList,getActivity(),this);
        cartAdapter.setHasStableIds(true);
        txtTotalAmount.setText("$"+Integer.toString(computeTotalAmount()));
        txtItemCount.setText(Integer.toString(computeItemCount()));
        recyclerViewCart.setAdapter(cartAdapter);

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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartFragmentCallback = null;
    }

    @Override
    public void onAddQuantityClicked(CartItem cartItem, int position) {
        cartFragmentCallback.onAddProductQuantityClicked(cartItem.getItem(),cartItem.getItemSize());
        cartAdapter.notifyDataSetChanged();
        txtTotalAmount.setText("$"+Integer.toString(computeTotalAmount()));
        txtItemCount.setText(Integer.toString(computeItemCount()));
    }

    @Override
    public void onReduceQuantityClicked(CartItem cartItem, int position) {
        cartFragmentCallback.onReduceProductQuantityClicked(cartItem.getItem(),cartItem.getItemSize());
        cartAdapter.notifyDataSetChanged();
        txtTotalAmount.setText("$"+Integer.toString(computeTotalAmount()));
        txtItemCount.setText(Integer.toString(computeItemCount()));
    }

    @Override
    public void onRemoveFromCart(CartItem cartItem, int position) {
        cartItemArrayList.remove(position);
        cartAdapter.notifyItemRemoved(position);
        txtTotalAmount.setText("$"+Integer.toString(computeTotalAmount()));
        cartFragmentCallback.onRemoveFromCartClicked(cartItem.getItem(),cartItem.getItemSize());
        txtItemCount.setText(Integer.toString(computeItemCount()));
    }

    @Override
    public void onShowProductDetails(Product product) {
        cartFragmentCallback.onShowProductDetailsClicked(product);
    }

    public int computeTotalAmount(){
        int total = 0;
        for(int i = 0; i < cartItemArrayList.size(); i++){
            total += cartItemArrayList.get(i).getItemQuantity() * cartItemArrayList.get(i).getItem().getItemPrice();
        }
        return total;
    }

    public int computeItemCount(){
        int total = 0;
        for(int i = 0; i < cartItemArrayList.size(); i++){
            total += cartItemArrayList.get(i).getItemQuantity();
        }
        return total;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface CartFragmentCallback {
        // TODO: Update argument type and name
        void onAddProductQuantityClicked(Product product, int size);
        void onRemoveFromCartClicked(Product product, int size);
        void onReduceProductQuantityClicked(Product product, int size);
        void onShowProductDetailsClicked(Product product);
        void onShowCheckOutScreenClicked();
    }
}
