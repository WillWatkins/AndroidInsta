package com.williamwatkins.androidinsta;

public class UserProfileDetails {

    private String username;
    private String profileDescription;
    private String displayName;
    private String followerCount;
    private String followingCount;
    private String posts;
    //A string for now until I add firebase storage
    private String profilePhoto;
    private String website;

    public UserProfileDetails(){

    }
    public UserProfileDetails(String username){
        this.username = username;
        profileDescription = "Bio...";
        displayName = username;
        followerCount = "0";
        followingCount = "0";
        posts = "0";
        profilePhoto = "none";
        website = "Website...";

    }

    public UserProfileDetails(String username, String profileDescription, String displayName,
                              String followerCount, String followingCount, String posts,
                              String profilePhoto, String website) {
        this.username = username;
        this.profileDescription = profileDescription;
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

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getWebsite() {
        return website;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
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

    public void changeDescription(String newDescription){
        profileDescription = newDescription;
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
}
