package group_14.software_engineering_project_group_14_bles;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.android.gms.maps.model.LatLng;

import group_14.software_engineering_project_group_14_bles.evaluation.Evaluator;
import group_14.software_engineering_project_group_14_bles.evaluation.ScoredItem;

public class ApplicationTest extends ApplicationTestCase<Application> {

    private static final LatLng Windsor = new LatLng(42.289810, -82.999313);
    private LatLng testPoint1 = new LatLng(42.3215477939, -82.9386634266);
    private LatLng testPoint2 = new LatLng(42.2821369613999, -83.0156976205999);
    private Evaluator evaluator = new Evaluator();

    public ApplicationTest() {
        super(Application.class);
    }

    /**
     * Test distance calculation - regular case
     */
    public void distanceCalculationRegular() {
        assertTrue((evaluator.getDistance(testPoint1, testPoint2)) > 0);
        assertTrue((evaluator.getDistance(testPoint2, testPoint1)) > 0);
    }
    /**
     * Test distance calculation - identical point
     */
    public void distanceCalculationIdenticalPoint() {
        assertTrue((evaluator.getDistance(testPoint1, testPoint1)) == 0);
    }
    /**
     * Test evaluation at point - PARKING_LOTS_GARAGES should have greater
     * scores than COMMUNITY_CENTER
     */
    public void parkingLotsHaveLargerScoresThanCommunity() {
        ScoredItem parking = evaluator.evaluateByCategory(FacilityCategory.PARKING_LOTS_GARAGES);
        double parkingScore = parking.getScore();
        ScoredItem community = evaluator.evaluateByCategory(FacilityCategory.PARKING_LOTS_GARAGES);
        double communityScore = community.getScore();
        assertTrue(parkingScore > communityScore);
    }

    public void executeTestSuite() {
        distanceCalculationRegular();
        distanceCalculationIdenticalPoint();
        parkingLotsHaveLargerScoresThanCommunity();
    }
}