package mobi.toan.geeknews.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.R;
import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.constants.Criteria;
import mobi.toan.geeknews.constants.Sources;
import mobi.toan.geeknews.models.Source;
import mobi.toan.geeknews.models.bus.NewsSelectedMessage;
import mobi.toan.geeknews.models.bus.SourceSelectedMessage;
import mobi.toan.geeknews.models.net.NewsItem;
import mobi.toan.geeknews.services.GeekAPI;
import mobi.toan.geeknews.utils.LogUtil;
import mobi.toan.geeknews.utils.PrefUtils;
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
    private static final int PAGE_SIZE = 30;
    private static final int INIT_PAGE = 1;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mCriteriaTab;
    private View mPopularTabView;
    private View mLatestTabView;
    private List<NewsItem> mDataSet = new ArrayList<>();

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
        return View.inflate(getActivity(), R.layout.fragment_news_list_content, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadNews(getSourceId());
        displayCriteriaTabBar(getSourceId());
    }

    public void onEvent(SourceSelectedMessage message) {
        loadNews(message.getSourceId());
        displayCriteriaTabBar(message.getSourceId());
    }

    private void displayCriteriaTabBar(final String sourceId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Source source = SourcesResolver.SOURCE_LIST.get(sourceId);
                if (source.getDataCriteria().equals(Criteria.BOTH)) {
                    mCriteriaTab.setVisibility(View.VISIBLE);
                } else {
                    mCriteriaTab.setVisibility(View.GONE);
                }

                // update selected states of tab-views
                if (PrefUtils.getCriteria(sourceId).equals(Criteria.LATEST)) {
                    mLatestTabView.setBackgroundResource(R.drawable.text_view_selected_state);
                    mPopularTabView.setBackgroundResource(R.drawable.text_view_unselected_state);
                } else if (PrefUtils.getCriteria(sourceId).equals(Criteria.POPULAR)) {
                    mLatestTabView.setBackgroundResource(R.drawable.text_view_unselected_state);
                    mPopularTabView.setBackgroundResource(R.drawable.text_view_selected_state);
                }

                loadNews(getSourceId());
            }
        });
    }

    private void initUI(View view) {
        initTabView(view);

        initPullToRefreshView(view);

        initNewsRecycleView(view);
    }

    private void initNewsRecycleView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.news_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsItem newsItem = mAdapter.getItem(position);
                NewsSelectedMessage message =
                        new NewsSelectedMessage(newsItem.getSource().getTargetUrl(),
                                newsItem.getTitle());
                EventBus.getDefault().post(message);
                LogUtil.e(TAG, newsItem.toString());
            }
        }));

        mAdapter = new NewsAdapter(getActivity(), mDataSet);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPullToRefreshView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        // When user pulls down the list to refresh news list.
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(getSourceId());
            }
        });
    }

    private void initTabView(View view) {
        mCriteriaTab = view.findViewById(R.id.criteria_tab);
        mPopularTabView = view.findViewById(R.id.tab_popular);
        mLatestTabView = view.findViewById(R.id.tab_latest);

        View.OnClickListener tabViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tab_latest:
                        if (PrefUtils.getCriteria(getSourceId()).equals(Criteria.LATEST)) {
                            return;
                        }
                        PrefUtils.saveCriteria(getSourceId(), Criteria.LATEST);
                        break;
                    case R.id.tab_popular:
                        if (PrefUtils.getCriteria(getSourceId()).equals(Criteria.POPULAR)) {
                            return;
                        }
                        PrefUtils.saveCriteria(getSourceId(), Criteria.POPULAR);
                        break;
                    default:
                        break;
                }
                displayCriteriaTabBar(getSourceId());
            }
        };
        mPopularTabView.setOnClickListener(tabViewClickListener);
        mLatestTabView.setOnClickListener(tabViewClickListener);
    }

    /**
     * Loads the news from sources.
     */
    private void loadNews(String source) {
        mAdapter.updateDataSet(null);
        Call<List<NewsItem>> gitHubNewsCall = GeekAPI.getInstance().getService().getNewsList(source,
                PrefUtils.getCriteria(getSourceId()), INIT_PAGE, PAGE_SIZE);
        gitHubNewsCall.enqueue(new Callback<List<NewsItem>>() {
            @Override
            public void onResponse(Response<List<NewsItem>> response, Retrofit retrofit) {
                mAdapter.updateDataSet(response.body());
                mRecyclerView.scrollToPosition(0);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtil.e(TAG, t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(SourcesResolver.getBeautifulName(getActivity(), source));
        }
    }

    private String getSourceId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.SOURCE, Sources.GITHUB);
    }
}
