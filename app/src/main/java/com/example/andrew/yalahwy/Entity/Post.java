package com.example.andrew.yalahwy.Entity;

import com.google.firebase.firestore.FieldValue;

import java.util.Date;


public class Post {
    private String postID;
    private String userID;
    private String postDesc;
    //private String searchType;
    private String region;
   /* private String Q1;
    private String Q2;*/
    private String postImage;
    private String imageThumb;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date timestamp;

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public Post(){

    }

    public Post(String postID, String userID, String postDesc, String region, String postImage, String imageThumb) {
        this.postID = postID;
        this.userID = userID;
        this.postDesc = postDesc;
        this.region = region;
        this.postImage = postImage;
        this.imageThumb = imageThumb;
    }


    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

   /* public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }*/

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }



 /*   public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String q2) {
        Q2 = q2;
    }*/
}
