import java.util.HashMap;

public class Main {

    public Main() {
        FileOperations f = new FileOperations();


        var start = System.currentTimeMillis();
        HashMap<Integer, Vertex> vertices = f.readTSVTripletsIntoHashMap(Paths.FACTS_PATH);
        f.SetPlaceInfoOfVertices(vertices, Paths.PLACES2_PATH);
        var verticesAsArray = vertices.values().toArray();
        int numOfPlaces = 0;
//        for (int i = 0; i < 200; i++) {
//            if (((Vertex) verticesAsArray[i]).isPlace) {
//                //System.out.println(verticesAsArray[i]);
//                numOfPlaces++;
//            }
//        }

        for (Integer key : vertices.keySet()) {
            if(vertices.get(key).isPlace)
                numOfPlaces++;
        }
        System.out.println("Total time (ms): " + (System.currentTimeMillis() - start));
        System.out.println("Total # of Places: " + numOfPlaces);
        System.out.println("Total # of Vertex: " + vertices.size());
    }

    public static void main(String[] args) {
        new Main();
    }
}
