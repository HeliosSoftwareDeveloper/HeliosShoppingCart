package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.http.HttpResponseCache;

import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIParams;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.APIResponse;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.MethodResponse;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model.MultipartParams;
import com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.SSLCertificate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Utility class to handle network transactions
 *
 * Created by Ruel Grajo on 10/13/2016.
 */
public class NetworkTransactionUtility {

    public static boolean isCacheEnable                         = false;

    public static final String CHARSET_NAME                     = "iso-8859-1";
    public static final String REQUEST_TYPE_GET                 = "GET";
    public static final String REQUEST_TYPE_POST                = "POST";
    public static final String REQUEST_TYPE_PUT                 = "PUT";
    public static final String REQUEST_TYPE_DELETE              = "DELETE";
    public static final int BUFFER_READER_SIZE                  = 8;
    public static final int ERROR_POST_PARAMS                   = 2000;
    public static final int ERROR_READING_SERVER_RESPONSE       = 2001;
    public static final int ERROR_HTTP_CONNECTION               = 2002;

    private static final String LINE_FEED                       = "\r\n";
    private static final String CHARSET_UTF                     = "UTF-8";
    private static final String CONTENT_TYPE_KEY                = "Content-Type";
    private static final String BOUNDARY_FORM_DATA              = "multipart/form-data; boundary=";
    private static final String BOUNDARY_DASHES                 = "--";
    private static final String BOUNDARY_EQUALS                 = "===";
    private static final String CONTENT_DISPOSITION_FORM_DATA   = "Content-Disposition: form-data; name=\"";
    private static final String CONTENT_TYPE_CHARSET            = "Content-Type: text/plain; CHARSET_UTF=";
    private static final String CONTENT_TYPE                    = "Content-Type: ";
    private static final String CONTENT_TRANSFER_ENCODING       = "Content-Transfer-Encoding: binary";
    private static final String CONTENT_FILENAME                = "\"; filename=\"";
    private static final String BOUNDARY_SLASH                  = "\"";
    private static final String CACHE_CONTROL                   = "Cache-Control";
    private static final String HTTP_KEY_CACHE                  = "http";
    private static final String NO_CACHE                        = "no-cache";
    private static final String MAX_STATE                       = "max-stale=";

    private static final int BUFFER_SIZE                        = 4096;
    private static final int MAX_STALE                          = 60 * 60 * 24 * 28; // tolerate 4-weeks stale

    public static final String ERROR_MESSAGE_UNABLE_TO_CONNECT  = "Unable to connect to server.";
    public static final String SUCCESS_MESSAGE                  = "Success";

    /**
     * Method to execute HTTP request
     *
     * @param apiParams
     *
     * @return APIResponse object generated base on the response from server
     */
    public static APIResponse executeRequest(APIParams apiParams){
        APIResponse apiResponse = new APIResponse();
        try{
            URL url = new URL(apiParams.getRequestUrl());
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod(apiParams.getRequestType());
            httpConn.setDoInput(true);
            httpConn.setUseCaches(isCacheEnable);

            //set request headers
            setHeaders(httpConn,apiParams.getListHeaders());

            if(apiParams.getRequestType().equals(REQUEST_TYPE_POST) || apiParams.getRequestType().equals(REQUEST_TYPE_PUT)
                    || apiParams.getRequestType().equals(REQUEST_TYPE_DELETE)){
                httpConn.setDoOutput(true);

                //if url params has a value, set the post parameter of HttpURLConnection
                if(apiParams.getRequestPostParams()!= null){
                    MethodResponse postParams = setPostParams(httpConn,apiParams.getRequestPostParams());
                   if(!postParams.isRESPONSE_STATUS()){
                       apiResponse.setAPI_RESPONSE_CODE(ERROR_POST_PARAMS);
                       apiResponse.setAPI_ERROR_MESSAGE(postParams.getRESPONSE());
                       return apiResponse;
                   }
                }
            }

            MethodResponse readhttp = readHTTPResponse(httpConn); //read http response

            if(readhttp.isRESPONSE_STATUS()){
                apiResponse.setAPI_RESPONSE_CODE(httpConn.getResponseCode());
                apiResponse.setAPI_RESPONSE(readhttp.getRESPONSE());
            }
            else{
                apiResponse.setAPI_RESPONSE_CODE(ERROR_READING_SERVER_RESPONSE);
                apiResponse.setAPI_ERROR_MESSAGE(readhttp.getRESPONSE());
            }

        }
        catch(Exception ex){
            apiResponse.setAPI_ERROR_MESSAGE(ERROR_MESSAGE_UNABLE_TO_CONNECT);
            apiResponse.setAPI_RESPONSE_CODE(ERROR_HTTP_CONNECTION);
        }
        return apiResponse;
    }

