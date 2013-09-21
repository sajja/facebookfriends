package com.example.facebookfriends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.TextView;
import com.example.facebookfriends.model.Friend;
import com.example.facebookfriends.model.FriendGroup;

public class FriendListActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, groups);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        List<Friend> friends = ((FacebookFriendsApplication)getApplication()).getFriends();
        List<FriendGroup> groups = ((FacebookFriendsApplication)getApplication()).getGroups();
        for (Friend friend : friends) {
            listDataHeader.add(friend.getName());
        }

        List<String> friendGroupNames = new ArrayList<String>();
        for (FriendGroup group : groups) {
            friendGroupNames.add(group.getName());
        }

        for (String header : listDataHeader) {
            listDataChild.put(header, friendGroupNames);
        }

        this.groups = friendGroupNames;
    }
}