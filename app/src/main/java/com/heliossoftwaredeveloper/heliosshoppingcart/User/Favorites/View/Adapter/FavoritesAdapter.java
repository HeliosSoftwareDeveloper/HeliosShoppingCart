package com.heliossoftwaredeveloper.heliosshoppingcart.User.Favorites.View.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.R;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.Constant;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.ImageLazyLoaderManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

import java.util.ArrayList;

/**
 * Adapter class for FavoritesProductsList RecyclerView
 *
 * Created by rngrajo on 30/01/2018.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ImageLazyLoaderManager.ImageLazyLoaderManagerCallback {

    private ArrayList<Product> productsArrayList;
    private Activity context;
    private ProductListAdapterCallback callback;

    /**
     * interface for FavoritesAdapter callback
     */
    public interface ProductListAdapterCallback {
        void onItemClicked(Product product, int position);
    }

    /**
     * Constructor for FavoritesAdapter
     *
     * @param callback
     * @param context
     * @param productsArrayList
     *
     */
    public FavoritesAdapter(ArrayList<Product> productsArrayList, Activity context, ProductListAdapterCallback callback) {
        this.productsArrayList = productsArrayList;
        this.context          = context;
        this.callback         = callback;
    }

    /**
     * Class for MovieViewDetails ViewHolder
     */
    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPhoto;
        public TextView txtProductName;

        public ProductsViewHolder(View view ) {
            super(view);
            imgPhoto = (ImageView)view.findViewById(R.id.imgPhoto);
            txtProductName = (TextView)view.findViewById(R.id.txtProductName);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int capsuleType) {
        return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_product, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Product product = productsArrayList.get(position);

        final ProductsViewHolder productViewHolder = (ProductsViewHolder) holder;

        productViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onItemClicked(productsArrayList.get(position),position);
            }
        });

        productViewHolder.txtProductName.setText(product.getItemName());

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