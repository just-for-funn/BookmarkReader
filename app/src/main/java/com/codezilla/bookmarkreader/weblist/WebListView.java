package com.codezilla.bookmarkreader.weblist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.RowDataViewBinding;
import com.codezilla.bookmarkreader.databinding.WebsiteListFragmentBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.codezilla.bookmarkreader.R.*;

/**
 * Created by davut on 15.02.2017.
 */

public class WebListView extends Fragment implements WebListViewModel.IWebListView
{
    RecyclerView recyclerView;
    WebsiteListFragmentBinding binding;
    private WebListViewModel model;

    private RowItemSelectedCallBack callBack;

    public void setCallBack(RowItemSelectedCallBack callBack) {
        this.callBack = callBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(binding == null)
        {
            this.binding = DataBindingUtil.inflate(inflater , layout.website_list_fragment , container , false );
            this.recyclerView = (RecyclerView) binding.getRoot().findViewById(id.web_site_list_fragment);
            recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
            this.model = new WebListViewModel(this);
            recyclerView.setAdapter( new WebListViewFragmentAdapter( Collections.<WebListRowModel>emptyList() , model));
            this.binding.setModel(model);
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!model.isLoaded())
            model.loadRows();
    }
    
    @Override
    public void onListChanged(List<WebSiteInfo> webSiteInfos) {
        List<WebListRowModel> rowElements = new ArrayList<>();
        for (WebSiteInfo w: webSiteInfos)
        {
            rowElements.add(new WebListRowModel(w.getUrl() , w.getSummary()));
        }
        recyclerView.setAdapter( new WebListViewFragmentAdapter( rowElements , this.model));
    }

    @Override
    public void notifyItemClick(WebListRowModel webListRowModel) {
        this.callBack.onRowItemSelected(webListRowModel);
    }


    static class WebListViewFragmentAdapter extends RecyclerView.Adapter<WebListViewRowDataHolder>
    {

        private final List<WebListRowModel> rows;
        private final WebListRowItemEventHandler handler;

        public WebListViewFragmentAdapter(List<WebListRowModel> rows , WebListRowItemEventHandler handler)
        {
            this.rows = rows;
            this.handler = handler;
        }

        @Override
        public WebListViewRowDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            RowDataViewBinding rdwb= DataBindingUtil.inflate(li , layout.row_data_view , parent , false);
            return new WebListViewRowDataHolder(rdwb);
        }

        @Override
        public void onBindViewHolder(WebListViewRowDataHolder holder, int position) {
            holder.setWebListRowModel(rows.get(position) , handler);
        }

        @Override
        public int getItemCount() {
            return rows.size();
        }
    }

    static class WebListViewRowDataHolder extends RecyclerView.ViewHolder
    {
        RowDataViewBinding binding;
        public WebListViewRowDataHolder(RowDataViewBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setWebListRowModel(WebListRowModel wlrm , WebListRowItemEventHandler handler)
        {
            Log.i("Bookmark","creating view");
            binding.setRowData(wlrm);
            binding.setHandler(handler);
            binding.executePendingBindings();
        }
    }
}
