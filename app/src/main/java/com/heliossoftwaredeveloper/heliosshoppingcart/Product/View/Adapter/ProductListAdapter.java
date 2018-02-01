package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter;

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
     * @param movieArrayList
     *
     */
    public ProductListAdapter(ArrayList<Product> movieArrayList, Activity context, ProductListAdapterCallback callback) {
        this.productsArrayList = movieArrayList;
        this.context          = context;
        this.callback         = callback;
        selectedSize = new HashMap<String, Integer>();
    }

    /**
     * Class for MovieViewDetails ViewHolder
     */
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName,txtSize,txtPrice;
        public ImageView imgPhoto;
        public ImageButton imgBtnBefore, imgBtnNext;
        public Button btnAddToCart;

        public ProductsViewHolder(View view ) {
            super(view);
            txtProductName = (TextView)view.findViewById(R.id.txtProductName);
            txtSize = (TextView)view.findViewById(R.id.txtSize);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            imgPhoto = (ImageView)view.findViewById(R.id.imgPhoto);
            imgBtnBefore = (ImageButton)view.findViewById(R.id.imgBtnBefore);
            imgBtnNext = (ImageButton)view.findViewById(R.id.imgBtnNext);
            btnAddToCart = (Button)view.findViewById(R.id.btnAddToCart);
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
        productViewHolder.txtSize.setText(Integer.toString(product.getSizes().get(0)));
        productViewHolder.txtPrice.setText("$"+Integer.toString(product.getItemPrice()));

        productViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onItemClicked(productsArrayList.get(position),position);
            }
        });

        productViewHolder.imgBtnBefore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int index = productsArrayList.get(position).getSizes().indexOf(Integer.valueOf(productViewHolder.txtSize.getText().toString()));
                if(index > 0)
                    productViewHolder.txtSize.setText(Integer.toString(productsArrayList.get(position).getSizes().get(index-1)));
            }
        });

        productViewHolder.imgBtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int index = productsArrayList.get(position).getSizes().indexOf(Integer.valueOf(productViewHolder.txtSize.getText().toString()));
                if(index >= 0 && index < (productsArrayList.get(position).getSizes().size() - 1))
                    productViewHolder.txtSize.setText(Integer.toString(productsArrayList.get(position).getSizes().get(index+1)));
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