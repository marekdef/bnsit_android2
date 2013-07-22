package pl.bnsit.aa2.geocode.json;


import java.util.ArrayList;
import java.util.List;

public class Results {

    private java.util.List<Result> results = new ArrayList<Result>();
    private java.lang.String status;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
