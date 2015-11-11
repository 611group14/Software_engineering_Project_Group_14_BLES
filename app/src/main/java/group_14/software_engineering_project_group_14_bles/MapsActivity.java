package group_14.software_engineering_project_group_14_bles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Import for drawing circle on map
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Marker;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

//for creating array list
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//for GPS feature
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

// buttom testing
import android.support.v4.app.ActivityCompat;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

//floating buttom testing
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

//for marker animation
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;


public class MapsActivity extends AppCompatActivity implements OnMarkerDragListener,
        OnMapLongClickListener,
        OnMapReadyCallback,
        OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback{

    //init parameters
    private GoogleMap mMap;

    private static final LatLng Windsor = new LatLng(42.289810, -82.999313);

    private static final double DEFAULT_RADIUS = 1000;

    public static final double RADIUS_OF_EARTH_METERS = 6371009;

    private List<DraggableCircle> mCircles = new ArrayList<DraggableCircle>(1);

    private int mStrokeColor = Color.BLACK;

    private int mFillColor;

    //init parameters for GPS features
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    //mark testing
    public Marker mark1;
    public Marker mark2;
    public Marker mark3;
    public Marker mark4;
    public Marker mark5;
    public Marker mark6;
    public Marker mark7;

    //latlngs list of Windsor
    private ArrayList<LatLng> latlngs = new ArrayList();

    //test
    private Polygon_Contain poly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        /**
         *defined action for floating bottom testing
         */
//        final View actionB = findViewById(R.id.action_b);
//
//        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
//
//        actionC.setTitle("Hide/Show Action above");
//        actionC.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            }
//        });
//
//        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
//
//        //method for adding button (test)
//        menuMultipleActions.addButton(actionC);
        //
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // move the camera to Windsor
//      mMap.moveCamera(CameraUpdateFactory.newLatLng(Windsor));
//      mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

        mFillColor = Color.HSVToColor(50, new float[]{1, 1, 1});

        // Move the map so that it is centered on the initial circle
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Windsor, 10f));

        //for gps feature
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        //highlight the area of Windsor
        latlngs.add(new LatLng(42.256350, -83.114807));
        latlngs.add(new LatLng(42.308929, -83.080475));
        latlngs.add(new LatLng(42.323145, -83.045112));
        latlngs.add(new LatLng(42.333044, -82.989151));
        latlngs.add(new LatLng(42.341673, -82.955505));
        latlngs.add(new LatLng(42.352330, -82.924263));
        latlngs.add(new LatLng(42.337613, -82.913620));
        latlngs.add(new LatLng(42.335582, -82.896454));
        latlngs.add(new LatLng(42.312737, -82.895767));
        latlngs.add(new LatLng(42.276422, -82.898857));
        latlngs.add(new LatLng(42.274643, -82.904693));
        latlngs.add(new LatLng(42.242373, -82.906753));
        latlngs.add(new LatLng(42.248981, -82.959282));
        latlngs.add(new LatLng(42.234747, -82.990181));
        latlngs.add(new LatLng(42.252793, -83.036873));
        latlngs.add(new LatLng(42.254826, -83.073608));
        latlngs.add(new LatLng(42.246694, -83.077728));
        latlngs.add(new LatLng(42.255842, -83.114807));

        //setup a list for storing lines of polygon
        List<LineofPolygon> lines = new ArrayList();

        for (int i = 1;i<latlngs.size();i++){
            lines.add(new LineofPolygon(latlngs.get(i-1),latlngs.get(i)));
        }
        //polygon contain function testing
        poly = new Polygon_Contain(lines);

        //draw the area of Windsor and highlight it
        PolygonOptions polygons = new PolygonOptions()
            .strokeColor(Color.RED)
            .strokeWidth(2)
            .fillColor(Color.HSVToColor(50, new float[]{210, 100, 100}));

        for (LatLng l : latlngs){
            polygons.add(l);
        }

        mMap.addPolygon(polygons);

        //testing for highlight facilities in Windsor
        LatLng l1 = new LatLng(42.3118656219999,-83.0334707361);
        LatLng l2 = new LatLng(42.3068574909,-82.9869316877999);
        LatLng l3 = new LatLng(42.2531537838999,-83.0239833768);
        LatLng l4 = new LatLng(42.2821369613999,-83.0156976205999);
        LatLng l5 = new LatLng(42.2999231237999,-83.0628378768);
        LatLng l6 = new LatLng(42.3215477939,-82.9386634266);
        LatLng l7 = new LatLng(42.2692392288999,-82.9662277009);

        mark1 = mMap.addMarker(new MarkerOptions().position(l1));
        mark2 = mMap.addMarker(new MarkerOptions().position(l2));
        mark3 = mMap.addMarker(new MarkerOptions().position(l3));
        mark4 = mMap.addMarker(new MarkerOptions().position(l4));
        mark5 = mMap.addMarker(new MarkerOptions().position(l5));
        mark6 = mMap.addMarker(new MarkerOptions().position(l6));
        mark7 = mMap.addMarker(new MarkerOptions().position(l7));

    }

    /**
     * The following contexts are for GPS feature development
     */

    //Enables the My Location layer if the fine location permission has been granted.
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        else if (mMap != null)
        {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     *Need to be modified for next step
     */
    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION))
        {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        }
        else
        {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /**
     *The following contexts are for Adding marker and defined circles
     */
    private class DraggableCircle {

        private final Marker centerMarker;

        private final Marker radiusMarker;

        private final Circle circle;

        private double radius;

        public DraggableCircle(LatLng center, double radius) {
            this.radius = radius;
            centerMarker = mMap.addMarker(new MarkerOptions()
                    .position(center)
                    .draggable(true));

            radiusMarker = mMap.addMarker(new MarkerOptions()
                    .position(toRadiusLatLng(center, radius))
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE)));

            circle = mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(radius)
                    .strokeWidth(5)//modified unsure
                    .strokeColor(mStrokeColor)
                    .fillColor(mFillColor));
        }

        public DraggableCircle(LatLng center, LatLng radiusLatLng) {

            this.radius = toRadiusMeters(center, radiusLatLng);

            centerMarker = mMap.addMarker(new MarkerOptions()
                    .position(center)
                    .draggable(true));

            radiusMarker = mMap.addMarker(new MarkerOptions()
                    .position(radiusLatLng)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE)));

            circle = mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(radius)
                    .strokeWidth(5)
                    .strokeColor(mStrokeColor)
                    .fillColor(mFillColor));
        }

        public boolean onMarkerMoved(Marker marker) {
            if (marker.equals(centerMarker)) {
                circle.setCenter(marker.getPosition());
                radiusMarker.setPosition(toRadiusLatLng(marker.getPosition(), radius));
                return true;
            }
            if (marker.equals(radiusMarker)) {
                radius = toRadiusMeters(centerMarker.getPosition(), radiusMarker.getPosition());
                circle.setRadius(radius);
                return true;
            }
            return false;
        }

        //modified circle attributes
