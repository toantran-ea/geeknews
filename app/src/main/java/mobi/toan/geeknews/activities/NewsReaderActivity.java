package mobi.toan.geeknews.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.R;
import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.fragments.NewsReaderFragment;
import mobi.toan.geeknews.models.net.NewsItem;
import mobi.toan.geeknews.utils.PrefUtils;

/**
 * Created by toantran on 10/25/15.
 */
public class NewsReaderActivity extends BaseApplicationActivity {
    private static final String NEWS_READER_TAG = "news_reader_tag";
    private static final String TAG = NewsReaderActivity.class.getSimpleName();
    private String mTargetUrl;
    private NewsReaderFragment mReaderFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reader);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTargetUrl = getIntent().getStringExtra(Constants.TARGET_URL);
        mReaderFragment = NewsReaderFragment.newInstance(mTargetUrl);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_holder, mReaderFragment, NEWS_READER_TAG).commit();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news_reader, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_bookmark:
                toggleBookmark(item, mTargetUrl);
                return true;
            default:
                return false;

        }
    }

    private void updateMenuState(MenuItem item, boolean isBookmarked) {
        if (isBookmarked) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_bookmark_white_48dp));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_bookmark_border_white_48dp));
        }
    }

    private void toggleBookmark(MenuItem item, String url) {
        boolean isBookmarked = checkBookmarked(url);
        if(!isBookmarked) {
            PrefUtils.saveBookmark(url, true);
        } else {
            PrefUtils.saveBookmark(url, false);
        }
        // update state of bookmark icon
        updateMenuState(item, !isBookmarked);
    }

    private boolean checkBookmarked(String url) {
        return PrefUtils.isBookmarked(url);
    }
}
