package group_14.software_engineering_project_group_14_bles.evaluation;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Jerry on 11/22/2015.
 */
public class ScoredItemListFragment extends ListFragment {

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
