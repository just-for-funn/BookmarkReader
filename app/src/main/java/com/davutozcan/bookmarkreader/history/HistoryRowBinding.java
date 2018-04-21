package com.davutozcan.bookmarkreader.history;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

/**
 * Created by davut on 9/19/2017.
 */

public class HistoryRowBinding {
    @BindingAdapter(value="historyRows")
    public static void bindRows(ListView listView , ObservableArrayList<HistoryViewRowModel> elements)
    {
        ((ListView)listView).setAdapter(new HistoryViewListAdapter(elements));
    }

    @BindingAdapter("background_color")
    public static void bindColor(View w , int colorResourceID)
    {
        w.setBackgroundColor(w.getContext().getResources().getColor(colorResourceID ));
    }
}
