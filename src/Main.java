import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    HashMap<Integer, Vertex> vertices;
    public Main() {
        FileOperations f = new FileOperations();


        var start = System.currentTimeMillis();
        vertices = f.readTSVTripletsIntoHashMap(Paths.FACTS_PATH);
        f.SetPlaceInfoOfVertices(vertices, Paths.PLACES2_PATH);


        System.out.println("Total time (ms): " + (System.currentTimeMillis() - start));
        System.out.println("Total # of Vertex: " + vertices.size());
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
