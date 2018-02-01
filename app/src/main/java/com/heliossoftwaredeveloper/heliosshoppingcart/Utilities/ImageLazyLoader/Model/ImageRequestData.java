package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by rngrajo on 31/01/2018.
 */

public class ImageRequestData {

    private ImageView imageView;
    private String url;
    private Drawable placeHolder, downloadedDrawable;

    public ImageRequestData (ImageView imageView, String url,Drawable placeHolder){
        this.imageView = imageView;
        this.url = url;
        this.placeHolder = placeHolder;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Drawable getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(Drawable placeHolder) {
        this.placeHolder = placeHolder;
    }

    public Drawable getDownloadedDrawable() {
        return downloadedDrawable;
    }

    public void setDownloadedDrawable(Drawable downloadedDrawable) {
        this.downloadedDrawable = downloadedDrawable;
    }
}
