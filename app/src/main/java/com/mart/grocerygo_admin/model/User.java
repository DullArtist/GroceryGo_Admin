package com.mart.grocerygo_admin.model;

public class User {

    private String Username;
    private String Email;
    private String Id;

    public User(String username, String email,String id) {
        Username = username;
        Email = email;
        this.Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
