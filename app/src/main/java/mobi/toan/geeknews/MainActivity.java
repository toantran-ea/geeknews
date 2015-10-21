package mobi.toan.geeknews;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mobi.toan.geeknews.service.NewsListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GeekAPI.getInstance().getService().getGitHubNews(new Callback<ArrayList<GitHubItem>>() {
//            @Override
//            public void success(ArrayList<GitHubItem> githubItems, Response response) {
//                Log.e("AAAA", ">>> " + githubItems);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });

        NewsListFragment fragment = NewsListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_holder, fragment, "NEWS_LIST").commit();

    }
}
