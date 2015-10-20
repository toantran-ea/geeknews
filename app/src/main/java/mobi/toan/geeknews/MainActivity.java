package mobi.toan.geeknews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mobi.toan.geeknews.models.net.GithubItem;
import mobi.toan.geeknews.service.GeekAPI;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeekAPI.getInstance().getService().getGitHubNews(new Callback<ArrayList<GithubItem>>() {
            @Override
            public void success(ArrayList<GithubItem> githubItems, Response response) {
                Log.e("AAAA", ">>> " + githubItems);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
