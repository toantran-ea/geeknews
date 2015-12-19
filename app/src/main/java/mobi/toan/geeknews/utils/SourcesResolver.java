package mobi.toan.geeknews.utils;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.toan.geeknews.R;
import mobi.toan.geeknews.constants.Criteria;
import mobi.toan.geeknews.constants.Sources;
import mobi.toan.geeknews.models.Source;
import mobi.toan.geeknews.models.net.NewsItem;

/**
 * Created by toantran on 10/23/15.
 */
public class SourcesResolver {
    public static Map<String, Source> SOURCE_LIST = new HashMap<>();
    static {
        SOURCE_LIST = getSourceList();
    }

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
            case R.id.nav_lobsters:
                return Sources.LOBSTERS;
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

    private static Map<String, Source> getSourceList() {
        Map<String, Source> sources = new HashMap<>();

        Source aListApart = new Source(Criteria.LATEST, Sources.ALISTAPART);
        sources.put(Sources.ALISTAPART, aListApart);

        Source angleList = new Source(Criteria.BOTH, Sources.ANGEL);
        sources.put(Sources.ANGEL, angleList);

        Source betaList = new Source(Criteria.LATEST, Sources.BETALIST);
        sources.put(Sources.BETALIST, betaList);

        Source cssTricks = new Source(Criteria.LATEST, Sources.CSSTRICKS);
        sources.put(Sources.CSSTRICKS, cssTricks);

        Source dataTau = new Source(Criteria.BOTH, Sources.DATATAU);
        sources.put(Sources.DATATAU, dataTau);

        Source designerNews = new Source(Criteria.BOTH, Sources.DESIGNERNEWS);
        sources.put(Sources.DESIGNERNEWS, designerNews);

        Source dzone = new Source(Criteria.LATEST, Sources.DZONE);
        sources.put(Sources.DZONE, dzone);

        Source echoJS = new Source(Criteria.BOTH, Sources.ECHOJS);
        sources.put(Sources.ECHOJS, echoJS);

        Source fastCompany = new Source(Criteria.LATEST, Sources.FASTCOMPANY);
        sources.put(Sources.FASTCOMPANY, fastCompany);

        Source fastCoDesign = new Source(Criteria.BOTH, Sources.FASTCODESIGN);
        sources.put(Sources.FASTCODESIGN, fastCoDesign);

        Source frontEndFront = new Source(Criteria.BOTH, Sources.FRONTENDFRONT);
        sources.put(Sources.FRONTENDFRONT, frontEndFront);

        Source github = new Source(Criteria.LATEST, Sources.GITHUB);
        sources.put(Sources.GITHUB, github);

        Source growthHacker = new Source(Criteria.BOTH, Sources.GROWTHHACKERS);
        sources.put(Sources.GROWTHHACKERS, growthHacker);

        Source hackerNews = new Source(Criteria.BOTH, Sources.HACKER_NEWS);
        sources.put(Sources.HACKER_NEWS, hackerNews);

        Source hackingUI = new Source(Criteria.LATEST, Sources.HACKINGUI);
        sources.put(Sources.HACKINGUI, hackingUI);

        Source inbound = new Source(Criteria.BOTH, Sources.INBOUND);
        sources.put(Sources.INBOUND, inbound);

        Source littleBigDetails = new Source(Criteria.LATEST, Sources.LITTLEBIGDETAILS);
        sources.put(Sources.LITTLEBIGDETAILS, littleBigDetails);

        Source lobster = new Source(Criteria.BOTH, Sources.LOBSTERS);
        sources.put(Sources.LOBSTERS, lobster);

        Source mashable = new Source(Criteria.LATEST, Sources.MASHABLE);
        sources.put(Sources.MASHABLE, mashable);

        Source medium = new Source(Criteria.BOTH, Sources.MEDIUM);
        sources.put(Sources.MEDIUM, medium);

        Source productHunt = new Source(Criteria.LATEST, Sources.PRODUCT_HUNT);
        sources.put(Sources.PRODUCT_HUNT, productHunt);

        Source recode = new Source(Criteria.LATEST, Sources.RECODE);
        sources.put(Sources.RECODE, recode);

        Source researchers = new Source(Criteria.BOTH, Sources.RESEARCHERS);
        sources.put(Sources.RESEARCHERS, researchers);

        Source sideBar = new Source(Criteria.LATEST, Sources.SIDEBAR);
        sources.put(Sources.SIDEBAR, sideBar);

        Source smashing = new Source(Criteria.LATEST, Sources.SMASHINGMAGAZINE);
        sources.put(Sources.SMASHINGMAGAZINE, smashing);

        Source swissMiss = new Source(Criteria.LATEST, Sources.SWISSMISS);
        sources.put(Sources.SWISSMISS, swissMiss);

        Source techcrunch = new Source(Criteria.LATEST, Sources.TECHCRUNCH);
        sources.put(Sources.TECHCRUNCH, techcrunch);

        Source theNextWeb = new Source(Criteria.LATEST, Sources.THENEXTWEB);
        sources.put(Sources.THENEXTWEB, theNextWeb);

        Source theVerge = new Source(Criteria.LATEST, Sources.THEVERGE);
        sources.put(Sources.THEVERGE, theVerge);

        Source uxHandy = new Source(Criteria.LATEST, Sources.UXHANDY);
        sources.put(Sources.UXHANDY, uxHandy);

        Source webDesigner = new Source(Criteria.BOTH, Sources.WEBDESIGNERNEWS);
        sources.put(Sources.WEBDESIGNERNEWS, webDesigner);

        Source wired = new Source(Criteria.LATEST, Sources.WIRED);
        sources.put(Sources.WIRED, wired);

        return sources;
    }
}
