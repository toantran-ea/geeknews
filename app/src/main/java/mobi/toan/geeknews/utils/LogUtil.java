package mobi.toan.geeknews.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import mobi.toan.geeknews.BuildConfig;

/**
 * Created by toantran on 12/16/15.
 */
public class LogUtil {
    private static Toast sToast;
    public static void e(String TAG, String ... messages) {
        if(BuildConfig.DEBUG && messages.length > 0) {
            Log.e(TAG, TextUtils.join(" ", messages));
        }
    }

    public static void d(String TAG, String ... messages) {
        if(BuildConfig.DEBUG && messages.length > 0) {
            Log.d(TAG, TextUtils.join(" ", messages));
        }
    }

    public static void toast(Context context, String message) {
        if(sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        sToast.show();
    }
}
