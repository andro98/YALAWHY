package com.example.andrew.yalahwy.Entity;

import android.net.Uri;

public class Person {
    private String FirstName;
    private String LastName;
    private String Address;
    private String PhoneNumber;
    private String User_id;
    private String UserEmail;
    private String UserPSW;
    private Uri mainImageURI = null;


    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPSW() {
        return UserPSW;
    }

    public void setUserPSW(String userPSW) {
        UserPSW = userPSW;
    }

    public Uri getMainImageURI() {
        return mainImageURI;
    }

    public void setMainImageURI(Uri mainImageURI) {
        this.mainImageURI = mainImageURI;
    }
}
