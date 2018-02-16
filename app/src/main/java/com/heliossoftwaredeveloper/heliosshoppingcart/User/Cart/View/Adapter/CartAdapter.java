package com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.View.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.User.Cart.Model.CartItem;
import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.HorizontalDataSetPicker;
import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.Constant;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

import java.util.ArrayList;

/**
 * Adapter class for Cart list RecyclerView
 *
 * Created by rngrajo on 30/01/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {

    private ArrayList<CartItem> cartItemArrayList;
    private Activity context;
    private CartAdapterCallback callback;

    /**
     * interface for CartAdapter callback
     */
    public interface CartAdapterCallback {
        void onAddQuantityClicked(CartItem cartItem, int position);
        void onReduceQuantityClicked(CartItem cartItem, int position);
        void onRemoveFromCart(CartItem cartItem, int position);
        void onSetQuantity(CartItem cartItem, int position, int newQuantity);
        void onShowProductDetails(Product product);
    }

    /**
     * Constructor for CartAdapter
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
     * Class for ProductsViewHolder ViewHolder
     */
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName,txtSize,txtPrice;
        public ImageView imgPhoto;
        public Button btnRemoveFromCart;
        public HorizontalDataSetPicker horizontalPickerQuantity;

        public ProductsViewHolder(View view ) {
            super(view);
            txtProductName = (TextView)view.findViewById(R.id.txtProductName);
            txtSize = (TextView)view.findViewById(R.id.txtSize);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            imgPhoto = (ImageView)view.findViewById(R.id.imgPhoto);
            btnRemoveFromCart = (Button)view.findViewById(R.id.btnRemoveFromCart);
            horizontalPickerQuantity = (HorizontalDataSetPicker)view.findViewById(R.id.horizontalPickerQuantity);
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

        productViewHolder.horizontalPickerQuantity.setDataRange(1, 20,cartItem.getItemQuantity());
        productViewHolder.horizontalPickerQuantity.setListener(new HorizontalDataSetPicker.HorizontalDataSetPickerListener.HorizontalDataSetPickerListenerInfinite() {
            @Override
            public void onNextClickedListener(String value, int intValue) {
                callback.onAddQuantityClicked(cartItem, position);
            }

            @Override
            public void onPreviousClickedListener(String value, int intValue) {
                callback.onReduceQuantityClicked(cartItem,position);
            }

            @Override
            public void onInfiniteTriggeredListener(String value, int intValue) {
                callback.onSetQuantity(cartItem, position, intValue);
            }
        });

        productViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onShowProductDetails(product);
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