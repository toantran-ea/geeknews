package mobi.toan.geeknews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cjj.SnailBar;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.R;
import mobi.toan.geeknews.models.bus.BackPressedEvent;
import mobi.toan.geeknews.utils.PrefUtils;
import mobi.toan.geeknews.utils.SourcesResolver;

/**
 * Created by toantran on 10/21/15.
 */
public class NewsReaderFragment extends Fragment {
    private static final String TARGET_URL = "target-url";
    private static final String TAG = NewsReaderFragment.class.getSimpleName();
    private static final int HIDE_PROGRESS_LEVEL = 80;
    private View mRootView;
    private String mTargetUrl;
    private WebView mWebView;

    public static NewsReaderFragment newInstance(String targetUrl) {
        NewsReaderFragment fragment = new NewsReaderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TARGET_URL, targetUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    public NewsReaderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(TARGET_URL)) {
            mTargetUrl = bundle.getString(TARGET_URL);
        }
        EventBus.getDefault().register(this);
    }

    public void onEvent(BackPressedEvent event) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_news_detail, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadUrl();
    }

    private void loadUrl() {
        mWebView = (WebView) mRootView.findViewById(R.id.web_view);
        final SnailBar progressBar = (SnailBar) mRootView.findViewById(R.id.progress_bar);

        mWebView.setInitialScale(1);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setScrollbarFadingEnabled(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress >= HIDE_PROGRESS_LEVEL) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            @Nullable
            public void onReceivedTitle(WebView view, String title) {
                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if(actionBar != null) {
                    actionBar.setTitle(title);
                }
            }
        });

        if (!TextUtils.isEmpty(mTargetUrl)) {
            mWebView.loadUrl(mTargetUrl);
        } else {
            Toast.makeText(getActivity(), R.string.message_empty_content_showing, Toast.LENGTH_SHORT).show();
        }
    }

}
