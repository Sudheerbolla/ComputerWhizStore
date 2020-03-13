package com.computerwhizstore.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.computerwhizstore.models.CategoryModel;

import org.jetbrains.annotations.NotNull;

public class CategoriesSpinnerAdapter extends ArrayAdapter<CategoryModel> {

    // Your custom values for the spinner (User)
    private CategoryModel[] values;

    public CategoriesSpinnerAdapter(Context context, int textViewResourceId, CategoryModel[] values) {
        super(context, textViewResourceId, values);
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public CategoryModel getItem(int position) {
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
        label.setText(values[position].getCategoryName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getCategoryName());
        return label;
    }
}