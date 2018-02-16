package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Impl.TransactionHistoryDetailsPresenterImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface.TransactionHistoryDetailsPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Adapter.TransactionHistoryDetailsAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.TransactionHistoryDetailsView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.WrapContentLinearLayoutManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionHistoryDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionHistoryDetailsFragment extends Fragment implements TransactionHistoryDetailsView {
    private static final String ARG_USERTRANSPARAMS = "param1";

    private UserTransaction userTransaction;
    private TransactionHistoryDetailsPresenter presenter;
    private RecyclerView recyclerViewTransactionDetails;
    private ArrayList<Object> transactionDetails = new ArrayList<>();

    public TransactionHistoryDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userTransaction Parameter 1.
     * @return A new instance of fragment TransactionHistoryDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionHistoryDetailsFragment newInstance(UserTransaction userTransaction) {
        TransactionHistoryDetailsFragment fragment = new TransactionHistoryDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERTRANSPARAMS,userTransaction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userTransaction = ((UserTransaction) getArguments().getSerializable(ARG_USERTRANSPARAMS));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history_transaction_details, container, false);

        presenter = new TransactionHistoryDetailsPresenterImpl(this);

        recyclerViewTransactionDetails = (RecyclerView)view.findViewById(R.id.recyclerViewTransactionDetails);

        recyclerViewTransactionDetails.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerViewTransactionDetails.setAdapter(new TransactionHistoryDetailsAdapter(transactionDetails, getActivity()));

        presenter.getTransactionDetailsItems(userTransaction);
        return view;
    }

    @Override
    public void updateTransactionDetails(ArrayList<Object> transactionHistoryDetails) {
        transactionDetails.clear();
        transactionDetails.addAll(transactionHistoryDetails);
        recyclerViewTransactionDetails.getAdapter().notifyDataSetChanged();
    }
}
