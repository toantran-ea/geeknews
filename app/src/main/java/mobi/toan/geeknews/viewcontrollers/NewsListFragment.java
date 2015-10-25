package mobi.toan.geeknews.viewcontrollers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.R;
import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.constants.Criteria;
import mobi.toan.geeknews.constants.Sources;
import mobi.toan.geeknews.models.bus.NewsSelectedMessage;
import mobi.toan.geeknews.models.bus.SourceSelectedMessage;
import mobi.toan.geeknews.models.net.NewsItem;
import mobi.toan.geeknews.services.GeekAPI;
import mobi.toan.geeknews.utils.SourcesResolver;
import mobi.toan.geeknews.views.DividerItemDecoration;
import mobi.toan.geeknews.views.NewsAdapter;
import mobi.toan.geeknews.views.RecyclerItemClickListener;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by toantran on 10/20/15.
 */
public class NewsListFragment extends Fragment {
    private static final String TAG = NewsListFragment.class.getSimpleName();
    private View mRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = View.inflate(getActivity(), R.layout.fragment_news_list_content, null);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        initUI();
        loadNews(getSource());
    }

    public void onEvent(SourceSelectedMessage message) {
        loadNews(message.getSource());
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.news_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);

        // When user pulls down the list to refresh news list.
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), getSource(), Toast.LENGTH_SHORT).show();
                loadNews(getSource());
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsItem newsItem = mAdapter.getItem(position);
                NewsSelectedMessage message  = new NewsSelectedMessage(newsItem.getSource().getTargetUrl(), newsItem.getTitle());
                EventBus.getDefault().post(message);
                Log.e(TAG, newsItem.toString());
            }
        }));

        mAdapter = new NewsAdapter(getActivity(), new ArrayList<NewsItem>());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Loads the news from sources.
     */
    private void loadNews(String source) {
        Call<List<NewsItem>> gitHubNewsCall = GeekAPI.getInstance().getService().getNewsList(source, Criteria.LATEST, 1, 30);
        gitHubNewsCall.enqueue(new Callback<List<NewsItem>>() {
            @Override
            public void onResponse(Response<List<NewsItem>> response, Retrofit retrofit) {
                mAdapter.updateDataSet(response.body());
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(SourcesResolver.getBeautifulName(getActivity(), source));
    }

    private String getSource() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.SOURCE, Sources.GITHUB);
    }
}
