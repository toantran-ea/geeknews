package mobi.toan.geeknews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.constants.Criteria;
import mobi.toan.geeknews.constants.Sources;

/**
 * Created by toantran on 10/27/15.
 */
public class PrefUtils {
    private static SharedPreferences sSharedPreferences;
    public static void init(Context context) {
        sSharedPreferences =  context.getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
    }

    public static void reset() {
        sSharedPreferences.edit().clear().apply();
    }

    public static void saveSource(String source) {
        checkInitState();
        sSharedPreferences.edit().putString(Constants.SOURCE, source).commit();
    }

    public static String getSource() {
        checkInitState();
        return sSharedPreferences.getString(Constants.SOURCE, Sources.GITHUB);
    }

    public static void saveCriteria(String sourceId, String criteria) {
        sSharedPreferences.edit().putString(sourceId, criteria).apply();
    }

    public static void saveBookmark(String url, boolean isBookmarked) {
        if(isBookmarked) {
            sSharedPreferences.edit().putBoolean(url, true).apply();
        } else {
            sSharedPreferences.edit().remove(url).apply();
        }
    }

    public static boolean isBookmarked(String url) {
        return sSharedPreferences.getBoolean(url, false);
    }

    /**
     * Default is LATEST
     * @param sourceId
     * @return
     */
    public static String getCriteria(String sourceId) {
        return sSharedPreferences.getString(sourceId, Criteria.LATEST);
    }

    private static void checkInitState() {
        if(sSharedPreferences == null) {
            throw new IllegalStateException("Must initialize this PrefUtils in Application first before use it!");
        }
    }
}
