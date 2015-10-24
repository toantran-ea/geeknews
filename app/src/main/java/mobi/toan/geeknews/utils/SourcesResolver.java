package mobi.toan.geeknews.utils;

import android.content.Context;
import android.content.res.Resources;

import mobi.toan.geeknews.R;
import mobi.toan.geeknews.Sources;

/**
 * Created by toantran on 10/23/15.
 */
public class SourcesResolver {
    public static String resolve(int id) {
        switch (id) {
            case R.id.nav_github:
                return Sources.GITHUB;
            case R.id.nav_angel:
                return Sources.ANGEL;
            case R.id.nav_betalist:
                return Sources.BETALIST;
            case R.id.nav_csstricks:
                return Sources.CSSTRICKS;
            case R.id.nav_hackernews:
                return Sources.HACKER_NEWS;
            case R.id.nav_product_hunt:
                return Sources.PRODUCT_HUNT;
            case R.id.nav_sidebar:
                return Sources.SIDEBAR;
            case R.id.nav_fastcompany:
                return Sources.FASTCOMPANY;
            case R.id.nav_designernews:
                return Sources.DESIGNERNEWS;
            case R.id.nav_alistapart:
                return Sources.ALISTAPART;
            case R.id.nav_datatau:
                return Sources.DATATAU;
            case R.id.nav_dzone:
                return Sources.DZONE;
            case R.id.nav_echojs:
                return Sources.ECHOJS;
            case R.id.nav_fastcodesign:
                return Sources.FASTCODESIGN;
            case R.id.nav_frontendfront:
                return Sources.FRONTENDFRONT;
            case R.id.nav_growthhackers:
                return Sources.GROWTHHACKERS;
            case R.id.nav_hackingui:
                return Sources.HACKINGUI;
            case R.id.nav_inbound:
                return Sources.INBOUND;
            case R.id.nav_littlebigdetails:
                return Sources.LITTLEBIGDETAILS;
            case R.id.nav_mashable:
                return Sources.MASHABLE;
            case R.id.nav_medium:
                return Sources.MEDIUM;
            case R.id.nav_recode:
                return Sources.RECODE;
            case R.id.nav_researchers:
                return Sources.RESEARCHERS;
            case R.id.nav_smashingmagazine:
                return Sources.SMASHINGMAGAZINE;
            case R.id.nav_swissmiss:
                return Sources.SWISSMISS;
            case R.id.nav_techcrunch:
                return Sources.TECHCRUNCH;
            case R.id.nav_thenextweb:
                return Sources.THENEXTWEB;
            case R.id.nav_theverge:
                return Sources.THEVERGE;
            case R.id.nav_uxhandy:
                return Sources.UXHANDY;
            case R.id.nav_webdesignernews:
                return Sources.WEBDESIGNERNEWS;
            case R.id.nav_wired:
                return Sources.WIRED;
        }
        return Sources.GITHUB;
    }

    public static String getBeautifulName(Context context, String source) {
        Resources res = context.getResources();
        switch (source) {
            case Sources.CSSTRICKS:
                return res.getString(R.string.csstricks);
            case Sources.GITHUB:
                return res.getString(R.string.github);
            case Sources.ANGEL:
                return res.getString(R.string.angel);
            case Sources.BETALIST:
                return res.getString(R.string.beta_list);
            case Sources.HACKER_NEWS:
                return res.getString(R.string.hackernews);
            case Sources.LOBSTERS:
                return res.getString(R.string.lobsters);
            case Sources.PRODUCT_HUNT:
                return res.getString(R.string.production_hunt);
            case Sources.SIDEBAR:
                return res.getString(R.string.sidebar);
            case Sources.FASTCOMPANY:
                return res.getString(R.string.fastcompany);
            case Sources.DESIGNERNEWS:
                return res.getString(R.string.designernews);
            case Sources.ALISTAPART:
                return res.getString(R.string.alistapart);
            case Sources.DATATAU:
                return res.getString(R.string.datatau);
            case Sources.DZONE:
                return res.getString(R.string.dzone);
            case Sources.ECHOJS:
                return res.getString(R.string.echojs);
            case Sources.FASTCODESIGN:
                return res.getString(R.string.fastcodesigner);
            case Sources.FRONTENDFRONT:
                return res.getString(R.string.frontendfront);
            case Sources.GROWTHHACKERS:
                return res.getString(R.string.growthhackers);
            case Sources.HACKINGUI:
                return res.getString(R.string.hackingui);
            case Sources.INBOUND:
                return res.getString(R.string.inbound);
            case Sources.LITTLEBIGDETAILS:
                return res.getString(R.string.littlebigdetails);
            case Sources.MASHABLE:
                return res.getString(R.string.mashable);
            case Sources.MEDIUM:
                return res.getString(R.string.medium);
            case Sources.RECODE:
                return res.getString(R.string.recode);
            case Sources.RESEARCHERS:
                return res.getString(R.string.researchers);
            case Sources.SMASHINGMAGAZINE:
                return res.getString(R.string.smashingmagazine);
            case Sources.SWISSMISS:
                return res.getString(R.string.swissmiss);
            case Sources.TECHCRUNCH:
                return res.getString(R.string.techcrunch);
            case Sources.THENEXTWEB:
                return res.getString(R.string.thenextweb);
            case Sources.THEVERGE:
                return res.getString(R.string.theverge);
            case Sources.UXHANDY:
                return res.getString(R.string.uxhandy);
            case Sources.WEBDESIGNERNEWS:
                return res.getString(R.string.webdesignernews);
            case Sources.WIRED:
                return res.getString(R.string.wired);
        }
        return "";
    }
}
