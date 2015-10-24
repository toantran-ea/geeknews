package mobi.toan.geeknews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.models.bus.NewsSelectedMessage;
import mobi.toan.geeknews.models.bus.SourceSelectedMessage;
import mobi.toan.geeknews.utils.SourcesResolver;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String NEWS_LIST_TAG = "NEWS_LIST";
    private static final String NEWS_DETAIL_TAG = "NEWS_DETAIL";

    private NewsReaderFragment mDetailFragment;

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

        getSupportActionBar().setTitle(SourcesResolver.getBeautifulName(this, getSource()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Change the source list", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(NewsSelectedMessage event) {
        mDetailFragment = NewsReaderFragment.newInstance(event.getTargetUrl());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_holder, mDetailFragment, NEWS_DETAIL_TAG).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        // Back on the webview scope
        if (mDetailFragment != null && mDetailFragment.getWebView().canGoBack()) {
            mDetailFragment.getWebView().goBack();
            return;
        } else {
            if( mDetailFragment.getWebView() != null && !mDetailFragment.getWebView().canGoBack()) {
                getSupportActionBar().setTitle(SourcesResolver.getBeautifulName(this, getSource()));
            }
        }

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

    private void saveSource(String source) {
        SharedPreferences pref = getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        pref.edit().putString(Constants.SOURCE, source).commit();
    }

    private String getSource() {
        return getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE).getString(Constants.SOURCE, Sources.GITHUB);
    }
}
