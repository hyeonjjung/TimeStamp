package com.example.userp.timestamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by userp on 2018-07-12.
 */

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ListviewItem> data;
    private int layout;

    public ListViewAdapter(Context context, int layout, ArrayList<ListviewItem> data) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }
        ListviewItem listviewItem = data.get(i);

        TextView time = (TextView) view.findViewById(R.id.textView);
        TextView situation = (TextView) view.findViewById(R.id.textView2);
        TextView speed  = (TextView) view.findViewById(R.id.speedTextView);

        time.setText(data.get(i).getTime());
        situation.setText(data.get(i).getSituation());
        speed.setText(data.get(i).getSpeed());

        return view;
    }
}
