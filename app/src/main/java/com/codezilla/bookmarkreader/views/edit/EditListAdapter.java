package com.codezilla.bookmarkreader.views.edit;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.EditListRowBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davut on 10.02.2018.
 */

class EditListAdapter extends RecyclerView.Adapter<EditListAdapter.EditListRowView>
{
    List<EditRowModel> items = new ArrayList();

    public EditListAdapter()
    {

    }
    @Override
    public EditListRowView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        EditListRowBinding binding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()) , R.layout.edit_list_row , parent , false );
        return new EditListRowView(binding);
    }

    @Override
    public void onBindViewHolder(EditListRowView holder, int position)
    {
        holder.binding.setModel(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ObservableList<String> urls) {
        items = Stream.of(urls)
                .map(o->create(o))
                .collect(Collectors.toList());
    }

    private EditRowModel create(String o)
    {
        EditRowModel model = new EditRowModel( o , false);
        return model;
    }

    public List<String> getSelected() {
        return Stream.of(this.items).filter(o->o.checked.get())
                .map(o->o.url.get())
                .collect(Collectors.toList());
    }


    static class EditListRowView extends RecyclerView.ViewHolder {

        private final EditListRowBinding binding;

        public EditListRowView(EditListRowBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
