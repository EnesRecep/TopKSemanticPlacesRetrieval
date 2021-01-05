import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SemanticPlace {

    public TreeMap<Double, Vertex> BSP(HashMap<Integer, Vertex> vertices, RTree<Integer, Geometry> rtree, ArrayList<String> queryKeywords, Point coordinate) {
        TreeMap<Double, Vertex> Hk = new TreeMap<>();

        double theta = Double.MAX_VALUE;

        /*Temporary looking for 200 vertex, Will be get from R-Tree*/
        //for(int i = 0 ;i < 200 ; i++){

        var near = rtree.nearest(Geometries.point(coordinate.x, coordinate.y), 3, 500);

        var nearList = near.toList().toBlocking().single();

        for (Entry<Integer, Geometry> place : nearList) {

            Vertex processing = vertices.get(place.value());

            if (Algorithms.euclidianDistance(processing, coordinate.x, coordinate.y) >= theta && Hk.size() > 10) {
                continue;
            }

            int looseness = Algorithms.findLoosenessWithBFS(processing.ID, vertices, (ArrayList<String>) queryKeywords.clone(), 5);

            if (looseness == Integer.MAX_VALUE) {
                continue;
            }

            double rankingScore = looseness * Algorithms.euclidianDistance(processing, coordinate.x, coordinate.y);

            Hk.put(rankingScore, processing);

            if (Hk.size() > 10) {
                Hk.remove(Hk.lastKey());
            }
        }


        //TreeMap<Double, Vertex> firstFive = putFirstEntries(5, Hk);

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

}
