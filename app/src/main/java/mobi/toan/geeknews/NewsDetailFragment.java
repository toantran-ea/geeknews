package mobi.toan.geeknews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by toantran on 10/21/15.
 */
public class NewsDetailFragment extends Fragment {
    private static final String TARGET_URL = "target-url";
    private static final String TAG = NewsDetailFragment.class.getSimpleName();
    private View mRootView;
    private String mTargetUrl;
    private WebView mWebView;

    public static NewsDetailFragment newInstance(String targetUrl) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TARGET_URL, targetUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    public NewsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(TARGET_URL)) {
            mTargetUrl = bundle.getString(TARGET_URL);
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
                if (progress >= 55) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
            }
        });

        if(!TextUtils.isEmpty(mTargetUrl)) {
            mWebView.loadUrl(mTargetUrl);
        } else {
            Toast.makeText(getActivity(), R.string.message_empty_content_showing, Toast.LENGTH_SHORT).show();
        }
    }

    public WebView getWebView() {
        return mWebView;
    }
}
