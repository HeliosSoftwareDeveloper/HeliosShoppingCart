package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.View.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Commons.SingleTextViewViewHolder;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model.User;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.Constant;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Adapter class for TransactionHistoryList RecyclerView
 *
 * Created by rngrajo on 30/01/2018.
 */

public class TransactionHistoryDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {

    private ArrayList<Object> transactionHistoryItemArrayList;
    private static final int TYPE_ITEM_TRANSACTION_DETAILS = 0;
    private static final int TYPE_ITEM_USER = 1;
    private static final int TYPE_ITEM_CART_ITEMS = 2;
    private static final int TYPE_ITEM_CART_HEADER = 3;
    DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    private Activity context;

    /**
     * Constructor for TransactionHistoryListAdapter
     *
     * @param transactionHistoryItemArrayList
     *
     */
    public TransactionHistoryDetailsAdapter(ArrayList<Object> transactionHistoryItemArrayList, Activity context) {
        this.transactionHistoryItemArrayList = transactionHistoryItemArrayList;
        this.context          = context;
    }

    /**
     * Class for TransactionDetailsHolder ViewHolder
     */
    public class TransactionDetailsHolder extends RecyclerView.ViewHolder {
        public TextView txtTransactionDate, txtTransactionNo, txtTotalAmount, txtItemsCount;

        public TransactionDetailsHolder(View view ) {
            super(view);
            txtTransactionDate = (TextView)view.findViewById(R.id.txtTransactionDate);
            txtTransactionNo = (TextView)view.findViewById(R.id.txtTransactionNo);
            txtTotalAmount = (TextView)view.findViewById(R.id.txtTotalAmount);
            txtItemsCount = (TextView)view.findViewById(R.id.txtItemsCount);
        }
    }

    /**
     * Class for TransactionCartItemHolder ViewHolder
     */
    public class TransactionCartItemHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName, txtQuantity, txtSize, txtPrice;
        public ImageView imgPhoto;

        public TransactionCartItemHolder(View view ) {
            super(view);
            txtProductName = (TextView)view.findViewById(R.id.txtProductName);
            txtQuantity = (TextView)view.findViewById(R.id.txtQuantity);
            txtSize = (TextView)view.findViewById(R.id.txtSize);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            imgPhoto = (ImageView)view.findViewById(R.id.imgPhoto);
        }
    }

    /**
     * Class for TransactionUserDetailsHolder ViewHolder
     */
    public class TransactionUserDetailsHolder extends RecyclerView.ViewHolder {
        public TextView txtFullName, txtPhoneNumber, txtEmail, txtDeliveryAddress;

        public TransactionUserDetailsHolder(View view ) {
            super(view);
            txtFullName = (TextView)view.findViewById(R.id.txtFullName);
            txtPhoneNumber = (TextView)view.findViewById(R.id.txtPhoneNumber);
            txtEmail = (TextView)view.findViewById(R.id.txtEmail);
            txtDeliveryAddress = (TextView)view.findViewById(R.id.txtDeliveryAddress);
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        if(type == TYPE_ITEM_TRANSACTION_DETAILS)
            return new TransactionDetailsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_details, parent, false));
        else if(type == TYPE_ITEM_USER)
            return new TransactionUserDetailsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_user_info, parent, false));
        if(type == TYPE_ITEM_CART_HEADER)
            return new SingleTextViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_textview, parent, false));
        else
            return new TransactionCartItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(transactionHistoryItemArrayList.get(position) instanceof User){
            User user = ((User)transactionHistoryItemArrayList.get(position));
            TransactionUserDetailsHolder transactionUserDetailsHolder = (TransactionUserDetailsHolder) holder;

            transactionUserDetailsHolder.txtFullName.setText(user.getFullName());
            transactionUserDetailsHolder.txtPhoneNumber.setText(user.getPhoneNumber());
            transactionUserDetailsHolder.txtEmail.setText(user.getEmail());
            transactionUserDetailsHolder.txtDeliveryAddress.setText(user.getDeliveryAddress());
        }
        else if(transactionHistoryItemArrayList.get(position) instanceof CartItem){
            CartItem cartItem = ((CartItem)transactionHistoryItemArrayList.get(position));
            TransactionCartItemHolder transactionCartItemHolder = (TransactionCartItemHolder) holder;

            transactionCartItemHolder.txtPrice.setText(numberFormat.format(cartItem.getItem().getItemPrice()));
            transactionCartItemHolder.txtProductName.setText(cartItem.getItem().getItemName());
            transactionCartItemHolder.txtQuantity.setText(Integer.toString(cartItem.getItemQuantity()));
            transactionCartItemHolder.txtSize.setText(Integer.toString(cartItem.getItemSize()));

            ImageLazyLoaderManager.getInstance().setCallback(this);
            ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(transactionCartItemHolder.imgPhoto, Constant.URL_IMAGE_LINK.replace("[SLUG]",cartItem.getItem().getImageArrayList().get(0)), ContextCompat.getDrawable(context, R.mipmap.ic_launcher)));
        }
        else if(transactionHistoryItemArrayList.get(position) instanceof String){
            final SingleTextViewViewHolder transactionDateHeaderViewHolder = (SingleTextViewViewHolder) holder;
            transactionDateHeaderViewHolder.txtLabel.setText(((String)transactionHistoryItemArrayList.get(position)));
        }
        else{
            ArrayList<String> transactionDetails = ((ArrayList<String>)transactionHistoryItemArrayList.get(position));
            TransactionDetailsHolder transactionDetailsHolder = (TransactionDetailsHolder) holder;

            transactionDetailsHolder.txtItemsCount.setText(transactionDetails.get(3));
            transactionDetailsHolder.txtTotalAmount.setText(transactionDetails.get(0));
            transactionDetailsHolder.txtTransactionDate.setText(transactionDetails.get(1));
            transactionDetailsHolder.txtTransactionNo.setText(transactionDetails.get(2));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(transactionHistoryItemArrayList.get(position) instanceof User)
            return TYPE_ITEM_USER;
        else if(transactionHistoryItemArrayList.get(position) instanceof CartItem)
            return TYPE_ITEM_CART_ITEMS;
        else if(transactionHistoryItemArrayList.get(position) instanceof String)
            return TYPE_ITEM_CART_HEADER;
        else
            return TYPE_ITEM_TRANSACTION_DETAILS;
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