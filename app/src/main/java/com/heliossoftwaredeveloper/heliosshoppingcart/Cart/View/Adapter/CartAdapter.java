package com.heliossoftwaredeveloper.heliosshoppingcart.Cart.View.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.Constant;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapter class for Movielist RecyclerView
 *
 * Created by rngrajo on 30/01/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {

    private ArrayList<CartItem> cartItemArrayList;
    private Activity context;
    private CartAdapterCallback callback;

    /**
     * interface for MovieListAdapter callback
     */
    public interface CartAdapterCallback {
        void onAddQuantityClicked(CartItem cartItem, int position);
        void onReduceQuantityClicked(CartItem cartItem, int position);
        void onRemoveFromCart(CartItem cartItem, int position);
        void onShowProductDetails(Product product);
    }

    /**
     * Constructor for MovieListAdapter
     *
     * @param callback
     * @param context
     * @param cartItemArrayList
     *
     */
    public CartAdapter(ArrayList<CartItem> cartItemArrayList, Activity context, CartAdapterCallback callback) {
        this.cartItemArrayList = cartItemArrayList;
        this.context          = context;
        this.callback         = callback;
    }

    /**
     * Class for MovieViewDetails ViewHolder
     */
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName,txtSize,txtPrice,txtQuantity;
        public ImageView imgPhoto;
        public ImageButton imgBtnBefore, imgBtnNext;
        public Button btnRemoveFromCart;

        public ProductsViewHolder(View view ) {
            super(view);
            txtProductName = (TextView)view.findViewById(R.id.txtProductName);
            txtSize = (TextView)view.findViewById(R.id.txtSize);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            txtQuantity = (TextView)view.findViewById(R.id.txtQuantity);
            imgPhoto = (ImageView)view.findViewById(R.id.imgPhoto);
            imgBtnBefore = (ImageButton)view.findViewById(R.id.imgBtnBefore);
            imgBtnNext = (ImageButton)view.findViewById(R.id.imgBtnNext);
            btnRemoveFromCart = (Button)view.findViewById(R.id.btnRemoveFromCart);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int capsuleType) {
        return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CartItem cartItem = cartItemArrayList.get(position);
        final Product product = cartItem.getItem();

        final ProductsViewHolder productViewHolder = (ProductsViewHolder) holder;
        productViewHolder.txtProductName.setText(product.getItemName());
        productViewHolder.txtSize.setText(Integer.toString(cartItem.getItemSize()));
        productViewHolder.txtPrice.setText("$"+Integer.toString(product.getItemPrice() * cartItem.getItemQuantity()));

        productViewHolder.txtQuantity.setText(Integer.toString(cartItem.getItemQuantity()));

        productViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onShowProductDetails(product);
            }
        });

        productViewHolder.imgBtnBefore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(productViewHolder.txtQuantity.getText().toString());
                if(quantity > 1){
                    productViewHolder.txtQuantity.setText(Integer.toString(quantity - 1));
                    callback.onReduceQuantityClicked(cartItem,position);
                }
                else{
                    callback.onRemoveFromCart(cartItem, position);
                }
            }
        });

        productViewHolder.imgBtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(productViewHolder.txtQuantity.getText().toString());
                    productViewHolder.txtQuantity.setText(Integer.toString(quantity +1));
                callback.onAddQuantityClicked(cartItem, position);
            }
        });

        productViewHolder.btnRemoveFromCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onRemoveFromCart(cartItemArrayList.get(position),position);
            }
        });


        ImageLazyLoaderManager.getInstance().setCallback(this);
        ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(productViewHolder.imgPhoto,Constant.URL_IMAGE_LINK.replace("[SLUG]",product.getImageArrayList().get(0)),ContextCompat.getDrawable(context, R.mipmap.ic_launcher)));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }

    @Override
    public void onImageDownloaded(ImageRequestData imageRequestData) {
        imageRequestData.getImageView().setImageDrawable(imageRequestData.getDownloadedDrawable());
    }

    @Override
    public void onImageDownloadError(String error) {

    }
}