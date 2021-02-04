import Model.PlaceLoosenessPair;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import org.w3c.dom.ls.LSOutput;
import rx.Observable;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    HashMap<Integer, Vertex> vertices;

    public Main() {
        FileOperations f = new FileOperations();

        long start = System.currentTimeMillis();

        RTree<Integer, Geometry> Rtree = RTree.create();

//        vertices = f.readTSVTripletsIntoHashMap(Paths.FACTS_PATH);
//        f.SetPlaceInfoOfVertices(vertices, Paths.PLACES2_PATH);
//        Utils.writeHashMap(vertices, "vertices");
//
//        if(1 == 1)
//            return;
        //vertices.clear();
        vertices = (HashMap<Integer, Vertex>)Utils.readHashMap("vertices");
/*
        long writeStart = System.currentTimeMillis();

        try {
            Utils.WriteMap(new FileOutputStream(new File("map.txt")), vertices);
        } catch (IOException e) {
            e.printStackTrace();
        }

        vertices.clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long readStart = System.currentTimeMillis();
        try {
            var newVertices = Utils.ReadMap(new FileInputStream(new File("map.txt")));
            System.out.println(newVertices);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        long readEnd = System.currentTimeMillis();

        System.out.println("Write (ms): " + (readStart - writeStart));
        System.out.println("Read (ms): " + (readEnd - readStart));
        */

        ///long mapCreationInit = System.currentTimeMillis();

//        var t = (HashMap<String, ArrayList<PlaceLoosenessPair>>)Utils.readHashMap("WordPlaceMap_3");
//        t.forEach((k,v) ->{
//            System.out.println(k + v);
//        });
        Ideas ideas = new Ideas();
        //var result = ideas.createWordPlaceMap(vertices);
        ideas.createPlaceWordMap(vertices);
        //Utils.writeHashMap(result, "PlaceWordMap_55");
        //var y = Utils.readHashMap("PlaceWordMap_55");

//        y.forEach((k,v) -> {
//            System.out.println(k + " -> " + v);
//        });

        /*
        //tree = tree.add(item, Geometries.point(10,20));
        for (Map.Entry<Integer, Vertex> vertexEntry : vertices.entrySet()) {
            Vertex vertex = vertexEntry.getValue();
            if(vertex.isPlace){
                Rtree = Rtree.add(vertex.ID, Geometries.point(vertex.location.x,vertex.location.y));
            }
        }
*/

        //var temp = vertices.get(vertices.keySet().toArray()[0]);
        //printMapWithLevels(0, 2, temp);
        //Algorithms a = new Algorithms();
        //var looseness = a.findLoosenessWithBFS(temp.ID, vertices, new ArrayList<String>(Arrays.asList("stadion", "krone", "Battle")), 5);


        //long start = System.currentTimeMillis();

        //SemanticPlace sp = new SemanticPlace();
        //var result = sp.BSP(vertices, Rtree, new ArrayList<String>(Arrays.asList("Ankara", "Turkey", "University")), new Location(39.92, 32.85));
/*

        var queries = Arrays.asList(new ArrayList<String>(Arrays.asList("Ankara", "Turkey", "University")),
                new ArrayList<String>(Arrays.asList("Art", "Museum", "Ankara")),
                new ArrayList<String>(Arrays.asList("Food", "Meat", "Ankara")));
        for (ArrayList<String> query : queries) {
            for(int i = 3 ; i <= 6 ; i++){
                Constants.MAX_DEEP = i == 6 ? Integer.MAX_VALUE :  i;
                long queryStart = System.currentTimeMillis();
                var result = sp.BSP(vertices, Rtree, query, new Location(39.92, 32.85));
                long queryEnd = System.currentTimeMillis();
                System.out.println((queryEnd - queryStart) + " ms. -> Depth : "+ Constants.MAX_DEEP + " -> Query : " + query);

            }
        }
*/

//        for (Map.Entry<Double, Vertex> doubleVertexEntry : result.entrySet()) {
//            System.out.println(doubleVertexEntry);
//        }



        //System.out.println(result);
        //System.out.println("Looseness: " + looseness);
        System.out.println("Total # of Vertex: " + vertices.size());
        //System.out.println("Total time for creating map (ms): " + (System.currentTimeMillis() - mapCreationInit));
        System.out.println("Total time (ms): " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        new Main();
    }

    public void printMapWithLevels(int level, int maxLevel, Vertex vertex) {
        for (int i = 0; i < level; i++)
            System.out.print("- ");
        System.out.println(vertex.name + " -> " + vertex.ID + " -> " + vertex.connectedNodes);
        if (level <= maxLevel) {
            for (Integer connectedNode : vertex.connectedNodes) {
                printMapWithLevels(level + 1, maxLevel, vertices.get(connectedNode));
            }
        }
    }
}
