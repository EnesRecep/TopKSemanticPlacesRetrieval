import java.util.HashMap;

public class Main {

    public Main(){
        FileOperations f = new FileOperations();
        HashMap<Integer, Vertex> vertices = f.readTSVTripletsIntoHashMap(Paths.FACTS_PATH);

        var verticesAsArray = vertices.values().toArray();
        for(int i = 0 ; i < 10 ; i++){
            System.out.println(verticesAsArray[i]);
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}
