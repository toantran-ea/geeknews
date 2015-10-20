package mobi.toan.geeknews.service;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import mobi.toan.geeknews.Constants;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by toantran on 10/19/15.
 */
public class GeekAPI {
    private static final String TAG = GeekAPI.class.getSimpleName();

    private static GeekAPI sInstance = new GeekAPI();
    private NewsService mService;

    private GeekAPI() {
        OkHttpClient client = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                }).setConverter(new JacksonConverter())
                .setClient(new OkClient(client))
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
