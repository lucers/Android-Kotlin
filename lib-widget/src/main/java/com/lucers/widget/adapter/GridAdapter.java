package com.lucers.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.lucers.widget.R;

import java.util.List;

/**
 * Created by Lucers on 2020/5/9.
 */
public class GridAdapter extends BaseAdapter {

    private List<String> data;

    public GridAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_number, parent, false);
        } else {
            view = convertView;
        }

        bindView(position, view);
        return view;
    }

    private void bindView(int position, View view) {
        Button btnNumber = view.findViewById(R.id.btn_number);
        btnNumber.setText(data.get(position));
    }
}