//        public void onStyleChange() {
//            circle.setStrokeWidth(mWidthBar.getProgress());
//            circle.setFillColor(mFillColor);
//            circle.setStrokeColor(mStrokeColor);
//        }

    }

    /** Generate LatLng of radius marker */
    private static LatLng toRadiusLatLng(LatLng center, double radius) {
        double radiusAngle = Math.toDegrees(radius / RADIUS_OF_EARTH_METERS) /
                Math.cos(Math.toRadians(center.latitude));
        return new LatLng(center.latitude, center.longitude + radiusAngle);
    }

    //calculate the distance between center and radius marker
    private static double toRadiusMeters(LatLng center, LatLng radius) {
        float[] result = new float[1];
        Location.distanceBetween(center.latitude, center.longitude,
                radius.latitude, radius.longitude, result);
        return result[0];
    }

    /*override methods for interfaces*/

    //handle drag action
    @Override
    public void onMarkerDragStart(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        onMarkerMoved(marker);
    }

    private void onMarkerMoved(Marker marker) {
        for (DraggableCircle draggableCircle : mCircles) {
            if (draggableCircle.onMarkerMoved(marker)) {
                break;
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {

//        if (poly.contains(point))
//        {
            // create new circle and marker
            DraggableCircle circle = new DraggableCircle(point, DEFAULT_RADIUS);

            //avoid the situation existing two circles on the map at the same time
            for (DraggableCircle draggableCircle : mCircles) {
                draggableCircle.centerMarker.remove();
                draggableCircle.circle.remove();
                draggableCircle.radiusMarker.remove();
            }

            //clear up the array list
            mCircles.clear();
            mCircles.add(circle);
            //animated marker added
            bouncingTest(circle.centerMarker);
//        }
//        else
//        {
//            Toast.makeText(this, "This point is out of Windsor", Toast.LENGTH_SHORT).show();
//        }
    }

    //test for animation marker
    public void bouncingTest(final Marker marker){

        //Make the marker bounce
        final Handler handler = new Handler();
        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;

        Projection proj = mMap.getProjection();
        final LatLng markerLatLng = marker.getPosition();
        Point startPoint = proj.toScreenLocation(markerLatLng);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0)
                {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

//    public void onToggleClicked(View view) {
//
//            if (((ToggleButton) view).isChecked()) {
//                mark1.setVisible(true);
//                mark2.setVisible(true);
//                mark3.setVisible(true);
//                mark4.setVisible(true);
//                mark5.setVisible(true);
//                mark6.setVisible(true);
//                mark7.setVisible(true);
//                // handle toggle on
//            } else {
//                mark1.setVisible(false);
//                mark2.setVisible(false);
//                mark3.setVisible(false);
//                mark4.setVisible(false);
//                mark5.setVisible(false);
//                mark6.setVisible(false);
//                mark7.setVisible(false);
//            }
//
//    }





}
