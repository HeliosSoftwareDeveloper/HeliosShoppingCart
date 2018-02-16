package com.heliossoftwaredeveloper.heliosshoppingcart.User.Transaction.Model;

import java.io.Serializable;

/**
 * Created by rngrajo on 14/02/2018.
 */

public class User implements Serializable{

    private String fullName, phoneNumber, email, deliveryAddress;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
