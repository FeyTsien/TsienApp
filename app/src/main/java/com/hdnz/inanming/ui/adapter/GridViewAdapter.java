package com.hdnz.inanming.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class GridViewAdapter<T> extends BaseAdapter {

    private int mResId;
    private List<T> mList;

    public GridViewAdapter(List<T> list, int resId) {
        this.mResId = resId;
        this.mList = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
        bindView(view, position);
        return view;
    }

    public abstract void bindView(View convertView, int position);

}