package reader.bookmarkreader.com.bookmarkreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by davut on 15.02.2017.
 */

public class WebSiteListFragment extends Fragment
{

    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View w =  inflater.inflate(R.layout.website_list_fragment , container , false);
        this.textView = (TextView) w.findViewById(R.id.txt1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(R.id.main_view_content , new DetailFragment()).commit();
            }
        });
        return w;

    }
}
