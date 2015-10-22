package mobi.toan.geeknews;

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

import de.greenrobot.event.EventBus;
import mobi.toan.geeknews.models.bus.NewsSelectedMessage;
import mobi.toan.geeknews.models.net.GithubItem;
import mobi.toan.geeknews.service.GeekAPI;
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
        initUI();
        loadNews();
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.news_list);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GithubItem githubItem = mAdapter.getItem(position);
                NewsSelectedMessage message  = new NewsSelectedMessage(githubItem.getSource().getTargetUrl(), githubItem.getTitle());
                EventBus.getDefault().post(message);
                Log.e(TAG, githubItem.toString());
            }
        }));

        mAdapter = new NewsAdapter(getActivity(), new ArrayList<GithubItem>());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadNews() {
        Call<List<GithubItem>> gitHubNewsCall = GeekAPI.getInstance().getService().getGitHubNews(1, 30);
        gitHubNewsCall.enqueue(new Callback<List<GithubItem>>() {
            @Override
            public void onResponse(Response<List<GithubItem>> response, Retrofit retrofit) {
                mAdapter.updateDataSet(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
