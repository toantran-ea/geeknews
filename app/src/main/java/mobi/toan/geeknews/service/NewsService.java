package mobi.toan.geeknews.service;

import java.util.ArrayList;

import mobi.toan.geeknews.models.net.GithubItem;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by toantran on 10/13/15.
 */
public interface NewsService {

    @Headers("Content-Type: application/json")
    @GET("/v1/sources/github/popular?page=1&size=20")
    void getGitHubNews(Callback<ArrayList<GithubItem>> callback);
}
