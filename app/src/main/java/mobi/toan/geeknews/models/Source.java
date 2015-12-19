package mobi.toan.geeknews.models;

/**
 * Created by toantran on 12/17/15.
 */
public class Source {
    private String dataCriteria;
    private String sourceId;

    public Source(String dataCriteria, String sourceId) {
        this.dataCriteria = dataCriteria;
        this.sourceId = sourceId;
    }

    public String getDataCriteria() {
        return dataCriteria;
    }

    public void setDataCriteria(String dataCriteria) {
        this.dataCriteria = dataCriteria;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
