

import Model.PlaceLoosenessPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Ideas {

    public HashMap<String, ArrayList<PlaceLoosenessPair>> createWordPlaceMap(){

    }

    public static int updateWordPlaceMapWithBFS(int placeID, HashMap<Integer, Vertex> vertices, HashMap<String, ArrayList<PlaceLoosenessPair>> wordPlaceMap, int maxLevel){
        int looseness = 0;
        ArrayList<String> found = new ArrayList<>();

        HashMap<Integer, Integer> level = new HashMap<>();
        HashMap<Integer, Boolean> marked = new HashMap<>();

        Queue<Integer> queue = new LinkedList<Integer>();

        queue.add(placeID);

        level.put(placeID, 0);

        while(queue.size()  > 0){

            var current = queue.peek();
            queue.remove();


            if(marked.containsKey(current) == false) {
                marked.put(current, true);

                if(level.get(current) < maxLevel) {
                    for (Integer connectedNode : vertices.get(current).connectedNodes) {
                        queue.add(connectedNode);
                        level.put(connectedNode, level.get(current) + 1);
                    }
                }

                var words = vertices.get(current).name.split(",| |\t|_");
                for(String word : words){
                    if(wordPlaceMap.containsKey(word) == false){
                        wordPlaceMap.put(word, new ArrayList<>());
                    }
                    //Update map
                }

            }
        }
    }
}
