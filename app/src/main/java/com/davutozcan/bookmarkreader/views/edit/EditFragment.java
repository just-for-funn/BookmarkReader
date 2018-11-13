package com.davutozcan.bookmarkreader.views.edit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.davutozcan.bookmarkreader.R;
import com.davutozcan.bookmarkreader.backend.IBookmarkReaderService;
import com.davutozcan.bookmarkreader.databinding.FragmentEditBinding;
import com.davutozcan.bookmarkreader.exception.DomainException;
import com.davutozcan.bookmarkreader.util.GmailUser;
import com.davutozcan.bookmarkreader.util.Logger;
import com.davutozcan.bookmarkreader.util.SessionManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.davutozcan.bookmarkreader.backend.DeleteBookmarkTask.deleteBookmarkTask;


public class EditFragment extends Fragment {
    private static final String MTAG = EditFragment.class.getSimpleName();
    EditFragmentViewModel model = new EditFragmentViewModel();
    FragmentEditBinding binding = null;
    private EditListAdapter listAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(MTAG,"on Create View");
        setHasOptionsMenu(true);
        this.binding =  DataBindingUtil.inflate(inflater , R.layout.fragment_edit , container , false);
        this.binding.setModel(model);
        this.binding.setHandler(new EditViewHandler(this));
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.edit_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        this.listAdapter = new EditListAdapter();
        recyclerView.setAdapter(listAdapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    void reload() {
        model.load();
        listAdapter.setItems(model.urls);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete)
        {
            delete();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    private void delete() {
        List<String> items =  this.listAdapter.getSelected();
        this.model.isBusy.set(true);
        Observable.concat(this.deleteFromBackend( items) , this.deleteLocal( items))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()->{
                    model.isBusy.set(false);
                    listAdapter.setItems(model.urls);
                    listAdapter.notifyDataSetChanged();
                })
                .subscribe(result-> {} , this::deleteFailed);
    }

    private void deleteFailed(Throwable throwable) {
        Logger.e(throwable);
        String message  = throwable instanceof DomainException ? ((DomainException) throwable).getMsg() : "Delete failed";
        Snackbar.make(this.getView() , message , Snackbar.LENGTH_SHORT ).show();
    }

    Observable<?> deleteLocal(List<String> items){
        return Observable.fromCallable(()->{
            model.remove(items);
            return true;
        });
    }

    Observable<?> deleteFromBackend(List<String > bookmarks){
        SessionManager sessionManager = new SessionManager(this.getActivity());
        Optional<GmailUser> gUser = sessionManager.getUser();
        if(gUser.isPresent())
        {
            return deleteBookmarkTask(IBookmarkReaderService.newInstance() , this.getActivity())
                    .doWork(gUser.get().getGoogleId() , bookmarks );
        }else
        {
            return Observable.empty();
        }
    }


    public EditFragmentViewModel getModel() {
        return model;
    }
}
