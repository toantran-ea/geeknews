package mobi.toan.geeknews.models.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by toantran on 10/19/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubItem {
    private String title;

    private String description;

    private Source source;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Source {
        public String targetUrl;

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        @Override
        public String toString() {
            return "Source{" +
                    "targetUrl='" + targetUrl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GitHubItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", source=" + source +
                '}';
    }
}
