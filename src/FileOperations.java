import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileOperations {

    public HashMap<Integer, Vertex> readTSVTripletsIntoHashMap(String path){
        HashMap<Integer, Vertex> vertices = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split("\t");
                if(elements.length == 4){

                    String subject = elements[1];
                    String object = elements[3];
                    if(vertices.containsKey(subject.hashCode()) == false){
                        vertices.put(subject.hashCode(), new Vertex(subject.hashCode(), subject));
                    }
                    if(vertices.containsKey(object.hashCode()) == false){
                        vertices.put(object.hashCode(), new Vertex(object.hashCode(), object));
                    }

                    vertices.get(subject.hashCode()).connectedNodes.add(object.hashCode());

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vertices;
    }
}
