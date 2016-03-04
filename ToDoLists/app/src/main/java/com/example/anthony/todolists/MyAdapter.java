package com.example.anthony.todolists;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by anthony on 3/4/16.
 */
public class MyAdapter extends ArrayAdapter {
    public MyAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        return createViewFromResource(mInflater, position, convertView, parent, mResource);
//    }
}
