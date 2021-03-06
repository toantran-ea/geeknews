package mobi.toan.geeknews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.R;
import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.fragments.NewsListFragment;
import mobi.toan.geeknews.models.bus.NewsSelectedMessage;
import mobi.toan.geeknews.models.bus.SourceSelectedMessage;
import mobi.toan.geeknews.utils.SourcesResolver;

public class MainActivity extends BaseApplicationActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String NEWS_LIST_TAG = "NEWS_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        NewsListFragment fragment = NewsListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_holder, fragment, NEWS_LIST_TAG).commit();

        EventBus.getDefault().register(this);

        displayCorrectActionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(NewsSelectedMessage event) {
        Intent intent = new Intent(this, NewsReaderActivity.class);
        intent.putExtra(Constants.TARGET_URL, event.getTargetUrl());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        displayCorrectActionBar();
        super.onBackPressed();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String source = SourcesResolver.resolve(item.getItemId());
        SourceSelectedMessage message = new SourceSelectedMessage(source);
        saveSource(source);
        EventBus.getDefault().post(message);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayCorrectActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(SourcesResolver.getBeautifulName(this, getSource()));
        }
    }

}

