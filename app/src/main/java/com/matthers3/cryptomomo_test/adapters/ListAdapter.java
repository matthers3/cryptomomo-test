package com.matthers3.cryptomomo_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListAdapter<T> extends BaseAdapter {

    T[] Items;
    LayoutInflater inflater;

    public ListAdapter(T[] items, Context ctx) {
        Items = items;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return Items.length;
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
        return null;
    }
}
