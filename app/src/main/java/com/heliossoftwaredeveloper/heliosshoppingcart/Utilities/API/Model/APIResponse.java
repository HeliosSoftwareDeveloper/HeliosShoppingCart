package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model;

/**
 * Created by Ruel Grajo on 18/10/2016.
 */
public class APIResponse {

    private String API_RESPONSE;
    private int API_RESPONSE_CODE;
    private String API_ERROR_MESSAGE;


    public String getAPI_RESPONSE() {
        return API_RESPONSE;
    }

    public void setAPI_RESPONSE(String API_RESPONSE) {
        this.API_RESPONSE = API_RESPONSE;
    }

    public String getAPI_ERROR_MESSAGE() {
        return API_ERROR_MESSAGE;
    }

    public void setAPI_ERROR_MESSAGE(String API_ERROR_MESSAGE) {
        this.API_ERROR_MESSAGE = API_ERROR_MESSAGE;
    }

    public int getAPI_RESPONSE_CODE() {
        return API_RESPONSE_CODE;
    }

    public void setAPI_RESPONSE_CODE(int API_RESPONSE_CODE) {
        this.API_RESPONSE_CODE = API_RESPONSE_CODE;
    }
}
