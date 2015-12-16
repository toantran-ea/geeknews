package mobi.toan.geeknews.services;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import mobi.toan.geeknews.utils.LogUtil;

/**
 * Created by toantran on 10/21/15.
 */
public class LoggingInterceptor implements Interceptor {
    public static final int LOG_LEVEL_SIMPLE = 0;
    public static final int LOG_LEVEL_VERBOSE = 1;
    private static final String TAG = "Retrofit";
    private int mLogLevel;

    public LoggingInterceptor(int logLevel) {
        mLogLevel = (logLevel == 0 || logLevel == 1) ? logLevel : LOG_LEVEL_SIMPLE;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        LogUtil.d(TAG, String.format("Request to %s", request.url()));
        Response response = chain.proceed(request);
        if (mLogLevel == LOG_LEVEL_VERBOSE) {
            long t2 = System.nanoTime();
            LogUtil.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        }
        String body = response.body().string();
        LogUtil.d(TAG, String.format("From %s \n--> %s", response.request().url(), body));
        response = response.newBuilder().body(ResponseBody.create(response.body().contentType(),
                body)).build();
        return response;
    }
}
