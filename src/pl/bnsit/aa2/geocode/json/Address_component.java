package pl.bnsit.aa2.geocode.json;


import java.util.ArrayList;
import java.util.List;

public class Address_component {
    private java.lang.String long_name;
    private java.lang.String short_name;
    private java.util.List<String> types = new ArrayList<String>();

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
