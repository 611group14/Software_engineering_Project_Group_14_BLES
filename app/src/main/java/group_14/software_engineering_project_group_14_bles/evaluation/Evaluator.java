package group_14.software_engineering_project_group_14_bles.evaluation;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;

import group_14.software_engineering_project_group_14_bles.Facility;
import group_14.software_engineering_project_group_14_bles.FacilityCategory;
import group_14.software_engineering_project_group_14_bles.FacilityDataAdapter;

/**
 * Created by Jerry on 11/25/2015.
 */
public class Evaluator {

    private final double MAX_RADIUS = 2500;

    private Context context;
    private LatLng center;
    private double radius = 1500;

    /**
     * This constructor is used to instantiate for testing purpose only.
     */
    public Evaluator() {
        // Do nothing.
    }

    public Evaluator(Context context, LatLng centerPoint, double radius) {
        this.context = context;
        this.center = centerPoint;
        this.radius = radius;
    }

    /**
     * Get distance between 2 points. Return value in meter.
     *
     * @param start
     * @param end
     * @return
     */
    public double getDistance(LatLng start, LatLng end) {
        float[] results = new float[3];
        Location.distanceBetween(start.latitude, start.longitude,
                end.latitude, end.longitude, results);
        return results[0];
    }

    /**
     * Evaluate a location by comparing it to all categories of facilities
     * existing in the map database. The evaluation is primarily based on the
     * geographical distance of the location and facilities.
     *
     * @param category
     * @return
     */
    public ScoredItem evaluateByCategory(String category) {

        ScoredItem result = new ScoredItem(0.0, category);
        FacilityDataAdapter adapter = new FacilityDataAdapter(context, category);
        ArrayList<Facility> facilities = adapter.getAllFacilities();

        if (category.equalsIgnoreCase("Not Implemented!!!")) {
            // TODO
        } else {

            double totalScore = 0.0;

            for (Facility facility : facilities) {
                double dist = this.getDistance(center, facility.getLatLng());

                if (dist > this.radius) {
                    continue;
                }

                double score = this.calculateScore(
                        this.getInitScoreByCategory(category), dist);

                totalScore += score;
                result.addFacility(facility);
            }
            result.setScore(totalScore);
        }
        return result;
    }

    /**
     * Calculate scores by its initial value and location distance.
     * @param initScore
     * @param distance
     * @return
     */
    private double calculateScore(double initScore, double distance) {
        DecimalFormat df = new DecimalFormat("#.##");
        double score = initScore - (distance / MAX_RADIUS) * initScore;
        String str = df.format(score);

        return Double.valueOf(str);
    }

    /**
     * Assign each category of facilities an initial value. The initial values are
     * all based on theoretical analysis. The theoretical background is covered by
     * project report documentation.
     *
     * @param category
     * @return
     */
    private double getInitScoreByCategory(String category) {

        double score = 0.0;

        if (category.equalsIgnoreCase(FacilityCategory.FIRE_STATION)) {
            score = 15;
        } else if (category.equalsIgnoreCase(FacilityCategory.SCHOOL)) {
            score = 1;
        } else if (category.equalsIgnoreCase(FacilityCategory.VOTING_STATION)) {
            score = 1;
        } else if (category.equalsIgnoreCase(FacilityCategory.AIRPORT)) {
            score = 15;
        } else if (category.equalsIgnoreCase(FacilityCategory.COMMUNITY_CENTER)) {
            score = 5;
        } else if (category.equalsIgnoreCase(FacilityCategory.HOSPITAL)) {
            score = 5;
        } else if (category.equalsIgnoreCase(FacilityCategory.PARK)) {
            score = 1;
        } else if (category.equalsIgnoreCase(FacilityCategory.PARKING_LOTS_GARAGES)) {
            score = 10;
        } else if (category.equalsIgnoreCase(FacilityCategory.POLICE_STATION)) {
            score = 15;
        } else if (category.equalsIgnoreCase(FacilityCategory.RAILWAY_STAION)) {
            score = 15;
        } else if (category.equalsIgnoreCase(FacilityCategory.TUNNEL_BRIDGE)) {
            score = 15;
        } else {
            // TODO
        }

        return score;
    }

}
