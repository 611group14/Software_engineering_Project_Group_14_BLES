package group_14.software_engineering_project_group_14_bles;

/**
 * Created by Administrator on 2015/11/20.
 */
public class Location_ini
{
    public static abstract class NewCoordinateInfo
    {
        public static final String LOCATION_ID = "location_id";
        public static final String LOCATION_ADDRESS = "location_address";
        public static final String LOCATION_X = "location_x";
        public static final String LOCATION_Y = "location_y";
        public static final String LOCATION_OTHERINFO = "location_otherinfo";
        public static final String LOCATION_TYPE = "location_type";
        public static final String TABLE_NAME = "location_coordinates";
    }

    public static abstract class NewUserInfo
    {
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "user_name";
        public static final String USER_PASS = "user_pass";
        public static final String User_TYPE = "user_type";
        public static final String TABLE_NAME = "user_info";
    }

    public  static abstract class NewRecordInfo
    {
        public static final String RECORD_ID = "record_id";
        public static final String RECORD_USER_ID = "record_user_id";
        public static final String RECORD_NAME = "record_name";
        public static final String RECORD_X = "record_x";
        public static final String RECORD_Y = "record_y";
        public static final String RECORD_TYPE = "record_type";
        public static final String RECORD_TIME = "record_time";
        public static final String RECORD_RADIUS = "record_radius";
        public static final String RECORD_SCORE = "record_score";
        public static final String TABLE_NAME = "record_info";
    }



}
