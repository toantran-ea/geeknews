package mobi.toan.geeknews.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.R;
import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.fragments.NewsReaderFragment;
import mobi.toan.geeknews.utils.SourcesResolver;

/**
 * Created by toantran on 10/25/15.
 */
public class NewsReaderActivity extends BaseApplicationActivity {
    private static final String NEWS_READER_TAG = "news_reader_tag";

    private NewsReaderFragment mReaderFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_reader);

        String url = getIntent().getStringExtra(Constants.TARGET_URL);
        Log.e("AA", ">>> url" +url);
        mReaderFragment = NewsReaderFragment.newInstance(url);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_holder, mReaderFragment, NEWS_READER_TAG).commit();

        EventBus.getDefault().register(this);

        getSupportActionBar().setTitle(SourcesResolver.getBeautifulName(this, getSource()));
    }


    @Override
    public void onBackPressed() {
        // Back on the webview scope
//        if (mDetailFragment != null && mDetailFragment.getWebView().canGoBack()) {
//            mDetailFragment.getWebView().goBack();
//            return;
//        } else {
//            if( mDetailFragment.getWebView() != null && !mDetailFragment.getWebView().canGoBack()) {
//                getSupportActionBar().setTitle(SourcesResolver.getBeautifulName(this, getSource()));
//            }
//        }

        super.onBackPressed();
    }
}
