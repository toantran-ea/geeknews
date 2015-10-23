package mobi.toan.geeknews.models.bus;

/**
 * Created by toantran on 10/21/15.
 */
public class NewsSelectedMessage {

    public NewsSelectedMessage(String targetUrl, String title) {
        this.targetUrl = targetUrl;
        this.title = title;
    }

    private String targetUrl;

    private String title;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
