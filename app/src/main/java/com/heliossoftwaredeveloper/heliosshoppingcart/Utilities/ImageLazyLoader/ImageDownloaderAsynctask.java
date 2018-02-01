package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.NetworkTransactionUtility;

/**
 * Asyntask class to handle image download transactions
 *
 * Created by Ruel Grajo on 10/13/2016.
 */
public class ImageDownloaderAsynctask extends AsyncTask<Void, Void, Object> {

    private ImageDownloaderAsyncCallback callback;
    private ImageRequestData imageRequestData;

    public ImageDownloaderAsynctask(ImageRequestData imageRequestData, ImageDownloaderAsyncCallback callback){
        this.callback                  = callback;
        this.imageRequestData          = imageRequestData;
    }

    /**
     * Interface for ImageDownloaderAsynctask callback
     */
    public interface ImageDownloaderAsyncCallback {
        void onImageDownloadRequestStarted(ImageRequestData imageRequestData);
        void onImageDownloadRequestFinished(ImageRequestData imageRequestData, Drawable drawable);
        void onImageDownloadRequestCancelled(ImageRequestData imageRequestData);
    }

    @Override
    protected Object doInBackground(Void... params) { // response from server.
        return NetworkTransactionUtility.downloadImageFromURL(imageRequestData.getUrl());
    }

    @Override
    protected void onPreExecute() {
        callback.onImageDownloadRequestStarted(imageRequestData);
    }

    @Override
    protected void onPostExecute(Object o) {
        callback.onImageDownloadRequestFinished(imageRequestData,(Drawable)o);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        callback.onImageDownloadRequestCancelled(imageRequestData);
    }
}
