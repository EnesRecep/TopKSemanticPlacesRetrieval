import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import org.w3c.dom.ls.LSOutput;
import rx.Observable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    HashMap<Integer, Vertex> vertices;
    public Main() {
        FileOperations f = new FileOperations();


        RTree<Integer, Geometry> Rtree = RTree.create();

        long start = System.currentTimeMillis();
        vertices = f.readTSVTripletsIntoHashMap(Paths.FACTS_PATH);
        f.SetPlaceInfoOfVertices(vertices, Paths.PLACES2_PATH);

        //tree = tree.add(item, Geometries.point(10,20));
        for (Map.Entry<Integer, Vertex> vertexEntry : vertices.entrySet()) {
            Vertex vertex = vertexEntry.getValue();
            if(vertex.isPlace){
                Rtree = Rtree.add(vertex.ID, Geometries.point(vertex.location.x,vertex.location.y));
            }
        }

        var near =  Rtree.nearest(Geometries.point(39.9, 32.8), 2, 100);

        near.forEach(n -> System.out.println(vertices.get(n.value())));

        var temp = vertices.get(vertices.keySet().toArray()[0]);
        //printMapWithLevels(0, 2, temp);
        Algorithms a = new Algorithms();
        var looseness = a.findLoosenessWithBFS(temp.ID, vertices, new ArrayList<String>(Arrays.asList("stadion", "krone", "Battle")), 5);


        SemanticPlace sp = new SemanticPlace();
        var result = sp.BSP(vertices, Rtree, new ArrayList<String>(Arrays.asList("stadion", "krone", "Battle")), new Point(30, 20));


        for (Map.Entry<Double, Vertex> doubleVertexEntry : result.entrySet()) {
            System.out.println(doubleVertexEntry);
        }
        //System.out.println(result);
        System.out.println("Looseness: " + looseness);
        System.out.println("Total # of Vertex: " + vertices.size());
        System.out.println("Total time (ms): " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        new Main();
    }

    public void printMapWithLevels(int level, int maxLevel, Vertex vertex){
        for(int i = 0 ; i < level ; i++)
            System.out.print("- ");
        System.out.println(vertex.name + " -> " + vertex.ID + " -> " + vertex.connectedNodes);
        if(level <= maxLevel) {
            for (Integer connectedNode : vertex.connectedNodes) {
                printMapWithLevels(level + 1, maxLevel,  vertices.get(connectedNode));
            }
        }
    }
}
