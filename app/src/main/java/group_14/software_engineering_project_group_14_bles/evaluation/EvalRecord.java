package group_14.software_engineering_project_group_14_bles.evaluation;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Jerry on 11/21/2015.
 */
public class EvalRecord {

    private String name;
    private LatLng point;

    public EvalRecord(String name, LatLng point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return this.name;
    }

    public LatLng getPoint() {
        return this.point;
    }

}
