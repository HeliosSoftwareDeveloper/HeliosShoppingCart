package com.heliossoftwaredeveloper.heliosshoppingcart.Product.View.Adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heliossoftwaredeveloper.heliosshoppingcart.Commons.CarouselViewHolder;
import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.HorizontalDataSetPicker;
import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.ImageCarouseDataHolder;
import com.heliossoftwaredeveloper.heliosshoppingcart.CustomViews.ImageCarousel;
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

    private ArrayList<Object> productsArrayList;
    private Activity context;
    private ProductListAdapterCallback callback;
    private static final int TYPE_PRODUCT = 10001;
    private static final int TYPE_CAROUSEL = 10002;


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
    public ProductListAdapter(ArrayList<Object> productsArrayList, Activity context, ProductListAdapterCallback callback) {
        this.productsArrayList = productsArrayList;
        this.context          = context;
        this.callback         = callback;
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
        if(capsuleType == TYPE_PRODUCT)
            return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false));
        else
            return new CarouselViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(productsArrayList.get(position) instanceof  Product){
            Product product = (Product) productsArrayList.get(position);

            final ProductsViewHolder productViewHolder = (ProductsViewHolder) holder;
            productViewHolder.txtProductName.setText(product.getItemName());
            productViewHolder.txtPrice.setText("$"+Integer.toString(product.getItemPrice()));

            productViewHolder.imgPhoto.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    callback.onItemClicked((Product) productsArrayList.get(position),position);
                }
            });

            productViewHolder.btnAddToCart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    callback.onItemAddToCart((Product) productsArrayList.get(position), productViewHolder.horizontalPickerSize.getValueInt());
                }
            });

            productViewHolder.horizontalPickerSize.setDataSetInt(((Product)productsArrayList.get(position)).getSizes());

            ImageLazyLoaderManager.getInstance().setCallback(this);
            ImageLazyLoaderManager.getInstance().loadImage(new ImageRequestData(productViewHolder.imgPhoto,Constant.URL_IMAGE_LINK.replace("[SLUG]",product.getImageArrayList().get(0)),ContextCompat.getDrawable(context, R.mipmap.ic_launcher)));
        }
        else{
            ArrayList<ImageCarouseDataHolder> imageCarouseDataHolderArrayList = (ArrayList<ImageCarouseDataHolder>) productsArrayList.get(position);
            final CarouselViewHolder carouselViewHolder = (CarouselViewHolder) holder;
            carouselViewHolder.imageCarousel.setData(imageCarouseDataHolderArrayList);
            carouselViewHolder.imageCarousel.autoplay(true);
            carouselViewHolder.imageCarousel.showRadioButtons();
            carouselViewHolder.imageCarousel.setListener(new ImageCarousel.ImageCarouselListener() {
                @Override
                public void onCarouselItemClicked(Object object) {
                    callback.onItemClicked((Product)object,position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return productsArrayList.get(position) instanceof  Product ? TYPE_PRODUCT : TYPE_CAROUSEL;
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