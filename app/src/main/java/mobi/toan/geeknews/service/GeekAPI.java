package mobi.toan.geeknews.service;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import mobi.toan.geeknews.Constants;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by toantran on 10/19/15.
 */
public class GeekAPI {
    private static final int TIMEOUT = 10000; // 10 secs
    private static final String TAG = GeekAPI.class.getSimpleName();

    private static GeekAPI sInstance = new GeekAPI();
    private NewsService mService;

    private GeekAPI() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        client.setRetryOnConnectionFailure(true);
        client.interceptors().add(new LoggingInterceptor());
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Constants.API_URL).addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
        mService = restAdapter.create(NewsService.class);
    }

    public static GeekAPI getInstance() {
        return sInstance;
    }

    public void setService(NewsService service) {
        mService = service;
    }

    public NewsService getService() {
        return mService;
    }
}
