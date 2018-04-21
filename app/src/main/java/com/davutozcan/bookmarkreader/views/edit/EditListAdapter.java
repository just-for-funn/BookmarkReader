package com.davutozcan.bookmarkreader.views.edit;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.bindings.recyclerview.BaseAdapter;
import com.davutozcan.bookmarkreader.bindings.recyclerview.BaseArrayListAdapter;
import com.davutozcan.bookmarkreader.bindings.recyclerview.DataBindedViewHolder;
import com.davutozcan.bookmarkreader.databinding.EditListRowBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davut on 10.02.2018.
 */

class EditListAdapter extends BaseArrayListAdapter<EditListRowBinding,EditRowModel>
{

    public void setItems(ObservableList<String> urls) {
        List<EditRowModel> items = Stream.of(urls)
                .map(o->create(o))
                .collect(Collectors.toList());
        setItems(items);
    }

    private EditRowModel create(String o)
    {
        EditRowModel model = new EditRowModel( o , false);
        return model;
    }

    public List<String> getSelected() {
        return Stream.of(getItems()).filter(o->o.checked.get())
                .map(o->o.url.get())
                .collect(Collectors.toList());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.edit_list_row;
    }
}
