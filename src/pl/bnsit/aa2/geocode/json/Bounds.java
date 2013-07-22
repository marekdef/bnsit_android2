package pl.bnsit.aa2.geocode.json;


public class Bounds {

    private LatLong northeast;
    private LatLong southwest;

    public LatLong getNortheast() {
        return northeast;
    }

    public void setNortheast(LatLong northeast) {
        this.northeast = northeast;
    }

    public LatLong getSouthwest() {
        return southwest;
    }

    public void setSouthwest(LatLong southwest) {
        this.southwest = southwest;
    }

}
