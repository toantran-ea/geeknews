package mobi.toan.geeknews.service;

import java.util.ArrayList;
import java.util.List;

import mobi.toan.geeknews.models.net.GitHubItem;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by toantran on 10/13/15.
 */
public interface NewsService {

    @Headers("Content-Type: application/json")
    @GET("/v1/sources/github/popular?page=1&size=20")
    Call<List<GitHubItem>> getGitHubNews();
}
