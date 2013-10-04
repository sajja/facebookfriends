package com.example.facebookfriends;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> groups;
    private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData, List<String> groups) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.groups = groups;
    }



    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

    	LayoutInflater layoutInflater =(LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.list_item, null);

        LinearLayout parentLayout = (LinearLayout) convertView.findViewById(R.id.layout);
        View view;

        for (String group : groups) {
            view = layoutInflater.inflate(R.layout.list_item_layout, null);
            // In order to get the view we have to use the new view with Toggle Button in it
            ToggleButton toggle = (ToggleButton)view.findViewById(R.id.toggle);
            toggle.setText(group);
            toggle.setTextOff(group);
            toggle.setTextOn(group);
            toggle.setHeight(50);
            toggle.setChecked(true);
            toggle.setBackgroundColor(Color.YELLOW);
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    		        if (isChecked) {
    		        	buttonView.setBackgroundColor(Color.YELLOW);
    		        } else {
    		        	buttonView.setBackgroundColor(Color.CYAN);
    		        }
    		    }
            });
            parentLayout.addView(toggle);
        }
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}