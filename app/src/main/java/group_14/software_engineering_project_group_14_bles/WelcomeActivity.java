package group_14.software_engineering_project_group_14_bles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;

import java.io.Console;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.romainpiel.titanic.library.Titanic;
import com.romainpiel.titanic.library.TitanicTextView;

import group_14.software_engineering_project_group_14_bles.dataparsing.XMLDataParser;

public class WelcomeActivity extends AppCompatActivity {

    Button ToMap;
    //Button Initialize;
    Context context = this;
    SharedPreferences prefs = null;
    int initial_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);

        // set fancy typeface
        tv.setTypeface(Typefaces.get(this, "ARCADE.TTF"));

        // start animation
        new Titanic().start(tv);

        prefs = getSharedPreferences("Running time",MODE_PRIVATE);

        if(prefs.getBoolean("firstrun", true))
        {
            Toast.makeText(getBaseContext(), "Welcome!", Toast.LENGTH_LONG).show();
            UserDbHelper userDbHelper;
            userDbHelper = new UserDbHelper(context);
            userDbHelper.deleteInformation(userDbHelper, Location_ini.NewCoordinateInfo.TABLE_NAME);

//            userDbHelper.addFacilityInformations(userDbHelper, "1", "815 Goyeau St", "42.3118656219999", "-83.0334707361", "Fire Hall #1", "FireStation");
//            userDbHelper.addFacilityInformations(userDbHelper,"2", "3121 Milloy St", "42.3068574909", "-82.9869316877999", "Fire Hall #2","FireStation");
//            userDbHelper.addFacilityInformations(userDbHelper,"3", "1905 Cabana Rd W", "42.2531537838999", "-83.0239833768", "Fire Hall #5","FireStation");
//            userDbHelper.addFacilityInformations(userDbHelper,"4", "2750 Ouellette Ave", "42.2821369613999", "-83.0156976205999", "Fire Hall #3","FireStation");
//            userDbHelper.addFacilityInformations(userDbHelper,"5", "2600 College Ave", "42.2999231237999", "-83.0628378768", "Fire Hall #4","FireStation");
//            userDbHelper.addFacilityInformations(userDbHelper,"6", "1380 Matthew Brady Blvd", "42.3215477939", "-82.9386634266", "Fire Hall #7","FireStation");
//            userDbHelper.addFacilityInformations(userDbHelper, "7", "Windsor Airport", "42.2692392288999", "-82.9662277009", "Fire Hall #6", "FireStation");
//
//            userDbHelper.addFacilityInformations(userDbHelper, "8", "1427 PRINCE RD", "42.2842887", "-83.06450715", "Windsor Regional Hospital - Western Campus", "Hospital");
//            userDbHelper.addFacilityInformations(userDbHelper, "9", "1995 LENS AVE", "42.30036155", "-82.99721348", "Windsor Regional Hospital - Metropolitan Campus", "Hospital");
//
//            userDbHelper.addFacilityInformations(userDbHelper, "10", "City Hall Square", "42.31687953", "-83.03514397", "Parking Lot 17", "ParkingLot&Garages");
//            userDbHelper.addFacilityInformations(userDbHelper, "11", "Wyandotte/Langlois", "42.31897415", "-83.0220301", "Parking Lot 6", "ParkingLot&Garages");
//
//            userDbHelper.addFacilityInformations(userDbHelper, "12", "3215 Forest Glade Dr", "42.303163", "-82.91572", "Forest Glade Community Centre", "CommunityCenter");
//            userDbHelper.addFacilityInformations(userDbHelper, "13", "5200 Matchette Rd", "42.264283", "-83.075724", "Ojibway Nature Centre", "CommunityCenter");
//
//            userDbHelper.addFacilityInformations(userDbHelper,"14", "Martinique Dr.", "42.3339351123999", "-82.9240927883", "MARTINIQUE PARK","Park");
//            userDbHelper.addFacilityInformations(userDbHelper,"15", "Morningstar Ave.", "42.324812837", "-82.9123383140999", "MORNINGSTAR PARK","Park");
//
//            userDbHelper.addFacilityInformations(userDbHelper,"16", "910 Raymo Rd", "42.3247712339", "-82.9752094348999", "Corpus Christi Catholic Middle School","School");
//            userDbHelper.addFacilityInformations(userDbHelper,"17", "1325 CALIFORNIA AVE", "42.2963396892", "-83.0607398934999", "Windsor Essex Catholic District School Board","School");


            // Get all xml file names.
            ArrayList<String> xmlFiles = FacilityCategory.getAllXmlFileNames();

            // Loop files.
            for (String name : xmlFiles) {
                // Get all facility items of one category.
                ArrayList<ArrayList<String>> facility =
                        XMLDataParser.parseFacilityData(this, name);

                // Loop rows.
                for (ArrayList<String> row : facility) {
                    // Get all information of one row.

                    userDbHelper.addFacilityInformations(userDbHelper,
                            String.valueOf(initial_id),
                            row.get(1),
                            row.get(2),
                            row.get(3),
                            row.get(4),
                            row.get(5));
                    initial_id++;
                }
            }

            prefs.edit().putBoolean("firstrun", false).commit();
            //Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
            userDbHelper.close();

        }

        //UserDbHelper userDbHelper;
        //int i =0;
        //userDbHelper = new UserDbHelper(context);
        //userDbHelper.addInformations(userDbHelper, "815 Goyeau St", "42.3118656219999", "-83.0334707361", "Fire Hall #1");
        //userDbHelper.addInformations(userDbHelper,"3121 Milloy St","42.3068574909","-82.9869316877999","Fire Hall #2");
        //userDbHelper.addInformations(userDbHelper,"1905 Cabana Rd W","42.2531537838999","-83.0239833768","Fire Hall #5");
        //userDbHelper.addInformations(userDbHelper,"2750 Ouellette Ave","42.2821369613999","-83.0156976205999","Fire Hall #3");
        //userDbHelper.addInformations(userDbHelper,"2600 College Ave","42.2999231237999","-83.0628378768","Fire Hall #4");
        //userDbHelper.addInformations(userDbHelper,"1380 Matthew Brady Blvd","42.3215477939","-82.9386634266","Fire Hall #7");
        //userDbHelper.addInformations(userDbHelper, "Windsor Airport", "42.2692392288999", "-82.9662277009", "Fire Hall #6");
        //Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        //userDbHelper.close();
        /*
        Cursor CR = userDbHelper.getInformations(userDbHelper);
        if(CR.moveToFirst())
        {
            do
            {
                //fsl[i].setAddress(CR.getString(0));
                double coordinate_x = Double.parseDouble(CR.getString(1));
                double coordinate_y = Double.parseDouble(CR.getString(2));
                fireStationArrayList.add(i,new FireStation(i,CR.getString(0),coordinate_x,coordinate_y,CR.getString(3)));

                //fs_coordinates[i] = new LatLng(coordinate_x,coordinate_y);
                //mMap.addMarker(new MarkerOptions().position(new LatLng(coordinate_x,coordinate_y)));
                //fsl[i].setX(coordinate_x);
                //fsl[i].setY(coordinate_y);
                //fsl[i].setOtherinfo(CR.getString(3));
                i++;
            }while(CR.moveToNext());
        }
        Toast.makeText(getBaseContext(),"i = " + i ,Toast.LENGTH_LONG).show();
        */

