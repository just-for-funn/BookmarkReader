package com.davutozcan.bookmarkreader.weblist;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.article.ArticleDetailView;
import com.davutozcan.bookmarkreader.databinding.WebsiteListFragmentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.davutozcan.bookmarkreader.R.id;
import static com.davutozcan.bookmarkreader.R.layout;

/**
 * Created by davut on 15.02.2017.
 */

public class WebListView extends Fragment implements WebListViewModel.IWebListView
{
    private  Animation fapOpenAnimation;
    private  Animation fapCloseAnimation;
    RecyclerView recyclerView;
    WebsiteListFragmentBinding binding;
    private WebListViewModel model;
    private WebListViewFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        fapOpenAnimation = AnimationUtils.loadAnimation(getContext() , R.anim.fab_open );
        fapCloseAnimation = AnimationUtils.loadAnimation(getContext() , R.anim.fab_close );
        if(binding == null)
        {
            this.binding = DataBindingUtil.inflate(inflater , layout.website_list_fragment , container , false );
            this.recyclerView = (RecyclerView) binding.getRoot().findViewById(id.web_site_list_fragment);
            recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
            this.model = new WebListViewModel(this);
            this.model.getIsFabOpened().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    fabStateChanged();
                }
            });
            this.adapter = new WebListViewFragmentAdapter( Collections.<WebListRowModel>emptyList());
            recyclerView.setAdapter(adapter);
            this.binding.setModel(model);
        }
        return binding.getRoot();
    }

    private void fabStateChanged() {
        if(model.getIsFabOpened().get())
        {
            showFaps();
        }else {
            hideFaps();
        }
    }

    private void hideFaps() {
        binding.fabAllContainer.startAnimation(fapCloseAnimation);
        binding.fabReadContainer.startAnimation(fapCloseAnimation);
        binding.fabUnreadContainer.startAnimation(fapCloseAnimation);
        binding.fabUnreadContainer.setClickable(false);
        binding.fabReadContainer.setClickable(false);
        binding.fabAllContainer.setClickable(false);
    }

    private void showFaps() {
        binding.fabAllContainer.startAnimation(fapOpenAnimation);
        binding.fabReadContainer.startAnimation(fapOpenAnimation);
        binding.fabUnreadContainer.startAnimation(fapOpenAnimation);
        binding.fabUnreadContainer.setClickable(true);
        binding.fabReadContainer.setClickable(true);
        binding.fabAllContainer.setClickable(true);
    }


    @Override
    public void onResume() {
        model.reload();
        super.onResume();
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

    @Override
    public void onPause() {
        super.onPause();
        model.getIsFabOpened().set(false);
    }

}
