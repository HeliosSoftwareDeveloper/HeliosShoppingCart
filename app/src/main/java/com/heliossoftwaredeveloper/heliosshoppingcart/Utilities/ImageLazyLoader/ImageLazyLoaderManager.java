package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.ImageLazyLoader.Model.ImageRequestData;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class for lazy image downloader
 *
 * Created by rngrajo on 31/01/2018.
 */

public class ImageLazyLoaderManager {

    private static ImageLazyLoaderManager instance = null;
    private ImageLazyLoaderManagerCallback callback;
    private final Map<String, SoftReference<Drawable>> mapCache = new HashMap<>();
    private final Map<ImageRequestData,ImageDownloaderAsynctask> mapDownloadTask = new HashMap <> ();
    private int maxCacheSize = 100;

    /**
     * interface for ImageLazyLoaderManager callback
     *
     */
    public interface ImageLazyLoaderManagerCallback{
        void onImageDownloaded(ImageRequestData imageRequestData);
        void onImageDownloadError(String error);
    }

    /**
     * method to get ImageLazyLoaderManager instance
     *
     * @return ImageLazyLoaderManager
     */
    public static ImageLazyLoaderManager getInstance() {
        if (instance == null) {
            instance = new ImageLazyLoaderManager();
        }
        return(instance);
    }

    /**
     * method to set the callback
     *
     * @param callback
     */
    public void setCallback(ImageLazyLoaderManagerCallback callback){
        this.callback = callback;
    }

    /**
     * method to execute Image download from url
     *
     * @param imageRequestData
     */
    public void loadImage(ImageRequestData imageRequestData){
        if(mapCache.containsKey(imageRequestData.getUrl())){
            if(mapCache.get(imageRequestData.getUrl()).get() == null){
                mapCache.remove(imageRequestData.getUrl());
            }
            else{
                imageRequestData.setDownloadedDrawable(mapCache.get(imageRequestData.getUrl()).get());
                callback.onImageDownloaded(imageRequestData);
                return;
            }
        }

        imageRequestData.getImageView().setImageDrawable(imageRequestData.getPlaceHolder());
        mapDownloadTask.put(imageRequestData,new ImageDownloaderAsynctask(imageRequestData, new ImageDownloaderAsynctask.ImageDownloaderAsyncCallback(){
            @Override
            public void onImageDownloadRequestStarted(ImageRequestData imageRequestData) {
            }

            @Override
            public void onImageDownloadRequestFinished(ImageRequestData imageRequestData, Drawable drawable) {
                mapDownloadTask.remove(imageRequestData);
                if(callback == null)
                    return;

                if(imageRequestData == null){
                    callback.onImageDownloadError("Could not download");
                }
                else{
                    if(mapCache.size() < maxCacheSize)
                        mapCache.put(imageRequestData.getUrl(),  new SoftReference<Drawable>(drawable));

                    imageRequestData.setDownloadedDrawable(new SoftReference<Drawable>(drawable).get());
                    callback.onImageDownloaded(imageRequestData);
                }
            }
            @Override
            public void onImageDownloadRequestCancelled(ImageRequestData imageRequestData) {
                if(mapDownloadTask.containsKey(imageRequestData))
                    mapDownloadTask.remove(imageRequestData);
            }
        }));
        mapDownloadTask.get(imageRequestData).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * method to cancel all pending request & clear all the hashmap data
     */
    public void cancelAllDownloadRequest(){
        Iterator it = mapDownloadTask.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            mapDownloadTask.get(pair.getKey()).cancel(true);
            it.remove(); // avoids a ConcurrentModificationException
        }
        clearImageCache();
        mapDownloadTask.clear();
    }

    /**
     * method to set the max cache size
     *
     * @param size
     */
    public void setMaxCacheSize(int size){
        maxCacheSize = size;
    }

    /**
     * method to get the max cache size
     *
     * @return maxCacheSize
     */
    public int getMaxCacheSize(){
        return maxCacheSize;
    }

    /**
     * method clearImageMapCache
     */
    public void clearImageCache(){
        mapCache.clear();
    }
}
