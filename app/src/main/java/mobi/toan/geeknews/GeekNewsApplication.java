package mobi.toan.geeknews;

import android.app.Application;

import mobi.toan.geeknews.utils.PrefUtils;

/**
 * Created by toantran on 10/27/15.
 */
public class GeekNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtils.init(getApplicationContext());
    }
}
