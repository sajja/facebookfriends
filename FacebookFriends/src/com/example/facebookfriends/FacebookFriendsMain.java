package com.example.facebookfriends;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FacebookFriendsMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_friends_main);


        LinearLayout parent = (LinearLayout) findViewById(R.id.layout);
        LayoutInflater li = getLayoutInflater();
        View view;
        for (int i = 0; i<10; i++) {
            view = li.inflate(R.layout.text_layout, parent, false);
            TextView tw = (TextView) view.findViewById(R.id.text);
            tw.setText("Hello " + i);
            parent.addView(tw);
        }
        System.out.println("xx");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.facebook_friends_main, menu);
		return true;
	}

	public void loadFriends(View view) {
		Intent intent = new Intent(this, FriendListActivity.class);
	    startActivity(intent);
	}
}
