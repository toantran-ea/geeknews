package mobi.toan.geeknews.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import mobi.toan.geeknews.constants.Constants;
import mobi.toan.geeknews.constants.Sources;

/**
 * Created by toantran on 10/25/15.
 */
public class BaseApplicationActivity extends AppCompatActivity {

    protected void saveSource(String source) {
        SharedPreferences pref = getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
        pref.edit().putString(Constants.SOURCE, source).commit();
    }

    protected  String getSource() {
        return getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE).getString(Constants.SOURCE, Sources.GITHUB);
    }

}
