package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API;

import android.content.Context;
import android.os.AsyncTask;

import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIParams;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIResponse;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.NetworkTransactionUtility;

/**
 * Manager class to handle API transactions
 *
 * Created by Ruel Grajo on 19/10/2016.
 */

public class APIManager implements HTTPAsynctask.APICallback {

    private HTTPAsynctask httpTask;
    private boolean isAutoRetry = false;
    private int retryCount = 0;
    private int maxRetryCount = 2;
    private APIManagerCallback callback;

    /**
     * Interface for HTTPAsynctask callback
     */
    public interface APIManagerCallback {
        void onAPIStarted(APIParams apiParams);
        void onAPIFinished(Object obj, APIParams apiParams);
        void onAPICancelled(APIParams apiParams);
    }

    /**
     * Constructor for cache disabled request
     */
    public APIManager(){
    }

    /**
     * Constructor for cache enable request
     *
     * @param context
     */
    public APIManager(Context context){
        NetworkTransactionUtility.isCacheEnable = true;
        NetworkTransactionUtility.installCache(context);
    }

    /**
     * Method to trigger http flush cache
     */
    public void flushHTTPCache(){
        NetworkTransactionUtility.flushCache();
    }

    /**
     * Method to trigger api request request
     *
     * @param  apiParams
     * @param listener
     *
     */
    public void execute(APIParams apiParams, APIManagerCallback listener){
        this.callback = listener;
        httpTask = new HTTPAsynctask(apiParams,this);
        httpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Method to cancel pending HTTPAsynctask request
     */
    public void cancel(){
        if(httpTask!=null)
        httpTask.cancel(true);
    }

    /**
     * Method to set max retry count
     */
    public void setRetryCount(int count){
        maxRetryCount = count;
    }

    /**
     * Method to trigger network fail auto retry
     */
    public void setAutoRetry(boolean status){
        isAutoRetry = status;
    }

    @Override
    public void onAPIRequestStarted(APIParams apiParams) {
        callback.onAPIStarted(apiParams);
    }

    @Override
    public void onAPiRequestFinished(Object obj, APIParams apiParams) {
        httpTask = null;
        if(isAutoRetry){
            if(((APIResponse)obj).getAPI_RESPONSE_CODE() != 200){
                if(retryCount >= maxRetryCount){
                    retryCount = 0;
                    callback.onAPIFinished(obj, apiParams);
                }
                else{
                    retryCount += 1;
                    httpTask = new HTTPAsynctask(apiParams,this);
                }
            }
        }
        else
            callback.onAPIFinished(obj, apiParams);
    }

    @Override
    public void onAPIRequestCancelled(APIParams apiParams) {
        httpTask = null;
        callback.onAPICancelled(apiParams);
    }
}
