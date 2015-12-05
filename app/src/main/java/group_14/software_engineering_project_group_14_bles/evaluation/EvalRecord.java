package group_14.software_engineering_project_group_14_bles.evaluation;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Jerry on 11/21/2015.
 */
public class EvalRecord {

    private String name;
    private Date creationTime;
    private LatLng point;

    public EvalRecord(String name, Date time, LatLng point) {
        this.name = name;
        this.creationTime = time;
        this.point = point;
    }

    public String getName() {
        return this.name;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public LatLng getPoint() {
        return this.point;
    }

}
