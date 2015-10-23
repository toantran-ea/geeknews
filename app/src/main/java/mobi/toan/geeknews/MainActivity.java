package mobi.toan.geeknews;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.models.bus.NewsSelectedMessage;

public class MainActivity extends AppCompatActivity {
    private static final String NEWS_LIST_TAG = "NEWS_LIST";
    private static final String NEWS_DETAIL_TAG = "NEWS_DETAIL";
    private static final String GITHUB_TRENDING_TITLE = "Github trending";

    private NewsDetailFragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsListFragment fragment = NewsListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_holder, fragment, NEWS_LIST_TAG).commit();

        EventBus.getDefault().register(this);

        getSupportActionBar().setTitle(GITHUB_TRENDING_TITLE);
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
        mDetailFragment = NewsDetailFragment.newInstance(event.getTargetUrl());
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
                getSupportActionBar().setTitle(GITHUB_TRENDING_TITLE);
            }
        }

        super.onBackPressed();
    }
}
