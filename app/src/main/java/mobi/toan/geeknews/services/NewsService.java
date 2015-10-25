package mobi.toan.geeknews.services;

import java.util.List;

import mobi.toan.geeknews.models.net.NewsItem;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by toantran on 10/13/15.
 */
public interface NewsService {

    @Headers("Content-Type: application/json")
    @GET("/v1/sources/{source}/{criteria}")
    Call<List<NewsItem>> getNewsList(@Path("source") String source, @Path("criteria") String criteria, @Query("page") int page, @Query("size") int size);
}
