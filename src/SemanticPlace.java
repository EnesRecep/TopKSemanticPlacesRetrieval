import jdk.jshell.execution.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SemanticPlace {

    public TreeMap<Double, Vertex> BSP(HashMap<Integer, Vertex> vertices, ArrayList<String> queryKeywords, Point coordinate){
        TreeMap<Double, Vertex> Hk = new TreeMap<>();
//        TreeMap<Vertex, Double> Hk = new TreeMap<>();



        double theta = Double.MAX_VALUE;

        for(int i = 0 ;i < 20 ; i++){

            Vertex processing = vertices.get(vertices.keySet().toArray()[i]);

            if(Algorithms.euclidianDistance(processing,coordinate.x, coordinate.y) >= theta /*&& getSize(Hk) > 10*/){
                continue;
            }

            int looseness = Algorithms.findLoosenessWithBFS(processing.ID, vertices, (ArrayList<String>) queryKeywords.clone(), 5);

            if(looseness == Integer.MAX_VALUE){
                continue;
            }

            double rankingScore = looseness * Algorithms.euclidianDistance(processing,coordinate.x,coordinate.y);

            Hk.put(rankingScore, processing);

        }


        TreeMap<Double, Vertex> firstFive = putFirstEntries(5, Hk);

        return Hk;
    }

    public static TreeMap<Double,Vertex> putFirstEntries(int max, TreeMap<Double,Vertex> source) {
        int count = 0;
        TreeMap<Double,Vertex> target = new TreeMap<Double,Vertex>();
        for (Map.Entry<Double,Vertex> entry:source.entrySet()) {
            if (count >= max) break;

            target.put(entry.getKey(), entry.getValue());
            count++;
        }
        return target;
    }

}
