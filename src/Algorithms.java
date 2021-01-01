import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Algorithms {

    public static int findLoosenessWithBFS(int placeID, HashMap<Integer, Vertex> vertices, ArrayList<String> queryKeywords, int maxLevel){
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
                for (String queryKeyword : queryKeywords) {
                    if (vertices.get(current).name.contains(queryKeyword) || vertices.get(current).name.equals(queryKeyword)) {
                        found.add(queryKeyword);
                        System.out.println("Keyword: " + queryKeyword + "  Level: " + level.get(current) + "  Vertex: "  + vertices.get(current).name + "   " + vertices.get(current).connectedNodes);
                        looseness += level.get(current);
                    }
                }

                queryKeywords.removeAll(found);
                found.clear();
            }
        }

        if(queryKeywords.isEmpty()){
            return  looseness;
        }else{
            return Integer.MAX_VALUE;
        }

    }

    public static double euclidianDistance(Vertex v, double x, double y){
        return Math.sqrt(Math.pow((v.location.x - x), 2) + Math.pow((v.location.y - y), 2));
    }
}
