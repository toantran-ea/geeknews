package mobi.toan.geeknews.activities;

import android.support.v7.app.AppCompatActivity;

import net.hockeyapp.android.CrashManager;

import mobi.toan.geeknews.BuildConfig;
import mobi.toan.geeknews.utils.PrefUtils;

/**
 * Created by toantran on 10/25/15.
 */
public class BaseApplicationActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        checkForCrashes();
    }

    protected void saveSource(String source) {
        PrefUtils.saveSource(source);
    }

    protected  String getSource() {
        return PrefUtils.getSource();
    }

    private void checkForCrashes() {
        CrashManager.register(this, BuildConfig.HOCKEY_APP_ID);
    }
}
