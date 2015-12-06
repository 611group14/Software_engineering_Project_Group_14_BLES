package group_14.software_engineering_project_group_14_bles.evaluation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import group_14.software_engineering_project_group_14_bles.DataOperation;
import group_14.software_engineering_project_group_14_bles.KeysOfExtra;
import group_14.software_engineering_project_group_14_bles.MyApplication;
import group_14.software_engineering_project_group_14_bles.R;

/**
 * Note: a portion of the code forming this class come from Google Maps API
 * tutorial project. Link: https://github.com/googlemaps/android-samples
 * Using them only for studying purpose.
 */
public class EvalRecordPickerActivity extends AppCompatActivity {

    private EvalRecordListFragment mList;
    private RecordPickerAdapter mAdapter;

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_record_picker);

        this.currentUser = getIntent().getParcelableExtra(KeysOfExtra.EVAL_DETAILS_CURRENT_USER);

        this.populateList();
        mAdapter = new RecordPickerAdapter(this, LIST_LOCATIONS);

        mList = (EvalRecordListFragment) getFragmentManager()
                .findFragmentById(R.id.eval_record_list);
        mList.setListAdapter(mAdapter);

        // Clean up maps.
        AbsListView lv = mList.getListView();
        lv.setRecyclerListener(mRecycleListener);
    }

    /**
     * Set adapter
     */
    private class RecordPickerAdapter extends ArrayAdapter<EvalRecord> {

        private final HashSet<MapView> mMaps = new HashSet<MapView>();

        public RecordPickerAdapter(Context context, List<EvalRecord> locations) {
            super(context, R.layout.eval_record_picker_row, R.id.eval_record_row_text, locations);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
                row = getLayoutInflater().inflate(R.layout.eval_record_picker_row, null);

                holder = new ViewHolder();
                row.findViewById(R.id.eval_record_row_map).setClickable(false);
                holder.mapView = (MapView) row.findViewById(R.id.eval_record_row_map);
                holder.title = (TextView) row.findViewById(R.id.eval_record_row_text);

                row.setTag(holder);
                holder.initializeMapView();
                mMaps.add(holder.mapView);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            EvalRecord item = getItem(position);
            holder.mapView.setTag(item);

            if (holder.map != null) {
                setMapLocation(holder.map, item);
            }

            holder.title.setText(item.getName());

            return row;
        }
    }

    /**
     * Set map location.
     * @param map
     * @param data
     */
    private static void setMapLocation(GoogleMap map, EvalRecord data) {
        // Add a marker for this item and set the camera
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.getPoint(), 13f));
        map.addMarker(new MarkerOptions().position(data.getPoint()));

        // Set the map type back to normal.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    /**
     *
     */
    class ViewHolder implements OnMapReadyCallback {

        MapView mapView;
        TextView title;
        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(getApplicationContext());
            map = googleMap;

            UiSettings settings = map.getUiSettings();
            settings.setMapToolbarEnabled(false);

            EvalRecord data = (EvalRecord) mapView.getTag();
            if (data != null) {
                setMapLocation(map, data);
            }
        }

        /**
         * Initialises the MapView by calling its lifecycle methods.
         */
        public void initializeMapView() {
            if (mapView != null) {
                // Initialise the MapView
                mapView.onCreate(null);
                // Set the map ready callback to receive the GoogleMap object
                mapView.getMapAsync(this);
            }
        }

    }


    private AbsListView.RecyclerListener mRecycleListener = new AbsListView.RecyclerListener() {

        @Override
        public void onMovedToScrapHeap(View view) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder != null && holder.map != null) {
                // Clear the map and free up resources by changing the map type to none
                holder.map.clear();
                holder.map.setMapType(GoogleMap.MAP_TYPE_NONE);
            }

        }
    };


    /**
     * Below codes are for experimental purpose only.
     */
    private static List<EvalRecord> LIST_LOCATIONS = new ArrayList<EvalRecord>();
    private void populateList() {
        LIST_LOCATIONS.clear();

//        LIST_LOCATIONS.add(new EvalRecord("2750 Ouellette Ave", new Date(), new LatLng(42.2821369613999, -83.0156976205999)));
//        LIST_LOCATIONS.add(new EvalRecord("1380 Matthew Brady Blvd", new Date(), new LatLng(42.3215477939, -82.9386634266)));
//        LIST_LOCATIONS.add(new EvalRecord("Windsor Airport", new Date(), new LatLng(42.2692392288999, -82.9662277009)));
//        LIST_LOCATIONS.add(new EvalRecord("400 Wyandotte St E", new Date(), new LatLng(42.316671, -83.03119)));

        DataOperation dataOperation = new DataOperation();

        // Get user.
        MyApplication myApp = (MyApplication) getApplication();
        String user = myApp.getValue();

        Context context = this;
        String userId = dataOperation.getUserInfo(context, user).get(0);
        ArrayList<ArrayList<String>> allRecords = dataOperation.getRecordInfo(context, userId);

        for (int i = 0; i < allRecords.size() - 1; i++) {
            LatLng point = new LatLng(Double.parseDouble(allRecords.get(i).get(3)),
                    Double.parseDouble(allRecords.get(i).get(4)));
            LIST_LOCATIONS.add(new EvalRecord(allRecords.get(i).get(2), point));
        }
    }

}
