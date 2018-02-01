package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruel Grajo on 18/10/2016.
 */
public class APIParams {

    private int requestCaller;
    private String requestUrl, requestType, requestPostParams;
    private HashMap<String, String> listHeaders;
    private boolean isMultipart;

    private ArrayList<MultipartParams> listMultipartParams;

    private boolean isHTTPS;

    public ArrayList<MultipartParams> getListMultipartParams() {
        return listMultipartParams;
    }

    public void setListMultipartParams(ArrayList<MultipartParams> listMultipartParams) {
        this.listMultipartParams = listMultipartParams;
    }

    public boolean isMultipart() {
        return isMultipart;
    }

    public void setMultipart(boolean multipart) {
        isMultipart = multipart;
    }

    public void setHTTPS(boolean HTTPS) {
        isHTTPS = HTTPS;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestPostParams() {
        return requestPostParams;
    }

    public void setRequestPostParams(String requestPostParams) {
        this.requestPostParams = requestPostParams;
    }

    public HashMap<String, String> getListHeaders() {
        return listHeaders;
    }

    public void setListHeaders(HashMap<String, String> listHeaders) {
        this.listHeaders = listHeaders;
    }

    public int getRequestCaller() {
        return requestCaller;
    }

    public void setRequestCaller(int requestCaller) {
        this.requestCaller = requestCaller;
    }

    public boolean isHTTPS() {
        return isHTTPS;
    }

    public void setIsHTTPS(boolean isHTTPS) {
        this.isHTTPS = isHTTPS;
    }

}
