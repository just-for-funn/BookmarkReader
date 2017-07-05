package reader.bookmarkreader.com.bookmarkreader.weblist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import reader.bookmarkreader.com.bookmarkreader.R;
import reader.bookmarkreader.com.bookmarkreader.databinding.RowDataViewBinding;

/**
 * Created by davut on 15.02.2017.
 */

public class WebListView extends Fragment
{
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View w =  inflater.inflate(R.layout.website_list_fragment , container , false);
        this.recyclerView = (RecyclerView) w.findViewById(R.id.web_site_list_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter( new WebListViewFragmentAdapter( getRows()));
        return w;
    }

    List<WebListRowModel> getRows()
    {
        return Arrays.asList(WebListRowModel.of("www.facebook.com","some text") , WebListRowModel.of("www.cnet.com","New phone"));
    }


    static class WebListViewFragmentAdapter extends RecyclerView.Adapter<WebListViewRowDataHolder>
    {

        private final List<WebListRowModel> rows;

        public WebListViewFragmentAdapter(List<WebListRowModel> rows) {
            this.rows = rows;
        }

        @Override
        public WebListViewRowDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            RowDataViewBinding rdwb= DataBindingUtil.inflate(li , R.layout.row_data_view , parent , false);
            return new WebListViewRowDataHolder(rdwb);
        }

        @Override
        public void onBindViewHolder(WebListViewRowDataHolder holder, int position) {
            holder.setWebListRowModel(rows.get(position));
        }

        @Override
        public int getItemCount() {
            return rows.size();
        }
    }

    static class WebListViewRowDataHolder extends RecyclerView.ViewHolder
    {
        RowDataViewBinding binding;
        public WebListViewRowDataHolder(RowDataViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void setWebListRowModel(WebListRowModel wlrm)
        {
            binding.setRowData(wlrm);
            binding.executePendingBindings();
        }
    }
}
