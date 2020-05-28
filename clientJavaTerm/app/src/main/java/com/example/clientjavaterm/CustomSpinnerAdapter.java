package com.example.clientjavaterm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomSpinnerAdapter<T> extends ArrayAdapter<T> {

    private final Context mContext;
    private List<T> list;
    private boolean flag;

    public CustomSpinnerAdapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
        this.mContext = context;
        this.list = new ArrayList<>();
        flag = false;
    }

    @Override
    public void addAll(@NonNull Collection<? extends T> collection) {
        //super.addAll(collection);
        list = (List<T>) collection;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int index) {
        return list.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view =  View.inflate(mContext, R.layout.department_row, null);
        //LayoutInflater inflater = LayoutInflater.from(getContext());
        //@SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.department_row, parent, false);
        TextView name = view.findViewById(R.id.TVDepartmentSpinnerRowName);

        //v.setTypeface(myTypeFace);
        if (flag) {
            name.setText(list.get(position).toString());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        @SuppressLint("ViewHolder") View view =  View.inflate(mContext, R.layout.department_row, null);
        //LayoutInflater inflater = LayoutInflater.from(getContext());
        //@SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.department_row, parent, false);
        TextView name = view.findViewById(R.id.TVDepartmentSpinnerRowName);

        //v.setTypeface(myTypeFace);
        name.setText(list.get(position).toString());
        return view;
    }

    public List<T> getList() {
        return list;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}