package mobi.toan.geeknews.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import mobi.toan.geeknews.utils.LogUtil;
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
    private View mRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<NewsItem> mDataSet = new ArrayList<>();
    private int mCurrentPage = INIT_PAGE;

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
        loadNews(getSource(), mCurrentPage);
    }

    public void onEvent(SourceSelectedMessage message) {
        loadNews(message.getSource(), mCurrentPage);
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.news_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_refresh_layout);

        // When user pulls down the list to refresh news list.
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), getSource(), Toast.LENGTH_SHORT).show();
                loadNews(getSource(), mCurrentPage);
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
                NewsSelectedMessage message = new NewsSelectedMessage(newsItem.getSource().getTargetUrl(), newsItem.getTitle());
                EventBus.getDefault().post(message);
                LogUtil.e(TAG, newsItem.toString());
            }
        }));

        mAdapter = new NewsAdapter(getActivity(), mDataSet);
        mAdapter.setOnLoadMoreListener(new NewsAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add progress item
//                mDataSet.add(null);
//                mAdapter.notifyItemInserted(mDataSet.size() - 1);
//                loadNews(getSource(), mCurrentPage);
                LogUtil.toast(getActivity(), getString(R.string.loading_more,
                        SourcesResolver.getBeautifulName(getContext(), getSource())));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Loads the news from sources.
     */
    private void loadNews(String source, int page) {
        LogUtil.e("NewsListFragment", "load News");
        mAdapter.updateDataSet(null);
        Call<List<NewsItem>> gitHubNewsCall = GeekAPI.getInstance().getService().getNewsList(source, source.equals(Sources.GITHUB) ? Criteria.POPULAR : Criteria.LATEST, INIT_PAGE, PAGE_SIZE);
        gitHubNewsCall.enqueue(new Callback<List<NewsItem>>() {
            @Override
            public void onResponse(Response<List<NewsItem>> response, Retrofit retrofit) {
                mAdapter.updateDataSet(response.body());
                mRecyclerView.scrollToPosition(0);
                mSwipeRefreshLayout.setRefreshing(false);
                finishLoadingMore();
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtil.e(TAG, t.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);
                finishLoadingMore();
            }
        });
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(SourcesResolver.getBeautifulName(getActivity(), source));
        } catch (NullPointerException ex) {
            // will not set the title in case of null exception
        }
    }

    private String getSource() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.SOURCE, Sources.GITHUB);
    }

    private void finishLoadingMore() {
        if(mAdapter.isLoading()) {
            mDataSet.remove(mDataSet.size() - 1);
            mAdapter.notifyItemRemoved(mDataSet.size());
            //add items one by one
            for (int i = 0; i < mDataSet.size(); i++) {
                mAdapter.notifyItemInserted(mDataSet.size());
            }
            mAdapter.setLoaded();
            //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
        }
    }
}