    /**
     * Method to execute HTTPS request
     *
     * @param apiParams
     *
     * @return APIResponse object generated base on the response from server
     */
    public static APIResponse executeRequestHTTPS(APIParams apiParams){
        APIResponse apiResponse = new APIResponse();
        try{
            SSLCertificate.set();
            URL url = new URL(apiParams.getRequestUrl());
            HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
            httpConn.setConnectTimeout(10000);
            httpConn.setRequestMethod(apiParams.getRequestType());
            httpConn.setDoInput(true);
            httpConn.setUseCaches(isCacheEnable);

            //set request headers
            setHeaders(httpConn, apiParams.getListHeaders());

            if(apiParams.getRequestType().equals(REQUEST_TYPE_POST) || apiParams.getRequestType().equals(REQUEST_TYPE_PUT)
                    || apiParams.getRequestType().equals(REQUEST_TYPE_DELETE)){
                httpConn.setDoOutput(true);

                //if url params has a value, set the post parameter of HttpURLConnection
                if(apiParams.getRequestPostParams()!= null){
                    MethodResponse postParams = setPostParams(httpConn,apiParams.getRequestPostParams());
                    if(!postParams.isRESPONSE_STATUS()){
                        apiResponse.setAPI_RESPONSE_CODE(ERROR_POST_PARAMS);
                        apiResponse.setAPI_ERROR_MESSAGE(postParams.getRESPONSE());
                        return apiResponse;
                    }
                }
            }

            MethodResponse readhttp = readHTTPResponse(httpConn); //read http response

            if(readhttp.isRESPONSE_STATUS()){
                apiResponse.setAPI_RESPONSE_CODE(httpConn.getResponseCode());
                apiResponse.setAPI_RESPONSE(readhttp.getRESPONSE());
            }
            else{
                apiResponse.setAPI_RESPONSE_CODE(ERROR_READING_SERVER_RESPONSE);
                apiResponse.setAPI_ERROR_MESSAGE(readhttp.getRESPONSE());
            }
        }
        catch(Exception ex){
            apiResponse.setAPI_ERROR_MESSAGE(ERROR_MESSAGE_UNABLE_TO_CONNECT);
            apiResponse.setAPI_RESPONSE_CODE(ERROR_HTTP_CONNECTION);
        }
        return apiResponse;
    }

    /**
     * Method to execute HTTP MULTIPART PARAMS
     *
     * @param apiParams
     *
     * @return APIResponse object generated base on the response from server
     */
    public static APIResponse executeMultiPartRequest(APIParams apiParams) {
        String boundary;
        HttpURLConnection httpConn;
        OutputStream outputStream;
        PrintWriter writer;

        APIResponse apiResponse = new APIResponse();
        // creates a unique boundary based on time stamp
        boundary = BOUNDARY_EQUALS + System.currentTimeMillis() + BOUNDARY_EQUALS;
        try{
            httpConn = (HttpURLConnection) new URL(apiParams.getRequestUrl()).openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty(CONTENT_TYPE_KEY, BOUNDARY_FORM_DATA + boundary);

            if(apiParams.getListHeaders() != null && apiParams.getListHeaders().size() > 0){
                Iterator myVeryOwnIterator = apiParams.getListHeaders().keySet().iterator();
                while(myVeryOwnIterator.hasNext()) {
                    String key=(String)myVeryOwnIterator.next();
                    String value=(String)apiParams.getListHeaders().get(key);
                    httpConn.addRequestProperty(key, value);
                }
            }
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, CHARSET_UTF),true);

            setMultipartParams( boundary, outputStream, writer, apiParams.getListMultipartParams());

            StringBuffer response = new StringBuffer();

