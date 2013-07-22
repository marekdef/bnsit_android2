package pl.bnsit.aa2.geocode.json;

import java.util.ArrayList;
import java.util.List;


public class Result {
    private static final Class CLAZZ = Result.class;

    private java.util.List<Address_component> address_components = new ArrayList<Address_component>();
    private java.lang.String formatted_address;
    private Geometry geometry;
    private java.util.List<String> types = new ArrayList<String>();

    public List<Address_component> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<Address_component> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}
