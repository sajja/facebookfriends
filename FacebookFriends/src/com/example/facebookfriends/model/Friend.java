package com.example.facebookfriends.model;


import java.util.ArrayList;
import java.util.List;

public class Friend {
    private String name;
    private String id;
    private List<FriendGroup> friendGroups = new ArrayList<FriendGroup>();

    public Friend(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void addFriendGroup(FriendGroup group) {
        friendGroups.add(group);
    }
}

