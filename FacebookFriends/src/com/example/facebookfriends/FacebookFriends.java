package com.example.facebookfriends;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.facebookfriends.model.Friend;
import com.example.facebookfriends.model.FriendGroup;

import java.util.ArrayList;

public class FacebookFriends extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_friends_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.facebook_friends_main, menu);
        //TODO: Load friends groups.
        FacebookFriendsApplication app = (FacebookFriendsApplication) getApplication();

        app.setFriends(new ArrayList<Friend>());
        app.getFriends().add(new Friend("Sajith"));
        app.getFriends().add(new Friend("Rushantha"));
        app.getFriends().add(new Friend("Silva"));

        app.setGroups(new ArrayList<FriendGroup>());
        app.getGroups().add(new FriendGroup("Office"));
        app.getGroups().add(new FriendGroup("School"));
        app.getGroups().add(new FriendGroup("gym"));

        return true;
    }

    public void loadFriendList(View view) {
        Intent intent = new Intent(this, FriendListActivity.class);
        startActivity(intent);
    }
}
