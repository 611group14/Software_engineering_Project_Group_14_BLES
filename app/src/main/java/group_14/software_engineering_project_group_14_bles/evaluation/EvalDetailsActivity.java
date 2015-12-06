package group_14.software_engineering_project_group_14_bles.evaluation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import group_14.software_engineering_project_group_14_bles.DataOperation;
import group_14.software_engineering_project_group_14_bles.FacilityCategory;
import group_14.software_engineering_project_group_14_bles.KeysOfExtra;
import group_14.software_engineering_project_group_14_bles.MyApplication;
import group_14.software_engineering_project_group_14_bles.R;
import group_14.software_engineering_project_group_14_bles.UserDbHelper;

public class EvalDetailsActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private final float EVAL_MAP_DEFAULT_ZOOM = 14;
    private String currentUser;

    /**
     * Evaluation map fragment.
     */
    private GoogleMap evalMap;
    private UiSettings uiSettings;
    private LatLng evalPoint;
    private Marker evalPointMarker;
    // Fixed value for now!!
    private double radius = 1500;

    private LatLng test1 = new LatLng(42.3118656219999, -83.0334707361);
    private LatLng test2 = new LatLng(42.3068574909, -82.9869316877999);

    /**
     * Fields and class for valuation details.
     */
    private ScoredItemListFragment mList;
    private ScoredItemListAdapter itemListAdapter;

    /**
     *
     */
    private double evalTotalScore = 0.0;

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
            this.textView.setTextSize(17f);
            this.textView.setTextColor(Color.BLACK);
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

        // Get user.
        MyApplication myApp = (MyApplication) getApplication();
        this.currentUser = myApp.getValue();

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
        this.updateEvalSummary("Your Destination Scored:  " + String.valueOf(this.evalTotalScore));

//        map.addMarker(new MarkerOptions().position(test1));
//        map.addMarker(new MarkerOptions().position(test2));
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
//                .title(getString(R.string.eval_point_marker_default_title))
//                .snippet(getString(R.string.eval_details_info_marker_snippet_see_below)));
                .title("Your Location"));
        this.evalPointMarker.showInfoWindow();
    }

    /**
     * Update evaluation summary to display.
     * @param text
     */
    private void updateEvalSummary(String text) {
        TextView view = (TextView)findViewById(R.id.eval_location_summary);
        view.setText(text);
        view.setTextSize(21f);
        view.setTextColor(Color.BLUE);
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

        MyApplication myApp = (MyApplication) getApplication();
        if (!myApp.isLogin()) {
            Toast.makeText(this, "Please log in or register a new user.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, EvalRecordPickerActivity.class);
        intent.putExtra(KeysOfExtra.EVAL_DETAILS_CURRENT_USER, this.currentUser);
        startActivity(intent);
    }

    /**
     * Call the save record prompt dialog to display.
     */
    public void saveCurrentEvalRecord(View view) {

        final Context context = this;
        MyApplication myApp = (MyApplication) getApplication();

        if (!myApp.isLogin()) {
            Toast.makeText(context, "Please log in or register a new user.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        final UserDbHelper helper = new UserDbHelper(context);

        // get view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.save_eval_record_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set to alert dialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.save_eval_record_prompt_name_input_box);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                // Save to database.
                                saveRecordToDatabase(helper, userInput.getText().toString());
                                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    private static List<ScoredItem> scoreList = new ArrayList<>();

    private void populateList() {

        scoreList.clear();

        Evaluator evaluator = new Evaluator(this, this.evalPoint, this.radius);
        ArrayList<String> allFacilityNames = FacilityCategory.getAllCategory();


        for (String name : allFacilityNames) {
            ScoredItem item = evaluator.evaluateByCategory(name);
            scoreList.add(item);
            this.evalTotalScore += item.getScore();
        }


//        for (Facility fac : item.getFacilities()) {
//            evalMap.addMarker(new MarkerOptions().position(fac.getLatLng()));
//        }

//        scoreList.add(new ScoredItem(33.5, "Fire Station"));
//        scoreList.add(new ScoredItem(87.2, "Hospital"));
//        scoreList.add(new ScoredItem(120.7, "Shopping Center"));
//        scoreList.add(new ScoredItem(20.7, "Library"));
//        scoreList.add(new ScoredItem(12.7, "Parking Lot"));
//        scoreList.add(new ScoredItem(12, "Bus Stop"));
//        scoreList.add(new ScoredItem(920.7, "Community"));
//        scoreList.add(new ScoredItem(0.7, "Playing Area"));
    }


    /**
     * Create and save an evaluation record by calling database handler.
     */
    private void saveRecordToDatabase(UserDbHelper userDbHelper, String name) {

        String uuid = UUID.randomUUID().toString();
        String timeStamp = String.valueOf(System.currentTimeMillis());

        String x = String.valueOf(this.evalPoint.latitude);
        String y = String.valueOf(this.evalPoint.longitude);

        // Temporarily using a fixed record type.
        String type = "Restaurant";

        DataOperation dataOperation = new DataOperation();
        ArrayList<String> userInfo = dataOperation.getUserInfo(this, this.currentUser);
        String userId = userInfo.get(0);

        String recordId = "EvalRecordID_" + uuid + "_" + timeStamp;

        userDbHelper.addRecordInformations(
                userDbHelper,
                recordId,
                userId,
                name,
                x,
                y,
                timeStamp,
                type,
                String.valueOf(this.radius),
                String.valueOf(this.evalTotalScore)
        );
    }

}
