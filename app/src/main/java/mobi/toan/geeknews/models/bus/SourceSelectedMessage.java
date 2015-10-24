package mobi.toan.geeknews.models.bus;

/**
 * Created by toantran on 10/23/15.
 */
public class SourceSelectedMessage {
    public String source;

    public SourceSelectedMessage(String source) {
        setSource(source);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
