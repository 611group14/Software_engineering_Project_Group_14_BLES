package group_14.software_engineering_project_group_14_bles;

import java.util.ArrayList;

/**
 * Created by Jerry on 11/24/2015.
 */
public class FacilityCategory {

    /**
     * All category names.
     */
    public final static String FIRE_STATION = "Fire Station";
    public final static String SCHOOL = "School";
    public final static String VOTING_STATION = "Voting Station";
    public final static String AIRPORT = "Airport";
    public final static String COMMUNITY_CENTER = "Community Center";
    public final static String HOSPITAL = "Hospital";
    public final static String PARK = "Park";
    public final static String PARKING_LOTS_GARAGES = "Parking Lots Garages";
    public final static String POLICE_STATION = "Police Station";
    public final static String RAILWAY_STAION = "Railway Station";
    public final static String TUNNEL_BRIDGE = "Tunnel Bridge";


    /**
     * All XML file names for category.
     */
    public final static String XML_FIRE_STATION = "fire_stations.xml";
    public final static String XML_SCHOOL = "schools.xml";
    public final static String XML_VOTING_STATION = "voting_stations.xml";
    public final static String XML_AIRPORT = "airport.xml";
    public final static String XML_COMMUNITY_CENTER = "community_centres.xml";
    public final static String XML_HOSPITAL = "hospitals.xml";
    public final static String XML_PARK = "park.xml";
    public final static String XML_PARKING_LOTS_GARAGES = "parking_lots_garages.xml";
    public final static String XML_POLICE_STATION = "police_stations.xml";
    public final static String XML_RAILWAY_STAION = "railway_station.xml";
    public final static String XML_TUNNEL_BRIDGE = "tunnel_bridge.xml";


    private static ArrayList<String> allCategory = null;
    private static ArrayList<String> allXmlNames = null;

    /**
     *
     * @return
     */
    public static ArrayList<String> getAllCategory() {

        if (allCategory != null) {
            return allCategory;
        }

        allCategory = new ArrayList<>();

        // NOTE: UPDATE THIS LIST WHEN MORE CATEGORIES ADDED IN!!!
        allCategory.add(FIRE_STATION);
        allCategory.add(SCHOOL);
        allCategory.add(VOTING_STATION);
        allCategory.add(AIRPORT);
        allCategory.add(COMMUNITY_CENTER);
        allCategory.add(HOSPITAL);
        allCategory.add(PARK);
        allCategory.add(PARKING_LOTS_GARAGES);
        allCategory.add(POLICE_STATION);
        allCategory.add(RAILWAY_STAION);
        allCategory.add(TUNNEL_BRIDGE);
        return allCategory;
    }

    /**
     *
     */
    public static ArrayList<String> getAllXmlFileNames() {

        if (allXmlNames != null) {
            return allXmlNames;
        }

        allXmlNames = new ArrayList<>();

        // NOTE: UPDATE THIS LIST WHEN MORE CATEGORIES ADDED IN!!!
        allXmlNames.add(XML_FIRE_STATION);
        allXmlNames.add(XML_SCHOOL);
        allXmlNames.add(XML_VOTING_STATION);
        allXmlNames.add(XML_AIRPORT);
        allXmlNames.add(XML_COMMUNITY_CENTER);
        allXmlNames.add(XML_HOSPITAL);
        allXmlNames.add(XML_PARK);
        allXmlNames.add(XML_PARKING_LOTS_GARAGES);
        allXmlNames.add(XML_POLICE_STATION);
        allXmlNames.add(XML_RAILWAY_STAION);
        allXmlNames.add(XML_TUNNEL_BRIDGE);
        return allXmlNames;
    }
}
