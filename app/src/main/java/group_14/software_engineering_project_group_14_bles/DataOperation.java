package group_14.software_engineering_project_group_14_bles;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class DataOperation
{
    public ArrayList<String> getFacilities(Context context,String facilityType)
    {
        ArrayList<String> facilityBackList = new ArrayList<String>();
        UserDbHelper userDbHelper;
        userDbHelper = new UserDbHelper(context);
        int i =0;
        String[] columns = {Location_ini.NewCoordinateInfo.LOCATION_ID, Location_ini.NewCoordinateInfo.LOCATION_ADDRESS,
                Location_ini.NewCoordinateInfo.LOCATION_X, Location_ini.NewCoordinateInfo.LOCATION_Y,
                Location_ini.NewCoordinateInfo.LOCATION_OTHERINFO, Location_ini.NewCoordinateInfo.LOCATION_TYPE};
        Cursor CR = userDbHelper.getInformations(userDbHelper,Location_ini.NewCoordinateInfo.TABLE_NAME,columns,"location_type=?",new String[]{facilityType});
        if(CR.moveToFirst())
        {
            do
            {

                facilityBackList.add(CR.getString(0));
                //double coordinate_x = Double.parseDouble(CR.getString(1));
                //double coordinate_y = Double.parseDouble(CR.getString(2));
                i++;
            }while(CR.moveToNext());
        }

        return facilityBackList;
    }

    public ArrayList<ArrayList<String>> getFacilityInfo(Context context,ArrayList<String> backList)
    {
        ArrayList<ArrayList<String>> facilityInfoList = new ArrayList<ArrayList<String>>();
        facilityInfoList.add(new ArrayList<String>());
        UserDbHelper userDbHelper;
        userDbHelper = new UserDbHelper(context);

        String[] columns = {Location_ini.NewCoordinateInfo.LOCATION_ID,Location_ini.NewCoordinateInfo.LOCATION_ADDRESS,
                Location_ini.NewCoordinateInfo.LOCATION_X, Location_ini.NewCoordinateInfo.LOCATION_Y,
                Location_ini.NewCoordinateInfo.LOCATION_OTHERINFO, Location_ini.NewCoordinateInfo.LOCATION_TYPE};
        for(int j = 0; j < backList.size(); j++)
        {
            int i = 0;
            Cursor CR = userDbHelper.getInformations(userDbHelper,Location_ini.NewCoordinateInfo.TABLE_NAME,columns,"location_id=?",new String[]{backList.get(j)});

            if(CR.moveToFirst())
            {
                do
                {
                    facilityInfoList.get(j).add(CR.getString(0));
                    facilityInfoList.get(j).add(CR.getString(1));
                    facilityInfoList.get(j).add(CR.getString(2));
                    facilityInfoList.get(j).add(CR.getString(3));
                    facilityInfoList.get(j).add(CR.getString(4));
                    facilityInfoList.get(j).add(CR.getString(5));
                    //double coordinate_x = Double.parseDouble(CR.getString(1));
                    //double coordinate_y = Double.parseDouble(CR.getString(2));
                    i++;
                }while(CR.moveToNext());
            }
            facilityInfoList.add(new ArrayList<String>());

        }

        return facilityInfoList;

    }

    public boolean loginAuthentication(Context context,String username,String userpass)
    {
        boolean loginstatus = false;
        String[] columns = {Location_ini.NewUserInfo.USER_ID,Location_ini.NewUserInfo.USER_NAME,
        Location_ini.NewUserInfo.USER_PASS,Location_ini.NewUserInfo.User_TYPE};
        UserDbHelper userDbHelper = new UserDbHelper(context);
        Cursor CR = userDbHelper.getInformations(userDbHelper,Location_ini.NewUserInfo.TABLE_NAME,columns,null,null);
        if(CR.moveToFirst())
        {
            do
            {
                if(username.equals(CR.getString(1))&& (userpass.equals(CR.getString(2))))
                {
                    loginstatus = true;
                }

            }while(CR.moveToNext());
        }

        return loginstatus;
    }

    public ArrayList<String> getUserInfo(Context context,String backID)
    {
        ArrayList<String> userInfoList = new ArrayList<String>();

        UserDbHelper userDbHelper;
        userDbHelper = new UserDbHelper(context);

        String[] columns = {Location_ini.NewUserInfo.USER_ID,Location_ini.NewUserInfo.USER_NAME,
                Location_ini.NewUserInfo.USER_PASS,Location_ini.NewUserInfo.User_TYPE};

        int i = 0;
        Cursor CR = userDbHelper.getInformations(userDbHelper,Location_ini.NewUserInfo.TABLE_NAME,columns,"user_id=?",new String[]{backID});

        if(CR.moveToFirst())
        {
            do
            {
                userInfoList.add(CR.getString(0));
                userInfoList.add(CR.getString(1));
                userInfoList.add(CR.getString(2));
                //double coordinate_x = Double.parseDouble(CR.getString(1));
                //double coordinate_y = Double.parseDouble(CR.getString(2));
                i++;
                }while(CR.moveToNext());
            }



        return userInfoList;

    }

    public ArrayList<String> getRecordInfo(Context context,String backID)
    {
        ArrayList<String> recordInfoList = new ArrayList<String>();
        UserDbHelper userDbHelper;
        userDbHelper = new UserDbHelper(context);


        String[] columns = {Location_ini.NewRecordInfo.RECORD_ID,Location_ini.NewRecordInfo.RECORD_USER_ID,
                Location_ini.NewRecordInfo.RECORD_NAME, Location_ini.NewRecordInfo.RECORD_X,
                Location_ini.NewRecordInfo.RECORD_Y, Location_ini.NewRecordInfo.RECORD_TYPE,
                Location_ini.NewRecordInfo.RECORD_TIME,Location_ini.NewRecordInfo.RECORD_RADIUS,
                Location_ini.NewRecordInfo.RECORD_SCORE};

        int i = 0;
        Cursor CR = userDbHelper.getInformations(userDbHelper,Location_ini.NewRecordInfo.TABLE_NAME,columns,"record_user_id=?",new String[]{backID});

        if(CR.moveToFirst())
        {
            do
            {
                recordInfoList.add(CR.getString(0));
                recordInfoList.add(CR.getString(1));
                recordInfoList.add(CR.getString(2));
                recordInfoList.add(CR.getString(3));
                recordInfoList.add(CR.getString(4));
                recordInfoList.add(CR.getString(5));
                recordInfoList.add(CR.getString(6));
                recordInfoList.add(CR.getString(7));
                recordInfoList.add(CR.getString(8));
                recordInfoList.add(CR.getString(9));
                //double coordinate_x = Double.parseDouble(CR.getString(1));
                //double coordinate_y = Double.parseDouble(CR.getString(2));
                i++;
            }while(CR.moveToNext());
        }

        return recordInfoList;

    }




}
