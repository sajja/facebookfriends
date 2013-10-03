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
import com.example.facebookfriends.util.FacebookConnector;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        FacebookConnector connector = new FacebookConnector("549162681809020", this, getApplicationContext(),
                new String[] {"read_friendlists", "manage_friendlists", "friends_groups"});
        connector.login();

        try {

            List<FriendGroup> friendLists = connector.getAllFriendLists();

            Map<String,Friend> friendMap = connector.getMyFriends(friendLists);

            app.setFriends(new ArrayList<Friend>(friendMap.size()));
            while (friendMap.keySet().iterator().hasNext()) {
                String key =  friendMap.keySet().iterator().next();
                app.getFriends().add(friendMap.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return true;
    }

    public void loadFriendList(View view) {
        Intent intent = new Intent(this, FriendListActivity.class);
        startActivity(intent);
    }
}
