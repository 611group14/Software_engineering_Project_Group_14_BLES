package group_14.software_engineering_project_group_14_bles;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by msi1234 on 2015/11/11.
 */
public class LineofPolygon {
    private final LatLng _start;
    private final LatLng _end;
    private double _a = Float.NaN;
    private double _b = Float.NaN;
    private boolean _vertical = false;

    public LineofPolygon(LatLng start, LatLng end)
    {
        _start = start;
        _end = end;

        if (_end.latitude - _start.latitude != 0)
        {
            _a = ((_end.longitude - _start.longitude) / (_end.latitude - _start.latitude));
            _b = _start.longitude - _a * _start.latitude;
        }

        else
        {
            _vertical = true;
        }
    }

    public boolean isInside(LatLng point)
    {
        if(point.latitude-_start.latitude!=0 && point.latitude-_end.latitude!=0)
        {
            if((point.longitude-_start.longitude)/(point.latitude-_start.latitude)==
                (point.longitude-_end.longitude)/(point.latitude-_end.latitude)) return true;

             else return false;
        }
        else return false;
    }

    public LatLng getStart()
    {
        return _start;
    }

    public LatLng getEnd()
    {
        return _end;
    }

    @Override
    public String toString()
    {
        return String.format("%s-%s", _start.toString(), _end.toString());
    }

    /**
     * Indicate whereas the line is vertical. <br>
     * For example, line like x=1 is vertical, in other words parallel to axis Y. <br>
     * In this case the A is (+/-)infinite.
     *
     * @return <code>True</code> if the line is vertical, otherwise return <code>False</code>
     */
    public boolean isVertical()
    {
        return _vertical;
    }

    /**
     * y = <b>A</b>x + B
     *
     * @return The <b>A</b>
     */
    public double getA()
    {
        return _a;
    }

    /**
     * y = Ax + <b>B</b>
     *
     * @return The <b>B</b>
     */
    public double getB()
    {
        return _b;
    }
}
