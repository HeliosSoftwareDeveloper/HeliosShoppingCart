package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.HorizontalDataSetPicker;
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

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {

    private ArrayList<Product> productsArrayList;
    private HashMap<String, Integer> selectedSize;
    private Activity context;
    private ProductListAdapterCallback callback;

    /**
     * interface for MovieListAdapter callback
     */
    public interface ProductListAdapterCallback {
        void onItemClicked(Product product, int position);
        void onItemAddToCart(Product product, int sizeSelected);
    }

    /**
     * Constructor for MovieListAdapter
     *
     * @param callback
     * @param context
     * @param productsArrayList
     *
     */
    public ProductListAdapter(ArrayList<Product> productsArrayList, Activity context, ProductListAdapterCallback callback) {
        this.productsArrayList = productsArrayList;
        this.context          = context;
        this.callback         = callback;
        selectedSize = new HashMap<String, Integer>();
    }

    /**
     * Class for MovieViewDetails ViewHolder
     */
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName,txtPrice;
        public ImageView imgPhoto;
        public Button btnAddToCart;
        public HorizontalDataSetPicker horizontalPickerSize;

        public ProductsViewHolder(View view ) {
            super(view);
            txtProductName = (TextView)view.findViewById(R.id.txtProductName);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            imgPhoto = (ImageView)view.findViewById(R.id.imgPhoto);
            btnAddToCart = (Button)view.findViewById(R.id.btnAddToCart);
            horizontalPickerSize = (HorizontalDataSetPicker)view.findViewById(R.id.horizontalPickerSize);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int capsuleType) {
        return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Product product = productsArrayList.get(position);

        final ProductsViewHolder productViewHolder = (ProductsViewHolder) holder;
        productViewHolder.txtProductName.setText(product.getItemName());
        productViewHolder.txtPrice.setText("$"+Integer.toString(product.getItemPrice()));

        productViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onItemClicked(productsArrayList.get(position),position);
            }
        });

        productViewHolder.btnAddToCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onItemAddToCart(productsArrayList.get(position), productViewHolder.horizontalPickerSize.getValueInt());
            }
        });

        productViewHolder.horizontalPickerSize.setDataSetInt(productsArrayList.get(position).getSizes());

        ImageLazyLoaderManager.getInstance().setCallback(this);
        ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(productViewHolder.imgPhoto,Constant.URL_IMAGE_LINK.replace("[SLUG]",product.getImageArrayList().get(0)),ContextCompat.getDrawable(context, R.mipmap.ic_launcher)));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    @Override
    public void onImageDownloaded(ImageRequestData imageRequestData) {
        imageRequestData.getImageView().setImageDrawable(imageRequestData.getDownloadedDrawable());
    }

    @Override
    public void onImageDownloadError(String error) {

    }
}