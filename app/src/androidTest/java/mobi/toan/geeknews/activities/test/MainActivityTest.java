package mobi.toan.geeknews.activities.test;

import android.app.Activity;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mobi.toan.geeknews.R;
import mobi.toan.geeknews.activities.MainActivity;
import mobi.toan.geeknews.constants.Sources;
import mobi.toan.geeknews.utils.PrefUtils;
import mobi.toan.geeknews.utils.SourcesResolver;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by toantran on 1/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);
    public Activity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = mRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        PrefUtils.reset();
    }

    @Test
    public void display_default_github_trending_on_opening_app() throws Exception {
        onView(withText(SourcesResolver.getBeautifulName(mActivity, Sources.GITHUB))).check(
                matches(isDisplayed())
        );
    }

    @Test
    public void testDisplay_news_sources() throws Exception {
        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withText(R.string.email)).check(matches(isDisplayed()));
    }

    @Test
    public void testSelect_news_source() throws Exception {
        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withText(R.string.hackernews)).perform(click());
        Thread.sleep(10000);
        onView(allOf(withText(SourcesResolver.getBeautifulName(mActivity, Sources.HACKER_NEWS)),
                isDescendantOfA(withId(R.id.toolbar)))).check(
                matches(isDisplayed())
        );
    }
}
