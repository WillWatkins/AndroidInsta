package com.williamwatkins.androidinsta.models;

import android.media.Image;

public class UserProfileDetails {

    private String username;
    private String bio;
    private String displayName;
    private String followerCount;
    private String followingCount;
    private String posts;
    //A string for now until I add firebase storage to allow for saving and retrieving images
    private String profilePhoto;
    private String website;
    private String pronouns;

    public UserProfileDetails(){

    }

    //Default details when a new user is registered. Used in the Register class
    public UserProfileDetails(String username){
        this.username = username;
        bio = "";
        displayName = username;
        followerCount = "0";
        followingCount = "0";
        posts = "0";
        profilePhoto = "";
        website = "";
        pronouns = "";
    }

    //Used in the ProfileActivity class to populate the users Profile info.
    public UserProfileDetails(String username, String bio, String displayName,
                              String followerCount, String followingCount, String posts,
                              String profilePhoto, String website) {
        this.username = username;
        this.bio = bio;
        this.displayName = displayName;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.posts = posts;
        this.profilePhoto = profilePhoto;
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFollowerCount() {
        return followerCount + " Followers";
    }

    public String getFollowingCount() {
        return followingCount + " Following";
    }

    public String getPosts() {
        return posts + " Posts";
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getWebsite() {
        return website;
    }

    public String getPronouns() {
        return pronouns;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public void addFollowerCount(){
        followerCount = String.valueOf(Integer.parseInt(followerCount) + 1);
    }

    public void subtractFollowerCount(){
        followerCount = String.valueOf(Integer.parseInt(followerCount) - 1);
    }

    public void addFollowingCount(){
        followingCount = String.valueOf(Integer.parseInt(followingCount) + 1);
    }

    public void subtractFollowingCount(){
        followingCount = String.valueOf(Integer.parseInt(followingCount) - 1);
    }

    public void addPostCount(){
        posts = String.valueOf(Integer.parseInt(posts) + 1);
    }

    public void subtractPostCount(){
        posts = String.valueOf(Integer.parseInt(posts) - 1);
    }


    public void changeBio(String newBio){
        bio = newBio;
    }

    public void changeDisplayName(String newDisplayName){
        displayName = newDisplayName;
    }

    //Will be completed once I add photo element
    public void changeProfilePhoto(){
    }

    public void changeWebsite(String websiteURL){
        website = websiteURL;
    }

    public void changePronouns(String newPronouns){
        pronouns = newPronouns;
    }
}
