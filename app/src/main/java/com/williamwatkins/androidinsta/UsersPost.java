package com.williamwatkins.androidinsta;

import android.media.Image;

import java.util.Date;

public class UsersPost {

    private String username;
    private String caption;
    private Image postImage;
    private String likes;
    private Date date;

    public UsersPost(){

    }
    //    public UsersPost(String postCaption, Image postImage, int numberOfLikes){
//        this.username = user.getUsername();
//        this.postCaption = postCaption;
//        this.postImage = postImage;
//        this.numberOfLikes = numberOfLikes;
//    }

    public UsersPost(String username, String caption) {
        this.username = username;
        this.caption = caption;
        likes = "0";
    }

    public UsersPost(String username, String caption, String likes){
        this.username = username;
        this.caption = caption;
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public Image getPostImage() {
        return postImage;
    }

    public String getCaption() {
        return caption;
    }

    public String getLikes() {
        return likes;
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
