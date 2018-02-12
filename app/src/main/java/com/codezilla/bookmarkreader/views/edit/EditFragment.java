package com.codezilla.bookmarkreader.views.edit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.codezilla.bookmarkreader.R;
import com.codezilla.bookmarkreader.databinding.FragmentEditBinding;

import java.util.List;


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

        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.edit_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        this.listAdapter = new EditListAdapter();
        recyclerView.setAdapter(listAdapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        model.load();
        listAdapter.setItems(model.urls);
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
        model.remove(items);
        listAdapter.setItems(model.urls);
        listAdapter.notifyDataSetChanged();
    }
}
