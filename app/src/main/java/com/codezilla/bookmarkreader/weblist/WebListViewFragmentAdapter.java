package com.codezilla.bookmarkreader.weblist;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.bindings.recyclerview.BaseArrayListAdapter;
import com.codezilla.bookmarkreader.databinding.RowDataViewBinding;

import java.util.List;

/**
 * Created by davut on 18.02.2018.
 */
class WebListViewFragmentAdapter extends BaseArrayListAdapter<RowDataViewBinding , WebListRowModel>
{
    public WebListViewFragmentAdapter(List<WebListRowModel> rowElements) {
        super(rowElements);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.row_data_view;
    }
}