//        ToMap = (Button)findViewById(R.id.ToMap);
//        ToMap.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                MapsActivity.fireStationLocationList.clear();
//                /*
//                UserDbHelper userDbHelper;
//                userDbHelper = new UserDbHelper(context);
//                int i =0;
//
//                Cursor CR = userDbHelper.getInformations(userDbHelper);
//                if(CR.moveToFirst())
//                {
//                    do
//                    {
//                        //fsl[i].setAddress(CR.getString(0));
//                        double coordinate_x = Double.parseDouble(CR.getString(1));
//                        double coordinate_y = Double.parseDouble(CR.getString(2));
//
//
//                        test.add(coordinate_x);
//                        Toast.makeText(getBaseContext(),"Test X1 = " + test.get(i), Toast.LENGTH_LONG).show();
//                        //fs_coordinates[i] = new LatLng(coordinate_x,coordinate_y);
//                        //mMap.addMarker(new MarkerOptions().position(new LatLng(coordinate_x,coordinate_y)));
//                        //fsl[i].setX(coordinate_x);
//                        //fsl[i].setY(coordinate_y);
//                        //fsl[i].setOtherinfo(CR.getString(3));
//                        i++;
//                    }while(CR.moveToNext());
//                }
//
//                Toast.makeText(getBaseContext(),"i = " + i ,Toast.LENGTH_LONG).show();
//                */
//
//                Intent intent = new Intent();
//                intent.setClass(WelcomeActivity.this, MapsActivity.class);
//                /*
//                for(int j =0; j<fireStationArrayList.size();j++)
//                {
//                    //Toast.makeText(getBaseContext(),"X = " + fireStationArrayList.get(j).getX() ,Toast.LENGTH_LONG).show();
//                    MapsActivity.fireStationLocationList.add(j,new LatLng(fireStationArrayList.get(j).getX(),fireStationArrayList.get(j).getY()));
//                }*/
//                //Toast.makeText(getBaseContext(),"fireStationArrayList size = " + fireStationArrayList.size() ,Toast.LENGTH_LONG).show();
//                startActivity(intent);
//            }
//        });



        /**
         Initialize = (Button)findViewById(R.id.Initialize);
         Initialize.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        UserDbHelper userDbHelper;
        userDbHelper = new UserDbHelper(context);
        userDbHelper.deleteInformation(userDbHelper,Location_ini.NewCoordinateInfo.TABLE_NAME);
        userDbHelper.addFacilityInformations(userDbHelper,"1", "815 Goyeau St", "42.3118656219999", "-83.0334707361", "Fire Hall #1","FireStation");
        userDbHelper.addFacilityInformations(userDbHelper,"2", "3121 Milloy St", "42.3068574909", "-82.9869316877999", "Fire Hall #2","FireStation");
        userDbHelper.addFacilityInformations(userDbHelper,"3", "1905 Cabana Rd W", "42.2531537838999", "-83.0239833768", "Fire Hall #5","FireStation");
        userDbHelper.addFacilityInformations(userDbHelper,"4", "2750 Ouellette Ave", "42.2821369613999", "-83.0156976205999", "Fire Hall #3","FireStation");
        userDbHelper.addFacilityInformations(userDbHelper,"5", "2600 College Ave", "42.2999231237999", "-83.0628378768", "Fire Hall #4","FireStation");
        userDbHelper.addFacilityInformations(userDbHelper,"6", "1380 Matthew Brady Blvd", "42.3215477939", "-82.9386634266", "Fire Hall #7","FireStation");
        userDbHelper.addFacilityInformations(userDbHelper,"7", "Windsor Airport", "42.2692392288999", "-82.9662277009", "Fire Hall #6","FireStation");
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        userDbHelper.close();

        }
        });
         */

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, MapsActivity.class);
                startActivity(intent);
                this.cancel();
            }
        },4000);

    }





}
