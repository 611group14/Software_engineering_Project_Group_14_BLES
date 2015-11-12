package group_14.software_engineering_project_group_14_bles;

import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
//==============================================================
//Animated add marker
//==============================================================
public class Marker_bouncing {

    public static void Bouncing(final Marker marker,GoogleMap mMap){
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
}
