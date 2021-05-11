package com.williamwatkins.androidinsta;

import android.media.Image;

public class UsersPost {

    User user = new User();

    private String username;
    private String postCaption;
    private Image postImage;

    public UsersPost(){

    }

    public UsersPost(String postCaption, Image postImage){
        this.username = user.getUsername();
        this.postCaption = postCaption;
        this.postImage = postImage;
    }

    public String getUsername() {
        return username;
    }

    public Image getPostImage() {
        return postImage;
    }

    public String getPostCaption() {
        return postCaption;
    }

    public void setPostCaption(String postCaption) {
        this.postCaption = postCaption;
    }
}
