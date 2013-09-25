package com.example.facebookfriends;

import android.app.Application;
import com.example.facebookfriends.model.Friend;
import com.example.facebookfriends.model.FriendGroup;

import java.util.List;


public class FacebookFriendsApplication extends Application {
    private List<Friend> friends;
    private List<FriendGroup> groups;

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<FriendGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FriendGroup> groups) {
        this.groups = groups;
    }
}
