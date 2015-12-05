package group_14.software_engineering_project_group_14_bles.evaluation;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jerry on 11/25/2015.
 */
public class Evaluator {

    private float getDistance(LatLng start, LatLng end) {
        float[] results = new float[3];
        Location.distanceBetween(start.latitude, start.longitude,
                end.latitude, end.longitude, results);
        return results[0];
    }



}
