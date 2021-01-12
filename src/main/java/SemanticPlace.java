import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SemanticPlace {

    public TreeMap<Double, Vertex> BSP(HashMap<Integer, Vertex> vertices, RTree<Integer, Geometry> rtree, ArrayList<String> queryKeywords, Location coordinate) {
        TreeMap<Double, Vertex> Hk = new TreeMap<>();

        long one = System.currentTimeMillis();
        double theta = Double.MAX_VALUE;

        /*Temporary looking for 200 vertex, Will be get from R-Tree*/
        //for(int i = 0 ;i < 200 ; i++){

        var near = rtree.nearest(Geometries.point(coordinate.x, coordinate.y), 10, 10000);

        var nearList = near.toList().toBlocking().single();

        long two = System.currentTimeMillis();
        for (Entry<Integer, Geometry> place : nearList) {


            Vertex processing = vertices.get(place.value());

            if (/*Algorithms.euclidianDistance(processing, coordinate.x, coordinate.y)*/ getNormalizeDistance(coordinate.x, coordinate.y, processing.location.x, processing.location.y, Constants.MAX_DISTANCE_KM) >= theta && Hk.size() >= 10) {
                System.out.println("Returned Early");
                return Hk;
            }

            int looseness = Algorithms.findLoosenessWithBFS(processing.ID, vertices, (ArrayList<String>) queryKeywords.clone(), Constants.MAX_DEEP);

            if (looseness == Integer.MAX_VALUE) {
                //System.out.println(processing);
                continue;
            }else{
                //System.out.println(looseness + "  " + processing);
            }

            double rankingScore = getNormalizeLooseness(looseness, Constants.MAX_LOOSENESS) * getNormalizeDistance(coordinate.x, coordinate.y, processing.location.x, processing.location.y, Constants.MAX_DISTANCE_KM) /*Algorithms.euclidianDistance(processing, coordinate.x, coordinate.y)*/;

            Hk.put(rankingScore, processing);

            if (Hk.size() > 10) {
                Hk.remove(Hk.lastKey());
                theta = Hk.lastKey().doubleValue();
            }
        }

        long three = System.currentTimeMillis();

        //TreeMap<Double, Vertex> firstFive = putFirstEntries(5, Hk);

        System.out.println("1: " + (two - one));
        System.out.println("2: " + (three - two));

        return Hk;
    }

    public static TreeMap<Double, Vertex> putFirstEntries(int max, TreeMap<Double, Vertex> source) {
        int count = 0;
        TreeMap<Double, Vertex> target = new TreeMap<>();
        for (Map.Entry<Double, Vertex> entry : source.entrySet()) {
            if (count >= max) break;

            target.put(entry.getKey(), entry.getValue());
            count++;
        }
        return target;
    }

    public static double getNormalizeLooseness(int looseness, int maxAllowedLooseness){
        return Math.min((double)looseness, (double)maxAllowedLooseness) / (double)maxAllowedLooseness;
    }
    public static double getNormalizeDistance(double lat1, double lon1, double lat2, double lon2, int maxAllowedDistance){
        var distance = Utils.distanceInKM(lat1, lon1, lat2, lon2);
        return Math.min((double)distance, (double)maxAllowedDistance) / (double)maxAllowedDistance;
    }
}