            writer.append(LINE_FEED).flush();
            writer.append(BOUNDARY_DASHES + boundary + BOUNDARY_DASHES).append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                httpConn.disconnect();
                apiResponse.setAPI_RESPONSE_CODE(status);
                apiResponse.setAPI_RESPONSE(response.toString());
            } else {
                apiResponse.setAPI_RESPONSE_CODE(status);
                apiResponse.setAPI_ERROR_MESSAGE(ERROR_MESSAGE_UNABLE_TO_CONNECT);
            }

        }
        catch(Exception ex){
            apiResponse.setAPI_RESPONSE_CODE(500);
            apiResponse.setAPI_ERROR_MESSAGE(ERROR_MESSAGE_UNABLE_TO_CONNECT);
        }

        return apiResponse;
    }

    /**
     * Method to set HTTP MULTIPART params
     *
     * @param boundary
     * @param outputStream
     * @param writer
     * @param multipartParamsArrayList
     *
     */
    public static void setMultipartParams(String boundary, OutputStream outputStream, PrintWriter writer, ArrayList<MultipartParams> multipartParamsArrayList)throws Exception {
        if(multipartParamsArrayList == null || multipartParamsArrayList.size() == 0)
            return;
        for(MultipartParams apiMultipartParams : multipartParamsArrayList) {
            if(apiMultipartParams.getFile() == null){
                writer.append(BOUNDARY_DASHES + boundary).append(LINE_FEED);
                writer.append(CONTENT_DISPOSITION_FORM_DATA + apiMultipartParams.getFieldName() + BOUNDARY_SLASH)
                        .append(LINE_FEED);
                writer.append(CONTENT_TYPE_CHARSET + CHARSET_UTF).append(
                        LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(apiMultipartParams.getFieldValue()).append(LINE_FEED);
                writer.flush();
            }
            else{//file
                String fileName = apiMultipartParams.getFile().getName();
                writer.append(BOUNDARY_DASHES + boundary).append(LINE_FEED);
                writer.append(CONTENT_DISPOSITION_FORM_DATA + apiMultipartParams.getFieldName() + CONTENT_FILENAME + fileName + BOUNDARY_SLASH).append(LINE_FEED);
                writer.append(CONTENT_TYPE+ URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
                writer.append(CONTENT_TRANSFER_ENCODING).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                FileInputStream inputStream = new FileInputStream(apiMultipartParams.getFile());
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();

                writer.append(LINE_FEED);
                writer.flush();
            }

        }
    }

    /**
     * Method to set headers
     *
     * @param httpConn
     * @param listHeaders
     *
     */
    public static void setHeaders(HttpURLConnection httpConn, HashMap<String, String> listHeaders){
        /*httpConn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpConn.addRequestProperty("Content-Language", "en-US");*/
        if(!isCacheEnable)
        httpConn.addRequestProperty(CACHE_CONTROL,NO_CACHE);
        else{
            httpConn.addRequestProperty(CACHE_CONTROL,MAX_STATE  + MAX_STALE);
        }
        if(listHeaders == null || listHeaders.size() == 0)
            return;

        Iterator myVeryOwnIterator = listHeaders.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            String key=(String)myVeryOwnIterator.next();
            String value=(String)listHeaders.get(key);
            httpConn.addRequestProperty(key, value);
        }
    }

    /**
     * Method to read response from HTTP/HTTPS Transaction
     *
     * @param httpConn
     *
     * @return MethodResponse object generated based on the response from server
     *
     */
    public static MethodResponse readHTTPResponse(HttpURLConnection httpConn) {
        MethodResponse methodResponse = new MethodResponse();
        InputStream inputStream = null;
        StringBuilder stringBuilder = null;

        try{
            if(httpConn.getResponseCode() >= 200 && httpConn.getResponseCode() < 400){
                inputStream = httpConn.getInputStream();
            }
            else{
                inputStream = httpConn.getErrorStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,CHARSET_NAME), BUFFER_READER_SIZE);

            stringBuilder = new StringBuilder();
            stringBuilder.append(reader.readLine() + "\n");

            String line = "";

            while((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            inputStream.close();

            methodResponse.setRESPONSE(stringBuilder.toString());
            methodResponse.setRESPONSE_STATUS(true);
        }
        catch(Exception ex){
            methodResponse.setRESPONSE(ex.toString());
            methodResponse.setRESPONSE_STATUS(false);
        }

        return methodResponse;
    }

    /**
     * Method to set HTTP Post Params
     *
     * @param httpConn
     * @param apiParams
     *
     * @return MethodResponse object generated to check if setting params is successful
     *
     */
    public static MethodResponse setPostParams(HttpURLConnection httpConn, String apiParams){
        MethodResponse methodResponse = new MethodResponse();
        try{
            DataOutputStream dataOutputStream = new DataOutputStream(httpConn.getOutputStream());
            dataOutputStream.writeBytes(apiParams);
            dataOutputStream.flush();
            dataOutputStream.close();

            methodResponse.setRESPONSE(SUCCESS_MESSAGE);
            methodResponse.setRESPONSE_STATUS(true);
        }
        catch(Exception ex){
            methodResponse.setRESPONSE(ex.toString());
            methodResponse.setRESPONSE_STATUS(false);
        }
        return methodResponse;
    }

    /**
     * Method to install http cache
     *
     * @param context
     *
     */
    public static void installCache(Context context){
        try{
            File httpCacheDir = new File(context.getCacheDir(), HTTP_KEY_CACHE);
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB

            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        }
        catch(Exception ex){
        }
    }

    /**
     * Method to flush cache
     */
    public static void flushCache(){
        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache != null) {
            cache.flush();
        }
    }

    /**
     * Method to download drawable from URL
     *
     * @param urlString
     *
     * @return downloaded drawable
     */
    public static Drawable downloadImageFromURL(String urlString){
        try {
            URL url = new URL(urlString);
            URLConnection connection;
            connection = url.openConnection();
            connection.setUseCaches(true);
            connection.connect();
            InputStream is = connection.getInputStream();

            Drawable drawable = Drawable.createFromStream(is, urlString);
            return drawable;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
