package com.heliossoftwaredeveloper.heliosshoppingcart.Product.Interactor;

import com.heliossoftwaredeveloper.heliosshoppingcart.Product.Model.Product;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.APIManager;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIParams;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rngrajo on 01/02/2018.
 */

public class ProductInteractorImpl implements ProductInteractor{

    private APIManager apiManagerGetProducts;

    @Override
    public void getProducts(APIParams apiParams,final OnGetProductsCallback callback) {
        apiManagerGetProducts = new APIManager();
        apiManagerGetProducts.execute(apiParams, new APIManager.APIManagerCallback() {
            @Override
            public void onAPIStarted(APIParams apiParams) {
            }

            @Override
            public void onAPIFinished(Object obj, APIParams apiParams) {
                if(obj == null) {//null response, showErrorMessage dialog
                    callback.onGetProductsError("Error","Unable to connect to server.",apiParams);
                    return;
                }
                APIResponse apiResponse = (APIResponse)obj;
                switch (apiResponse.getAPI_RESPONSE_CODE()){
                    case 200:
                        try{
                            Product product;
                            ArrayList<Product> productArrayList = new ArrayList<>();
                            ArrayList<Integer> sizeArrayList;
                            ArrayList<String> colorSchemeArrayList, imageArrayList;

                            JSONObject jsonResponse = new JSONObject(apiResponse.getAPI_RESPONSE());
                            JSONArray jsonArrayProduct = jsonResponse.getJSONObject("data").getJSONArray("shoeList");
                            for(int i = 0; i < jsonArrayProduct.length(); i++){
                                JSONObject jsonObjectProduct = jsonArrayProduct.getJSONObject(i);
                                product = new Product();
                                sizeArrayList = new ArrayList<>();
                                colorSchemeArrayList = new ArrayList<>();
                                imageArrayList = new ArrayList<>();

                                JSONArray jsonArrayColorScheme = jsonObjectProduct.getJSONArray("colorScheme");
                                JSONArray jsonArraySizes = jsonObjectProduct.getJSONArray("sizes");
                                JSONArray jsonArrayItemImages = jsonObjectProduct.getJSONArray("itemImages");

                                for(int a = 0; a < jsonArrayColorScheme.length(); a++){
                                    colorSchemeArrayList.add(jsonArrayColorScheme.optString(a, ""));
                                }
                                for(int a = 0; a < jsonArraySizes.length(); a++){
                                    sizeArrayList.add(jsonArraySizes.optInt(a, 0));
                                }
                                for(int a = 0; a < jsonArrayItemImages.length(); a++){
                                    imageArrayList.add(jsonArrayItemImages.optString(a, ""));
                                }

                                //set values
                                product.setItemName(jsonObjectProduct.optString("itemName", ""));
                                product.setItemCategory(jsonObjectProduct.optString("itemCategory", ""));
                                product.setItemcode(jsonObjectProduct.optString("itemcode", ""));
                                product.setColorSchemeArrayList(colorSchemeArrayList);
                                product.setSizes(sizeArrayList);
                                product.setImageArrayList(imageArrayList);
                                product.setReleaseDate(jsonObjectProduct.optString("releaseDate", ""));
                                product.setItemPrice(jsonObjectProduct.optInt("itemPrice", 0));
                                product.setItemDescription(jsonObjectProduct.optString("itemDescription", ""));

                                //set movie object to arraylist
                                productArrayList.add(product);
                            }
                            callback.onGetProductsSuccess(productArrayList);
                        }
                        catch(Exception ex){
                            callback.onGetProductsError("Error","Unable to parse data.",apiParams);
                        }
                        apiManagerGetProducts = null;
                        break;
                    default:
                        callback.onGetProductsError("Error",apiResponse.getAPI_ERROR_MESSAGE(),apiParams);
                        apiManagerGetProducts = null;
                        break;
                }
            }
            @Override
            public void onAPICancelled(APIParams apiParams) {
                callback.onGetProductsCanceled();
                apiManagerGetProducts = null;
            }
        });
    }

    /**
     * method to be called when the context caller has been destroyed.
     * Cancel all pending api request to avoid fatal exception
     */

    @Override
    public void onDestroy() {
        if(apiManagerGetProducts != null){
            apiManagerGetProducts.cancel();
            apiManagerGetProducts = null;
        }
    }
}
