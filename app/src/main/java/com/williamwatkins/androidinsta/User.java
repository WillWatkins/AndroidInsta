package com.williamwatkins.androidinsta;

public class User {

    //User variables
    private String name;
    private String surname;
    private String username;
    private int age;
    private String email;
    private String password;
    String user_id;

    //Empty constructor
    public User(){

    }

    //Constructor
    public User(String name, String surname, String username, int age, String email, String user_id){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.age = age;
        this.email = email;
        this.user_id = user_id;
    }

    public User (String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getUser_id(){
        return  user_id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
}
