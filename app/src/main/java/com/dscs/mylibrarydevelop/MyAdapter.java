package com.dscs.mylibrarydevelop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 *
 */

public class MyAdapter extends BaseAdapter {
    List<KeyValue<String, Class<? extends Activity>>> data;
    private Context context;

    public MyAdapter(Context context, List<KeyValue<String, Class<? extends Activity>>> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        } else {
            tv = (TextView) convertView;
        }
        tv.setText(data.get(position).key);
        return convertView;
    }
}
