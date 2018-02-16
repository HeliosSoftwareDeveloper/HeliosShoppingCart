package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Commons.SingleTextViewViewHolder;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.UserTransaction;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

import java.util.ArrayList;

/**
 * Adapter class for TransactionHistoryList RecyclerView
 *
 * Created by rngrajo on 30/01/2018.
 */

public class TransactionHistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {

    private ArrayList<Object> transactionHistoryItemArrayList;
    private TransactionHistoryListAdapterCallback callback;

    public static final int TYPE_HEADER = 1000;
    public static final int TYPE_ITEM = 1001;
    /**
     * interface for TransactionHistoryListAdapter callback
     */
    public interface TransactionHistoryListAdapterCallback {
        void onShowTransactionHistoryDetails(UserTransaction userTransaction);
    }

    /**
     * Constructor for TransactionHistoryListAdapter
     *
     * @param callback
     * @param transactionHistoryItemArrayList
     *
     */
    public TransactionHistoryListAdapter(ArrayList<Object> transactionHistoryItemArrayList, TransactionHistoryListAdapterCallback callback) {
        this.transactionHistoryItemArrayList = transactionHistoryItemArrayList;
        this.callback         = callback;
    }

    /**
     * Class for TransactionHistoryItem ViewHolder
     */
    public class TransactionHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTransactionDate,txtTransactionNo,txtTotalAmount;
        public RelativeLayout layoutNavigationNext;

        public TransactionHistoryViewHolder(View view ) {
            super(view);
            txtTransactionDate = (TextView)view.findViewById(R.id.txtTransactionDate);
            txtTransactionNo = (TextView)view.findViewById(R.id.txtTransactionNo);
            txtTotalAmount = (TextView)view.findViewById(R.id.txtTotalAmount);
            layoutNavigationNext = (RelativeLayout)view.findViewById(R.id.layoutNavigationNext);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        if(type == TYPE_HEADER)
            return new SingleTextViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_textview, parent, false));
        else
            return new TransactionHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_history, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(transactionHistoryItemArrayList.get(position) instanceof  UserTransaction){
            final UserTransaction userTransaction = ((UserTransaction)transactionHistoryItemArrayList.get(position));
            final TransactionHistoryViewHolder transactionHistoryViewHolder = (TransactionHistoryViewHolder) holder;

            transactionHistoryViewHolder.txtTotalAmount.setText(userTransaction.getFormattedTotalAmount());
            transactionHistoryViewHolder.txtTransactionDate.setText(userTransaction.getFormattedDateTime());
            transactionHistoryViewHolder.txtTransactionNo.setText(userTransaction.getTransactionId());

            transactionHistoryViewHolder.layoutNavigationNext.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    callback.onShowTransactionHistoryDetails(userTransaction);
                }
            });
        }
        else{
            final SingleTextViewViewHolder transactionDateHeaderViewHolder = (SingleTextViewViewHolder) holder;
            transactionDateHeaderViewHolder.txtLabel.setText(((String)transactionHistoryItemArrayList.get(position)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return transactionHistoryItemArrayList.get(position) instanceof  UserTransaction ? TYPE_ITEM: TYPE_HEADER;
    }

    @Override
    public int getItemCount() {
        return transactionHistoryItemArrayList.size();
    }

    @Override
    public void onImageDownloaded(ImageRequestData imageRequestData) {
        imageRequestData.getImageView().setImageDrawable(imageRequestData.getDownloadedDrawable());
    }

    @Override
    public void onImageDownloadError(String error) {

    }
}