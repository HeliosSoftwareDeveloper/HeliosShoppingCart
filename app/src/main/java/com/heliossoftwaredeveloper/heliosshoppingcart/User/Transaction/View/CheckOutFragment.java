package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Impl.CheckOutPresenterImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface.CheckOutPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckOutFragment.CheckOutFragmentCallback} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class CheckOutFragment extends Fragment implements CheckOutView{

    private CheckOutFragmentCallback checkOutFragmentCallback;

    private EditText etFullName, etPhoneNumber, etEmail, etAddress;
    private TextView txtTotalAmount,txtItemCount;
    private CheckOutPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_check_out, container, false);
        presenter = new CheckOutPresenterImpl(this);
        Button btnCheckOut = (Button)view.findViewById(R.id.btnCheckOut);
        Button btnCancel = (Button)view.findViewById(R.id.btnCancel);

        etFullName = (EditText)view.findViewById(R.id.etFullName);
        etPhoneNumber = (EditText)view.findViewById(R.id.etPhoneNumber);
        etEmail = (EditText)view.findViewById(R.id.etEmail);
        etAddress = (EditText)view.findViewById(R.id.etAddress);

        txtTotalAmount = (TextView)view.findViewById(R.id.txtTotalAmount);
        txtItemCount = (TextView)view.findViewById(R.id.txtItemCount);

        btnCheckOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!checkIfEmpty(etFullName) && !checkIfEmpty(etPhoneNumber) && !checkIfEmpty(etEmail)&& !checkIfEmpty(etAddress)){
                   showConfirmCheckOutDialog();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to cancel current transaction?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.cancelTransaction();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
            }
        });

        presenter.getItemCountAndTotalAmount();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CheckOutFragmentCallback) {
            checkOutFragmentCallback = (CheckOutFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        checkOutFragmentCallback = null;
    }

    @Override
    public void showMainScreen() {
        checkOutFragmentCallback.onProceedToCheckOutClicked();
    }

    @Override
    public void showConfirmCheckOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to check out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UserTransaction userTransaction = new UserTransaction();
                userTransaction.setCartItemArrayList(presenter.getCartItems());
                userTransaction.setDeliveryAddress(etAddress.getText().toString());
                userTransaction.setEmail(etEmail.getText().toString());
                userTransaction.setFullName(etFullName.getText().toString());
                userTransaction.setPhoneNumber(etPhoneNumber.getText().toString());
                presenter.saveTransaction(userTransaction);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean checkIfEmpty(EditText editText){
        return editText.getText().toString().trim().isEmpty();
    }

    @Override
    public void updateTotalAmountAndItemCount(int totalAmount, int itemCount) {
        txtTotalAmount.setText("$"+Integer.toString(totalAmount));
        txtItemCount.setText(Integer.toString(itemCount));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface CheckOutFragmentCallback {
        // TODO: Update argument type and name
        void onProceedToCheckOutClicked();
    }

}