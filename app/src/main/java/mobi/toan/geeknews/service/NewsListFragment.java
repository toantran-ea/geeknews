package mobi.toan.geeknews.service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobi.toan.geeknews.R;
import mobi.toan.geeknews.models.net.GitHubItem;
import mobi.toan.geeknews.views.DividerItemDecoration;
import mobi.toan.geeknews.views.NewsAdapter;
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
        mRootView = View.inflate(getActivity(), R.layout.news_list_content, null);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        loadNews();
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.news_list);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new NewsAdapter(getActivity(), new ArrayList<GitHubItem>());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadNews() {
        Call<List<GitHubItem>> gitHubNewsCall = GeekAPI.getInstance().getService().getGitHubNews();
        gitHubNewsCall.enqueue(new Callback<List<GitHubItem>>() {
            @Override
            public void onResponse(Response<List<GitHubItem>> response, Retrofit retrofit) {
                mAdapter.updateDataSet(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
