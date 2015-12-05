package group_14.software_engineering_project_group_14_bles;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jerry on 11/24/2015.
 */
public class Facility {

    private String id;
    private String address;
    private double lat;
    private double lng;
    private String other;
    private String category;

    public Facility(String id, String address, double lat, double lng, String other, String category) {
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.other = other;
        this.category = category;
    }

    public String getId() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }

    public LatLng getLatLng() {
        return new LatLng(this.lat, this.lng);
    }

    public String getOtherInfo() {
        return this.other;
    }

    public String getCategory() {
        return this.category;
    }
}
