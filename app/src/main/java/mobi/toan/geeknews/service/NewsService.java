package mobi.toan.geeknews.service;

import java.util.ArrayList;
import java.util.List;

import mobi.toan.geeknews.models.net.GitHubItem;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by toantran on 10/13/15.
 */
public interface NewsService {

    @Headers("Content-Type: application/json")
    @GET("/v1/sources/github/popular")
    Call<List<GitHubItem>> getGitHubNews(@Query("page") int page, @Query("size") int size);
}
