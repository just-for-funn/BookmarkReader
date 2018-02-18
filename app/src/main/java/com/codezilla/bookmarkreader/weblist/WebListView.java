package com.codezilla.bookmarkreader.weblist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.article.ArticleDetailView;
import com.codezilla.bookmarkreader.databinding.RowDataViewBinding;
import com.codezilla.bookmarkreader.databinding.WebsiteListFragmentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codezilla.bookmarkreader.R.id;
import static com.codezilla.bookmarkreader.R.layout;

/**
 * Created by davut on 15.02.2017.
 */

public class WebListView extends Fragment implements WebListViewModel.IWebListView
{
    RecyclerView recyclerView;
    WebsiteListFragmentBinding binding;
    private WebListViewModel model;
    private WebListViewFragmentAdapter adapter;


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
            this.adapter = new WebListViewFragmentAdapter( Collections.<WebListRowModel>emptyList());
            recyclerView.setAdapter(adapter);
            this.binding.setModel(model);
        }
        return binding.getRoot();
    }



    @Override
    public void onResume() {
        super.onResume();
        model.loadRows();
    }
    
    @Override
    public void onListChanged(List<WebSiteInfo> webSiteInfos) {
        Log.i(getClass().getSimpleName() , "List Changed");
        List<WebListRowModel> rowElements = new ArrayList<>();
        for (WebSiteInfo w: webSiteInfos)
        {
            WebListRowModel rm =  new WebListRowModel(w.getUrl() , w.getSummary() , w.getFaviconUrl() , this::onItemClicked);
            rm.setChangeDate(w.getChangeDate());
            rowElements.add(rm);
        }
        adapter.setItems(rowElements);
    }

    private void onItemClicked(WebListRowModel wlrm) {
        ArticleDetailView articleDetailView =  new ArticleDetailView();
        articleDetailView.load(wlrm.getTitle());
        getFragmentManager().beginTransaction().replace(R.id.main_view_content , articleDetailView  , "TAG_ARTICLE_DETAIL")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showError(String message)
    {
        Snackbar.make(this.getView() , message , Snackbar.LENGTH_SHORT ).show();
    }
}
