package mobi.toan.geeknews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.constants.Sources;

/**
 * Created by toantran on 10/27/15.
 */
public class PrefUtils {
    private static SharedPreferences sSharedPreferences;
    public static void init(Context context) {
        sSharedPreferences =  context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
    }

    public static void saveSource(String source) {
        checkInitState();
        sSharedPreferences.edit().putString(Constants.SOURCE, source).commit();
    }

    public static String getSource() {
        checkInitState();
        return sSharedPreferences.getString(Constants.SOURCE, Sources.GITHUB);
    }

    private static void checkInitState() {
        if(sSharedPreferences == null) {
            throw new IllegalStateException("Must initialize this PrefUtils in Application first before use it!");
        }
    }
}
