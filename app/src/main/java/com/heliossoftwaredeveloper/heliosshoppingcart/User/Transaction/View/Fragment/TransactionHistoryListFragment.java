package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Impl.TransactionHistoryListPresenterImpl;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Presenter.Interface.TransactionHistoryListPresenter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Adapter.TransactionHistoryListAdapter;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.TransactionListHistoryView;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.WrapContentLinearLayoutManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionHistoryListFragment.TransactionHistoryListFragmentCallback} interface
 * to handle interaction events.
 */
public class TransactionHistoryListFragment extends Fragment implements TransactionListHistoryView,TransactionHistoryListAdapter.TransactionHistoryListAdapterCallback {

    private TransactionHistoryListFragmentCallback callback;
    private RecyclerView recyclerViewTransactionHistory;
    private RelativeLayout layoutEmptyTransactions;
    private TransactionHistoryListPresenter presenter;
    private ArrayList<Object> userTransactionArrayList = new ArrayList<>();
    private TextView txtTotalAmount, txtTotalItem;
    DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_transaction_history_list, container, false);

        presenter = new TransactionHistoryListPresenterImpl(this);
        recyclerViewTransactionHistory = (RecyclerView)view.findViewById(R.id.recyclerViewTransactionHistory);
        txtTotalAmount = (TextView)view.findViewById(R.id.txtTotalAmount);
        txtTotalItem = (TextView)view.findViewById(R.id.txtTotalItem);
        layoutEmptyTransactions = (RelativeLayout)view.findViewById(R.id.layoutEmptyTransactions);
        recyclerViewTransactionHistory.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerViewTransactionHistory.setAdapter(new TransactionHistoryListAdapter(userTransactionArrayList, this));

        presenter.getTransactionHistoryList();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TransactionHistoryListFragmentCallback) {
            callback = (TransactionHistoryListFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onShowTransactionHistoryDetails(UserTransaction userTransaction) {
        callback.onTransactionHistoryListItemClicked(userTransaction);
    }

    @Override
    public void updateTransactionHistoryList(ArrayList<Object> userTransactionArrayList) {
        this.userTransactionArrayList.clear();
        this.userTransactionArrayList.addAll(userTransactionArrayList);
        recyclerViewTransactionHistory.getAdapter().notifyDataSetChanged();

        txtTotalAmount.setText(numberFormat.format(presenter.getTransactionTotalAmount()));
        txtTotalItem.setText(Integer.toString(presenter.getTransactionCount()));
    }

    @Override
    public void showEmptyTransactionHistoryView() {
        layoutEmptyTransactions.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyTransactionHistoryView() {
        layoutEmptyTransactions.setVisibility(View.GONE);
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
    public interface TransactionHistoryListFragmentCallback {
        // TODO: Update argument type and name
        void onTransactionHistoryListItemClicked(UserTransaction userTransaction);
    }
}
