package group_14.software_engineering_project_group_14_bles;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//==============================================================
//The following contexts are for Adding marker and defined circles
//==============================================================
public class DraggableCircle {

    public static final double RADIUS_OF_EARTH_METERS = 6371009;

    public final Marker centerMarker;

//    public final Marker radiusMarker;

    public final Circle circle;

    private double radius = 1000;

    private GoogleMap mMap;

    private int mStrokeColor = Color.BLACK;

    private int mFillColor;

    public DraggableCircle(LatLng center,GoogleMap mMap) {
        this.mMap = mMap;
        mFillColor = Color.HSVToColor(50, new float[]{1, 1, 1});

        centerMarker = mMap.addMarker(new MarkerOptions()
                .position(center)
                .title("Evaluation(Test)")
                .snippet("Please click on this window for evaluating current location")
                .draggable(true));

//        radiusMarker = mMap.addMarker(new MarkerOptions()
//                .position(toRadiusLatLng(center, radius))
//                .draggable(true)
//                .icon(BitmapDescriptorFactory.defaultMarker(
//                        BitmapDescriptorFactory.HUE_AZURE)));

        circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeWidth(0)
                .strokeColor(mStrokeColor)
                .fillColor(mFillColor));

//        circle.setVisible(false);

    }


//    public boolean onMarkerMoved(Marker marker) {
//        if (marker.equals(centerMarker)) {
//            circle.setCenter(marker.getPosition());
//            radiusMarker.setPosition(toRadiusLatLng(marker.getPosition(), radius));
//            return true;
//        }
//        if (marker.equals(radiusMarker)) {
//            radius = toRadiusMeters(centerMarker.getPosition(), radiusMarker.getPosition());
//            circle.setRadius(radius);
//            return true;
//        }
//        return false;
//    }


    //==============================================================
    //calculate the distance between center and radius marker
    //==============================================================
    private static double toRadiusMeters(LatLng center, LatLng radius) {
        float[] result = new float[1];
        Location.distanceBetween(center.latitude, center.longitude,
                radius.latitude, radius.longitude, result);
        return result[0];
    }
}

