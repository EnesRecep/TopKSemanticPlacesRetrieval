import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    HashMap<Integer, Vertex> vertices;
    public Main() {
        FileOperations f = new FileOperations();


        var start = System.currentTimeMillis();
        vertices = f.readTSVTripletsIntoHashMap(Paths.FACTS_PATH);
        f.SetPlaceInfoOfVertices(vertices, Paths.PLACES2_PATH);

        var temp = vertices.get(vertices.keySet().toArray()[0]);
        printMapWithLevels(0, 2, temp);
        Algorithms a = new Algorithms();
        var looseness = a.findLoosenessWithBFS(temp.ID, vertices, new ArrayList<String>(Arrays.asList("stadion", "krone", "Battle")), 5);


        SemanticPlace sp = new SemanticPlace();
        var result = sp.BSP(vertices, new ArrayList<String>(Arrays.asList("stadion", "krone", "Battle")), new Point(30, 20));


        System.out.println(result);
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
