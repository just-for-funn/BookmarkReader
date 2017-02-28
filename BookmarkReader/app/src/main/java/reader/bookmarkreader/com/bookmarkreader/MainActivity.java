package reader.bookmarkreader.com.bookmarkreader;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends FragmentActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Bookmark Reader");
        this.toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);


        WebSiteListFragment fragment = new WebSiteListFragment();
        FragmentManager manager =  getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.main_view_content , fragment)
                .add(R.id.settings_container , new SettingsFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        toggle.syncState();
        super.onPostCreate(savedInstanceState);
    }
}
