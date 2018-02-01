package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API;

import android.os.AsyncTask;

import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIParams;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.NetworkTransactionUtility;

/**
 * Asyntask class to handle network transactions
 *
 * Created by Ruel Grajo on 10/13/2016.
 */
public class HTTPAsynctask extends AsyncTask<Void, Void, Object> {

    APICallback callback;
    APIParams apiParams;

    public HTTPAsynctask(APIParams apiParams, APICallback listener){
        this.callback = listener;
        this.apiParams          = apiParams;
    }

    /**
     * Interface for HTTPAsynctask callback
     */
    public interface APICallback {
        void onAPIRequestStarted(APIParams apiParams);
        void onAPiRequestFinished(Object obj, APIParams apiParams);
        void onAPIRequestCancelled(APIParams apiParams);
    }

    @Override
    protected Object doInBackground(Void... params) { // response from server.
        if(apiParams.isMultipart()){
            return NetworkTransactionUtility.executeMultiPartRequest(apiParams);
        }
        else{
            if(apiParams.isHTTPS())
                return NetworkTransactionUtility.executeRequestHTTPS(apiParams);
            else
                return NetworkTransactionUtility.executeRequest(apiParams);
        }
    }

    @Override
    protected void onPreExecute() {
        callback.onAPIRequestStarted(apiParams);
    }

    @Override
    protected void onPostExecute(Object o) {
        callback.onAPiRequestFinished(o,apiParams);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        callback.onAPIRequestCancelled(apiParams);
    }
}
