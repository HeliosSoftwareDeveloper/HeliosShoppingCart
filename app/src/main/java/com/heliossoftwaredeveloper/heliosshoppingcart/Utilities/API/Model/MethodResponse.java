package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model;

/**
 * Created by Ruel Grajo on 18/10/2016.
 */
public class MethodResponse{
    private String RESPONSE;
    private boolean RESPONSE_STATUS;

    public String getRESPONSE() {
        return RESPONSE;
    }

    public void setRESPONSE(String RESPONSE) {
        this.RESPONSE = RESPONSE;
    }

    public boolean isRESPONSE_STATUS() {
        return RESPONSE_STATUS;
    }

    public void setRESPONSE_STATUS(boolean RESPONSE_STATUS) {
        this.RESPONSE_STATUS = RESPONSE_STATUS;
    }
}