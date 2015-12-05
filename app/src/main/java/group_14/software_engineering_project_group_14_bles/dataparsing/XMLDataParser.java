package group_14.software_engineering_project_group_14_bles.dataparsing;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Jerry on 11/24/2015.
 */
public class XMLDataParser {

    public static ArrayList<ArrayList<String>> parseFacilityData(Context context, String xmlFileName) {

        ArrayList<ArrayList<String>> facility = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();

            // Init parser.
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            InputStream inputStream = context.getApplicationContext().getAssets().open(xmlFileName);
            parser.setInput(inputStream, null);


            ArrayList<String> currentRowList = null;

            int rowId = 1;
            int eventType = parser.getEventType();

            // Parsing process.
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (tagName.equalsIgnoreCase("row")) {
                            currentRowList = new ArrayList<>();
                            currentRowList.add(String.valueOf(rowId));
                            rowId++;
                        } else if (currentRowList != null) {
                            currentRowList.add(parser.nextText());
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if (tagName.equalsIgnoreCase("row") && currentRowList != null) {
                            facility.add(currentRowList);
                        }
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            // TODO
        } catch (IOException e) {
            // TODO
        }

        return facility;
    }
}
