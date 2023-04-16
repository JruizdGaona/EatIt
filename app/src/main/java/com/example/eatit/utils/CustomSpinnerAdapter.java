package com.example.eatit.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eatit.R;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    String[] tipos;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, String[] tipos) {
        this.context = applicationContext;
        this.tipos = tipos;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return tipos.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);
        TextView names = (TextView) view.findViewById(R.id.text);
        names.setText(tipos[i]);
        return view;
    }
}
