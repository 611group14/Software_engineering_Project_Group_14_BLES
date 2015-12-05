package group_14.software_engineering_project_group_14_bles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2015/11/20.
 */
public class UserDbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "LOCATIONINFO.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY_FACILITY =
            "CREATE TABLE "+ Location_ini.NewCoordinateInfo.TABLE_NAME+"("+ Location_ini.NewCoordinateInfo.LOCATION_ID+" TEXT,"+
                    Location_ini.NewCoordinateInfo.LOCATION_ADDRESS+" TEXT,"+ Location_ini.NewCoordinateInfo.LOCATION_X+" TEXT,"+
                    Location_ini.NewCoordinateInfo.LOCATION_Y+" TEXT,"+ Location_ini.NewCoordinateInfo.LOCATION_OTHERINFO+" TEXT,"+
                    Location_ini.NewCoordinateInfo.LOCATION_TYPE+" TEXT);";

    private static final  String CREATE_QUERY_USER =
            "CREATE TABLE "+ Location_ini.NewUserInfo.TABLE_NAME+"("+ Location_ini.NewUserInfo.USER_ID+" TEXT,"+
                    Location_ini.NewUserInfo.USER_NAME+" TEXT,"+ Location_ini.NewUserInfo.USER_PASS+" TEXT,"+
                    Location_ini.NewUserInfo.User_TYPE+" TEXT);";

    private static final  String CREATE_QUERY_RECORD =
            "CREATE TABLE "+ Location_ini.NewRecordInfo.TABLE_NAME+"("+ Location_ini.NewRecordInfo.RECORD_ID+" TEXT,"+
                    Location_ini.NewRecordInfo.RECORD_USER_ID+" TEXT,"+Location_ini.NewRecordInfo.RECORD_NAME+" TEXT,"+
                    Location_ini.NewRecordInfo.RECORD_X+" TEXT,"+ Location_ini.NewRecordInfo.RECORD_Y+" TEXT,"+
                    Location_ini.NewRecordInfo.RECORD_TIME+" TEXT,"+ Location_ini.NewRecordInfo.RECORD_TYPE+" TEXT,"+
                    Location_ini.NewRecordInfo.RECORD_RADIUS+" TEXT,"+ Location_ini.NewRecordInfo.RECORD_SCORE+" TEXT);";

    public UserDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATION","Database created.. /opened..");
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_QUERY_FACILITY);
        Log.e("DATABASE OPERATION", "Facility Table created..");
        db.execSQL(CREATE_QUERY_USER);
        Log.e("DATABASE OPERATION", "User Table created..");
        db.execSQL(CREATE_QUERY_RECORD);
        Log.e("DATABASE OPERATION","Record Table created..");
    }

    public void addFacilityInformations(UserDbHelper userDbHelper,String location_id,String location_address,String location_x,String location_y,String location_otherinfo,String location_type)
    {
        SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Location_ini.NewCoordinateInfo.LOCATION_ID, location_id);
        contentValues.put(Location_ini.NewCoordinateInfo.LOCATION_ADDRESS, location_address);
        contentValues.put(Location_ini.NewCoordinateInfo.LOCATION_X, location_x);
        contentValues.put(Location_ini.NewCoordinateInfo.LOCATION_Y, location_y);
        contentValues.put(Location_ini.NewCoordinateInfo.LOCATION_OTHERINFO, location_otherinfo);
        contentValues.put(Location_ini.NewCoordinateInfo.LOCATION_TYPE, location_type);
        sqLiteDatabase.insert(Location_ini.NewCoordinateInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATION", "One row inserted.In Facility Table");
    }

    public void addUserInformations(UserDbHelper userDbHelper,String user_id,String user_name,String user_pass,String user_type)
    {
        SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Location_ini.NewUserInfo.USER_ID,user_id);
        contentValues.put(Location_ini.NewUserInfo.USER_NAME,user_name);
        contentValues.put(Location_ini.NewUserInfo.USER_PASS,user_pass);
        contentValues.put(Location_ini.NewUserInfo.User_TYPE,user_type);
        sqLiteDatabase.insert(Location_ini.NewUserInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATION", "One row inserted.In User Table");
    }

    public void addRecordInformations(UserDbHelper userDbHelper,String record_id,String record_user_id,String record_name,String record_x,String record_y,String record_time,String record_type,String record_radius,String record_score)
    {
        SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Location_ini.NewRecordInfo.RECORD_ID,record_id);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_USER_ID,record_user_id);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_NAME,record_name);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_X,record_x);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_Y,record_y);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_TIME,record_time);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_TYPE,record_type);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_RADIUS,record_radius);
        contentValues.put(Location_ini.NewRecordInfo.RECORD_SCORE,record_score);
        sqLiteDatabase.insert(Location_ini.NewRecordInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATION", "One row inserted.In Record Table");
    }

    public Cursor getInformations(UserDbHelper userDbHelper,String tableName,String[] columns,String selection,String[] selectionArgs)
    {
        SQLiteDatabase sqLiteDatabase = userDbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.query(true,tableName,columns,selection,selectionArgs,null,null,null,null);
        return cursor;

    }


    public void deleteInformation(UserDbHelper userDbHelper,String tableName)
    {
        SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + tableName);
        Log.e("DATABASE OPERATION", "deleted..");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
