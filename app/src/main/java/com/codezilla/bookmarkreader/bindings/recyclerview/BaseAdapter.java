package com.codezilla.bookmarkreader.bindings.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by davut on 17.02.2018.
 */

public abstract class BaseAdapter<RowBinding extends ViewDataBinding, RowModel> extends RecyclerView.Adapter<DataBindedViewHolder<RowBinding , RowModel>> {
    @Override
    public DataBindedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowBinding binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), parent, false);
        return new DataBindedViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(DataBindedViewHolder holder, int position)
    {
        RowModel model = getItemForPosition(position);
        holder.bind(model);
    }

    protected abstract int getLayoutId();

    protected abstract RowModel getItemForPosition(int position);

}
