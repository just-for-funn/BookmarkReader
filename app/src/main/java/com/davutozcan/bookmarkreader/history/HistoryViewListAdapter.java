package com.davutozcan.bookmarkreader.history;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.databinding.HistoryRowViewBinding;

import java.util.List;

/**
 * Created by davut on 9/19/2017.
 */

class HistoryViewListAdapter extends BaseAdapter
{
    private final List<HistoryViewRowModel> elements;

    public HistoryViewListAdapter(List<HistoryViewRowModel> elements) {
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public HistoryViewRowModel getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryRowViewBinding binding = null;
        if(convertView == null )
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binding = HistoryRowViewBinding.inflate(inflater , parent , false );
        }else
        {
            binding = DataBindingUtil.findBinding(convertView);
        }
        binding.setModel(elements.get(position) );
        return binding.getRoot();
    }
}
