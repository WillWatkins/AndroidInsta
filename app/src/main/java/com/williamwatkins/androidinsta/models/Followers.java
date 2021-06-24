package com.williamwatkins.androidinsta.models;

import android.graphics.Bitmap;

public class Followers {

    private String username;
    private String displayName;
    private boolean following;
    private String userUID;
    private Bitmap profilePhoto;


    public Followers(){

    }

    public Followers(String username, String displayName, String id, Bitmap photo){
        this.displayName = displayName;
        this.username = username;
        this.userUID = id;
        this.profilePhoto = photo;
    }

    public Followers(String username, String displayName, String id){
        this.displayName = displayName;
        this.username = username;
        this.userUID = id;
    }



    public Followers(String id){
        this.userUID = id;
    }


    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getUserUID() {
        return userUID;
    }

    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public void setProfilePhoto(Bitmap profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
