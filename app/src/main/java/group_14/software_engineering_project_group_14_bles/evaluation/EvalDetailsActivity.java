package group_14.software_engineering_project_group_14_bles.evaluation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import group_14.software_engineering_project_group_14_bles.KeysOfExtra;
import group_14.software_engineering_project_group_14_bles.R;
import group_14.software_engineering_project_group_14_bles.dataparsing.XMLDataParser;

public class EvalDetailsActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private final float EVAL_MAP_DEFAULT_ZOOM = 14;

    /**
     * Evaluation map fragment.
     */
    private GoogleMap evalMap;
    private UiSettings uiSettings;
    private LatLng evalPoint;
    private Marker evalPointMarker;

    /**
     * Fields and class for valuation details.
     */
    private ScoredItemListFragment mList;
    private ScoredItemListAdapter itemListAdapter;

    /**
     *
     */
    private class ScoredItemListAdapter extends ArrayAdapter<ScoredItem> {

        public ScoredItemListAdapter(Context context, List<ScoredItem> itemList) {
            super(context, R.layout.scored_item_row, R.id.scored_item_row_text, itemList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;
            ScoredItem item = getItem(position);

            if (row == null) {
                row = getLayoutInflater().inflate(R.layout.scored_item_row, null);
                holder = new ViewHolder();
                holder.textView = (TextView) row.findViewById(R.id.scored_item_row_text);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            holder.formatText(item);
            return row;
        }
    }

    /**
     *
     */
    private class ViewHolder {
        public TextView textView;

        public void formatText(ScoredItem item) {
            String s = "Facility Category: " + item.getCategory()
                    + "\nScore: " + String.valueOf(item.getScore());
            this.textView.setText(s);
            this.textView.setTextSize(18f);
            this.textView.setTextColor(Color.BLUE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.eval_details_map);
        mapFragment.getMapAsync(this);

        // Receive user selected point.
        this.initDataFromMainMap();


        // Handling evaluation details list.
        this.populateList(); // TBD
        itemListAdapter = new ScoredItemListAdapter(this, this.scoreList); //TBD

        mList = (ScoredItemListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.eval_details_score_list);
        mList.setListAdapter(itemListAdapter);

    }

    /**
     * Init data that are delivered by main map activity.
     */
    private void initDataFromMainMap() {
        Intent intent = getIntent();
        this.evalPoint = intent.getParcelableExtra(KeysOfExtra.MAIN_MAP_LAST_EVAL_POINT);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        evalMap = map;

        uiSettings = evalMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);

        evalMap.moveCamera(CameraUpdateFactory.newLatLngZoom(evalPoint, EVAL_MAP_DEFAULT_ZOOM));

        // Get user evaluation point address.
//        this.getPointAddress(this.evalPoint);
        this.initEvalPointMarker();
        this.updateEvalSummary(this.evalPoint.toString());

    }


    /**
     * This should be called on all entry points that call methods on the Google Maps API.
     */
    private boolean checkReady() {
        if (evalMap == null) {
            Toast.makeText(this, R.string.eval_details_msg_map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Set marker to the evaluation point.
     */
    private void initEvalPointMarker() {
        this.evalPointMarker = evalMap.addMarker(new MarkerOptions()
                .position(this.evalPoint)
                .draggable(false)
                .title(getString(R.string.eval_point_marker_default_title))
                .snippet(getString(R.string.eval_details_info_marker_snippet_see_below)));
        this.evalPointMarker.showInfoWindow();
    }

    /**
     * Update evaluation summary to display.
     * @param text
     */
    private void updateEvalSummary(String text) {
        TextView view = (TextView)findViewById(R.id.eval_location_summary);
        view.setText(text);
    }

    // TODO - To be modified or deleted
    private void getPointAddress(LatLng point) {
        Geocoder gCoder = new Geocoder(this);
        List<Address> addresses = null;
        try {
            addresses = gCoder.getFromLocation(point.latitude, point.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            Toast.makeText(this, "country: " + addresses.get(0).getCountryName(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "no location found", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Go to evaluation record picker page.
     */
    public void pickUpAnotherEvalRecord(View view) {
        Intent intent = new Intent(this, EvalRecordPickerActivity.class);
        //intent.putExtra(KEY_LAST_EVAL_POINT, this.lastEvalPoint);
        startActivity(intent);
    }

    /**
     *
     */
    public void saveCurrentEvalRecord(View view) {

        ArrayList<ArrayList<String>> facility =
                XMLDataParser.parseFacilityData(this, "community_centres.xml");

        //String message = String.valueOf(facility.size());
        String message = "";

        for (ArrayList<String> list : facility) {
            for (String s : list) {
                message += s + " ";
            }

            message += "\n\n";
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }


    // Experiment Code!!
    private static List<ScoredItem> scoreList = new ArrayList<ScoredItem>();
    private void populateList() {
        scoreList.clear();
        scoreList.add(new ScoredItem(33.5, "Fire Station"));
        scoreList.add(new ScoredItem(87.2, "Hospital"));
        scoreList.add(new ScoredItem(120.7, "Shopping Center"));
        scoreList.add(new ScoredItem(20.7, "Library"));
        scoreList.add(new ScoredItem(12.7, "Parking Lot"));
        scoreList.add(new ScoredItem(12, "Bus Stop"));
        scoreList.add(new ScoredItem(920.7, "Community"));
        scoreList.add(new ScoredItem(0.7, "Playing Area"));
    }

    private void saveEvalRecord() {
        System.currentTimeMillis();
        UUID.randomUUID();
    }

}
