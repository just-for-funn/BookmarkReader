package com.davutozcan.bookmarkreader.bindings.recyclerview;

import android.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davut on 18.02.2018.
 */

public abstract class BaseArrayListAdapter<RowBinding extends ViewDataBinding, RowModel>
        extends BaseAdapter<RowBinding , RowModel> {

    List<RowModel> items = new ArrayList();

    public BaseArrayListAdapter(List<RowModel> items) {
        this.items = items;
    }

    public BaseArrayListAdapter() {
        this(new ArrayList<>());
    }

    @Override
    protected RowModel getItemForPosition(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<RowModel> getItems() {
        return items;
    }

    public void setItems(List<RowModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
