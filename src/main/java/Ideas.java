

import Model.PlaceLoosenessPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Ideas {

    public HashMap<String, ArrayList<PlaceLoosenessPair>> createWordPlaceMap(HashMap<Integer, Vertex> vertices){

        int[] index = {0};
        HashMap<String, ArrayList<PlaceLoosenessPair>> wordPlaceMap = new HashMap<>();
        vertices.entrySet().parallelStream().forEach(e -> {
            if(e.getValue().isPlace) {
                updateWordPlaceMapWithBFS(e.getKey(), vertices, wordPlaceMap, Constants.MAX_DEEP);
                index[0] += 1;
                if(index[0]  % 1000 == 0)
                    System.out.println(index[0]);
            }

        });
//        vertices.forEach((id, v) -> {
//            if(v.isPlace) {
//                updateWordPlaceMapWithBFS(id, vertices, wordPlaceMap, Constants.MAX_DEEP);
//                index[0] += 1;
//                if(index[0]  % 1000 == 0)
//                    System.out.println(index[0]);
//            }
//        });

        return wordPlaceMap;
    }

    public void updateWordPlaceMapWithBFS(int placeID, HashMap<Integer, Vertex> vertices, HashMap<String, ArrayList<PlaceLoosenessPair>> wordPlaceMap, int maxLevel){

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

                var words = vertices.get(current).name.split(",| |\t|_|(|)");
                for(String word : words){
                    if(word.length() > 2) {
                        if (wordPlaceMap.containsKey(word.toLowerCase()) == false) {
                            wordPlaceMap.put(word.toLowerCase(), new ArrayList<>());
                        }
                        wordPlaceMap.get(word.toLowerCase()).add(new PlaceLoosenessPair(current, level.get(current)));
                    }
                }

            }
        }
    }
}
