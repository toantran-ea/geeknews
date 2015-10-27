package mobi.toan.geeknews.activities;

import android.support.v7.app.AppCompatActivity;

import mobi.toan.geeknews.utils.PrefUtils;

/**
 * Created by toantran on 10/25/15.
 */
public class BaseApplicationActivity extends AppCompatActivity {

    protected void saveSource(String source) {
        PrefUtils.saveSource(source);
    }

    protected  String getSource() {
        return PrefUtils.getSource();
    }

}
