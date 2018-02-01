package com.heliossoftwaredeveloper.heliosshoppingcart.Utilities.API.Model;

import java.io.File;

/**
 * Created by Ruel Grajo on 18/10/2016.
 */
public class MultipartParams {

    private String fieldName, fieldValue;
    private File file;

    public MultipartParams(String fieldName, String fieldValue, File file){
        this.fieldName          = fieldName;
        this.fieldValue         = fieldValue;
        this.file               = file;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public File getFile() {
        return file;
    }
}
