import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class FileOperations {

    public HashMap<Integer, Vertex> readTSVTripletsIntoHashMap(String path){
        HashMap<Integer, Vertex> vertices = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {

                /*
                    Example Data
                    <id_Uy5EwU3nX1_H?S_otuJrkvKs1>	<Richard_Stallman>	<isLeaderOf>	<Free_Software_Foundation>
                    Split with TAB
                    [1] => Subject
                    [3] => Object
                 */
                String[] elements = line.split("\t");
                if(elements.length == 4){

                    String subject = elements[1].substring(1, elements[1].length() - 1);
                    String object = elements[3].substring(1, elements[3].length() - 1);
                    if(vertices.containsKey(subject.hashCode()) == false){
                        vertices.put(subject.hashCode(), new Vertex(subject.hashCode(), subject));
                    }
                    if(vertices.containsKey(object.hashCode()) == false){
                        vertices.put(object.hashCode(), new Vertex(object.hashCode(), object));
                    }

                    vertices.get(subject.hashCode()).connectedNodes.add(object.hashCode());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vertices;
    }

    public void SetPlaceInfoOfVertices(HashMap<Integer, Vertex> vertices, String path){
        HashSet<String> set = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            int num = 0;
            while ((line = br.readLine()) != null) {

                if(line.contains("<http://www.georss.org/georss/point>")){
                    var elements =  line.split(" ");
                    var loc = elements[0].substring(elements[0].lastIndexOf('/') + 1, elements[0].indexOf('>'));
                    var x = elements[2].substring(1);
                    var y = elements[3].substring(0, elements[3].length() - 4);

                    if(vertices.containsKey(loc.hashCode())){
                        vertices.get(loc.hashCode()).isPlace = true;
                        vertices.get(loc.hashCode()).location.x = Double.parseDouble(x);
                        vertices.get(loc.hashCode()).location.y = Double.parseDouble(y);;

                    }
                num++;
                }
                //<http://dbpedia.org/resource/Union_Christian_Academy> <http://www.georss.org/georss/point> "36.05777777777778 -90.55833333333334"@en .

                //if(elements[1].contains("isLocatedIn") == false)
                //    set.add(elements[1].substring(elements[1].indexOf('_') + 1, elements[1].lastIndexOf('_')));
                ///System.out.println(elements[1].substring(elements[1].indexOf('_') + 1, elements[1].lastIndexOf('_')) + " # " + elements[3]);

            }
            System.out.println(num);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
