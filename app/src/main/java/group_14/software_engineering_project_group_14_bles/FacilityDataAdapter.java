package group_14.software_engineering_project_group_14_bles;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Jerry on 11/23/2015.
 */
public class FacilityDataAdapter {

    private ArrayList<ArrayList<String>> dataTable;

    public FacilityDataAdapter(Context context, String category) {
        DataOperation dataOperation = new DataOperation();
        this.dataTable = dataOperation.getFacilityInfo(context,
                dataOperation.getFacilities(context, category));
    }

    /**
     *
     * @param rowNumber
     * @return
     */
    public Facility getRow(int rowNumber) {

        return new Facility(dataTable.get(rowNumber).get(0),
                dataTable.get(rowNumber).get(1),
                Double.parseDouble(dataTable.get(rowNumber).get(2)),
                Double.parseDouble(dataTable.get(rowNumber).get(3)),
                dataTable.get(rowNumber).get(4),
                dataTable.get(rowNumber).get(5));
    }

}
