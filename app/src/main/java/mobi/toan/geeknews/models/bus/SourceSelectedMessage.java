package mobi.toan.geeknews.models.bus;

/**
 * Created by toantran on 10/23/15.
 */
public class SourceSelectedMessage {
    public String sourceId;

    public SourceSelectedMessage(String sourceId) {
        setSourceId(sourceId);
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
