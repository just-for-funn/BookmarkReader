package com.codezilla.bookmarkreader.bindings.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.codezilla.bookmarkreader.BR;
import com.codezilla.bookmarkreader.databinding.EditListRowBinding;

/**
 * Created by davut on 17.02.2018.
 */
public class DataBindedViewHolder<T extends ViewDataBinding , RowModel> extends RecyclerView.ViewHolder {

    final T binding;

    public DataBindedViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(RowModel model) {
        binding.setVariable( BR.model , model);
        binding.executePendingBindings();
    }
}
