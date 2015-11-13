package group_14.software_engineering_project_group_14_bles;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msi1234 on 2015/11/11.
 */
public class Polygon_Contain {

    private  List<LineofPolygon> lines;
    //init max/min lat/lng data of boundary
    private double boundaries_MaxX = 0.0d;
    private double boundaries_MinX = 0.0d;
    private double boundaries_MaxY = 0.0d;
    private double boundaries_MinY = 0.0d;
    //for creating a ray
    private final LatLng OUTSIDE_POINT = new LatLng(42.216188, -82.993614);

     Polygon_Contain(List<LineofPolygon> lines){
        this.lines = lines;
    }

    public void updateBoundaries (ArrayList<LatLng> latlngs){
        for (LatLng l:latlngs){
                // set maximum/minimum x,y
                if (l.latitude > boundaries_MaxX)
                {
                    boundaries_MaxX = l.latitude;
                }
                else if (l.latitude < boundaries_MinX)
                {
                    boundaries_MinX = l.latitude;
                }

                if (l.longitude > boundaries_MaxY)
                {
                    boundaries_MaxY = l.longitude;
                }
                else if (l.longitude < boundaries_MinY)
                {
                    boundaries_MinY = l.longitude;
                }
        }
    }

    private boolean inBoundary(LatLng point)
    {
        if (point.latitude < boundaries_MinX || point.latitude > boundaries_MaxX ||
                point.longitude < boundaries_MinY || point.longitude > boundaries_MaxY)
        {
            return false;
        }
        return true;
    }

//    private LineofPolygon createRay(LatLng point)
//    {
//        LineofPolygon rayLine = new LineofPolygon(point, OUTSIDE_POINT);
//        return rayLine;
//    }

    private LineofPolygon createRay(LatLng point)
    {
        // create outside point
        double epsilon = (boundaries_MaxX - boundaries_MinX) / 100f;
        LatLng outsidePoint = new LatLng(boundaries_MinX - epsilon, boundaries_MinY);

        LineofPolygon vector = new LineofPolygon(outsidePoint, point);
        return vector;
    }

    /**
     * Check if the the given point is inside of the polygon.<br>
     *
     * @param point
     *            The point to check
     * @return <code>True</code> if the point is inside the polygon, otherwise return <code>False</code>
     */
    public boolean contains(LatLng point)
    {
        if (inBoundary(point))
        {
            LineofPolygon ray = createRay(point);
            int intersection = 0;
            for (LineofPolygon side : lines)
            {
                if (intersect(ray, side))
                {
                    // System.out.println("intersection++");
                    intersection++;
                }
            }

			/*
			 * If the number of intersections is odd, then the point is inside the polygon
			 */
            if (intersection % 2 == 1)
            {
                return true;
            }
        }
        return false;
    }

    public List<LineofPolygon> getLines()
    {
        return lines;
    }

    /**
     * By given ray and one side of the polygon, check if both lines intersect.
     *
     * @param ray
     * @param side
     * @return <code>True</code> if both lines intersect, otherwise return <code>False</code>
     */
    private boolean intersect(LineofPolygon ray, LineofPolygon side)
    {
        LatLng intersectPoint = null;

        // if both vectors aren't from the kind of x=1 lines then go into
        if (!ray.isVertical() && !side.isVertical())
        {
            // check if both vectors are parallel. If they are parallel then no intersection point will exist
            if (ray.getA() - side.getA() == 0)
            {
                return false;
            }

            double x = ((side.getB() - ray.getB()) / (ray.getA() - side.getA())); // x = (b2-b1)/(a1-a2)
            double y = side.getA() * x + side.getB(); // y = a2*x+b2
            intersectPoint = new LatLng(x, y);
        }

        else if (ray.isVertical() && !side.isVertical())
        {
            double x = ray.getStart().latitude;
            double y = side.getA() * x + side.getB();
            intersectPoint = new LatLng(x, y);
        }

        else if (!ray.isVertical() && side.isVertical())
        {
            double x = side.getStart().latitude;
            double y = ray.getA() * x + ray.getB();
            intersectPoint = new LatLng(x, y);
        }

        else
        {
            return false;
        }

        // System.out.println("Ray: " + ray.toString() + " ,Side: " + side);
        // System.out.println("Intersect point: " + intersectPoint.toString());

        if (side.isInside(intersectPoint) && ray.isInside(intersectPoint))
        {
            return true;
        }

        return false;
    }



}
