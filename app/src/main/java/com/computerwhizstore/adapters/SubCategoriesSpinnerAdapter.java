package com.computerwhizstore.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.computerwhizstore.models.SubCategoryModel;

import org.jetbrains.annotations.NotNull;

public class SubCategoriesSpinnerAdapter extends ArrayAdapter<SubCategoryModel> {

    private SubCategoryModel[] values;

    public SubCategoriesSpinnerAdapter(Context context, int textViewResourceId, SubCategoryModel[] values) {
        super(context, textViewResourceId, values);
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public SubCategoryModel getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getSubCategoryName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getSubCategoryName());
        return label;
    }
}