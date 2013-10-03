package com.example.facebookfriends.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.facebookfriends.model.Friend;
import com.example.facebookfriends.model.FriendGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.example.facebookfriends.util.SessionEvents.AuthListener;
import com.example.facebookfriends.util.SessionEvents.LogoutListener;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FacebookConnector {

    private Facebook facebook = null;
    private Context context;
    private String[] permissions;
    private Handler mHandler;
    private Activity activity;
    private SessionListener mSessionListener = new SessionListener();
    ;

    public FacebookConnector(String appId, Activity activity, Context context, String[] permissions) {
        this.facebook = new Facebook(appId);

        SessionStore.restore(facebook, context);
        SessionEvents.addAuthListener(mSessionListener);
        SessionEvents.addLogoutListener(mSessionListener);

        this.context = context;
        this.permissions = permissions;
        this.mHandler = new Handler();
        this.activity = activity;
    }

    public void login() {
        if (!facebook.isSessionValid()) {
            facebook.authorize(this.activity, this.permissions, new LoginDialogListener());
        }
    }

    public void logout() {
        SessionEvents.onLogoutBegin();
        AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(this.facebook);
        asyncRunner.logout(this.context, new LogoutRequestListener());
    }

    public void postMessageOnWall(String msg) {
        if (facebook.isSessionValid()) {
            Bundle parameters = new Bundle();
            parameters.putString("message", msg);
            try {
                String response = facebook.request("me/feed", parameters, "POST");
                System.out.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            login();
        }
    }

    public Map<String, Friend> getMyFriends(List<FriendGroup> friendGroups) throws JSONException, IOException {
        HashMap<String, Friend> friendMap = new HashMap<String, Friend>();
        Bundle parameters = new Bundle();


        if (facebook.isSessionValid()) {
            for (FriendGroup friendGroup : friendGroups) {
                String response = facebook.request(friendGroup.getId()+"/members", parameters, "GET");
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonObjectArray = jsonResponse.getJSONArray("data");

                for (int i = 0; i < jsonObjectArray.length(); i++) {
                    JSONObject object = jsonObjectArray.getJSONObject(i);
                    Friend friend = new Friend(object.getString("name"), object.getString("id"));
                    if (friendMap.containsKey(friend.getId())) {
                        friend = friendMap.get(friend.getId());
                        friend.addFriendGroup(friendGroup);
                    } else {
                        friend.addFriendGroup(friendGroup);
                        friendMap.put(friend.getId(),friend);
                    }
                }
            }
        }
        return friendMap;
    }

    public List<FriendGroup> getAllFriendLists() throws JSONException {
        ArrayList<FriendGroup> friendList = new ArrayList<FriendGroup>();
        if (facebook.isSessionValid()) {
            Bundle parameters = new Bundle();
            try {
                String response = facebook.request("me/friendlists", parameters, "GET");
                JSONObject jsonResponse = new JSONObject(response);
                System.out.println(response);
                JSONArray jsonObjectArray = jsonResponse.getJSONArray("data");

                for (int i = 0; i < jsonObjectArray.length(); i++) {
                    JSONObject object = jsonObjectArray.getJSONObject(i);
                    friendList.add(new FriendGroup(object.getString("name"), object.getString("id")));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            login();
        }
        return friendList;
    }

    private final class LoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
            SessionEvents.onLoginSuccess();
        }

        public void onFacebookError(FacebookError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        public void onError(DialogError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        public void onCancel() {
            SessionEvents.onLoginError("Action Canceled");
        }
    }

    public class LogoutRequestListener extends BaseRequestListener {
        public void onComplete(String response, final Object state) {
            // callback should be run in the original thread, 
            // not the background thread
            mHandler.post(new Runnable() {
                public void run() {
                    SessionEvents.onLogoutFinish();
                }
            });
        }
    }

    private class SessionListener implements AuthListener, LogoutListener {

        public void onAuthSucceed() {
            SessionStore.save(facebook, context);
        }

        public void onAuthFail(String error) {
        }

        public void onLogoutBegin() {
        }

        public void onLogoutFinish() {
            SessionStore.clear(context);
        }
    }

    public Facebook getFacebook() {
        return this.facebook;
    }
}
