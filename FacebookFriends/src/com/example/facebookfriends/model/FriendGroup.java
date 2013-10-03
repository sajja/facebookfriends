package com.example.facebookfriends.model;


public class FriendGroup {
    private String name;
    private String id;


    public FriendGroup(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
