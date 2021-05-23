package com.williamwatkins.androidinsta;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;

public class UsersPost {

    private String username;
    private String caption;
    private Bitmap postImageBitmap;
    private String likes;
    private String imageLabel;
    private String usersID;
    private Date date;

    public UsersPost() {
    }

    public UsersPost(String username, String caption, String imageLabel, String usersID) {
        this.username = username;
        this.caption = caption;
        likes = "0";
        this.imageLabel = imageLabel;
        this.usersID = usersID;
    }

    public UsersPost(String username, String caption, String likes, Bitmap bitmap){
        this.username = username;
        this.caption = caption;
        this.likes = likes;
        this.postImageBitmap = bitmap;
    }
    public UsersPost(String username, String caption){
        this.username = username;
        this.caption = caption;

    }

    public String getUsername() {
        return username;
    }

    public Bitmap getPostImage() {
        return postImageBitmap;
    }

    public String getCaption() {
        return caption;
    }

    public String getLikes() {
        return likes;
    }

    public String getImageLabel() {
        return imageLabel;
    }

    public Date getDate() {
        return date;
    }

    public Bitmap getPostImageBitmap() {
        return postImageBitmap;
    }

    public String getUsersID() {
        return usersID;
    }

    public void setPostCaption(String postCaption) {
        this.caption = postCaption;
    }

//    //Add like and subtract like will be added to one method with a boolean for if a 'like button' is pressed or not.
//    public int addLike(int numberOfLikes) {
//        return likes + 1;
//    }
//
//    public int subtractLike(int numberOfLikes){
//        return  likes - 1;
//    }
//
//    public String editCaption(String newCaption){
//        return newCaption;
//    }
}
