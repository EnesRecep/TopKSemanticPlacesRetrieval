

import Model.PlaceLoosenessPair;
import Model.WordLoosenessPair;
import com.github.davidmoten.rtree.internal.util.Pair;

import java.util.*;

public class Ideas {

    public HashMap<String, HashSet<PlaceLoosenessPair>> createWordPlaceMap(HashMap<Integer, Vertex> vertices) {
        HashMap<String, HashSet<PlaceLoosenessPair>> wordPlaceMap = new HashMap<>();
        int[] index = {0};

        Iterator<Map.Entry<Integer, Vertex>> itr = vertices.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<Integer, Vertex> e = itr.next();
            try {
                if (e.getValue().isPlace) {
                    updateWordPlaceMapWithBFS(e.getKey(), vertices, wordPlaceMap, Constants.MAX_DEEP);
                    index[0] += 1;
                    if (index[0] % 1000 == 0) {
                        System.out.println(index[0]);
                        System.out.println(wordPlaceMap.size());
                    }

                    vertices.remove(e.getKey());
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
//        vertices.entrySet().parallelStream().forEach(e -> {
//            try {
//                if (e.getValue().isPlace) {
//                    updateWordPlaceMapWithBFS(e.getKey(), vertices, wordPlaceMap, Constants.MAX_DEEP);
//                    index[0] += 1;
//                    if (index[0] % 1000 == 0) {
//                        System.out.println(index[0]);
//                        System.out.println(wordPlaceMap.size());
//                    }
//                }
//            } catch (Exception ex) {
//                System.out.println(ex.toString());
//            }
//
//        });
//        vertices.forEach((id, v) -> {
//            if(v.isPlace) {
//                updateWordPlaceMapWithBFS(id, vertices, wordPlaceMap, Constants.MAX_DEEP);
//                index[0] += 1;
//                if(index[0]  % 1000 == 0) {
//                    System.out.println(index[0]);
//                    System.out.println(wordPlaceMap.size());
//                }
//            }
//        });

        return wordPlaceMap;
    }

    public void updateWordPlaceMapWithBFS(int placeID, HashMap<Integer, Vertex> vertices, HashMap<String, HashSet<PlaceLoosenessPair>> wordPlaceMap, int maxLevel) {

        HashMap<Integer, Integer> level = new HashMap<>();
        HashMap<Integer, Boolean> marked = new HashMap<>();

        Queue<Integer> queue = new LinkedList<Integer>();

        queue.add(placeID);

        level.put(placeID, 0);

        while (queue.size() > 0) {

            var current = queue.peek();
            queue.remove();


            if (marked.containsKey(current) == false) {
                marked.put(current, true);

                if (level.get(current) < maxLevel) {
                    for (Integer connectedNode : vertices.get(current).connectedNodes) {
                        queue.add(connectedNode);
                        level.put(connectedNode, level.get(current) + 1);
                    }
                }

                var words = vertices.get(current).name.split(",|\\s+|_|/(|/)");
                for (String word : words) {
                    if (word.length() > 2/* && word.toLowerCase().startsWith("a")*/) {
                        if (wordPlaceMap.containsKey(word.toLowerCase()) == false) {
                            wordPlaceMap.put(word.toLowerCase(), new HashSet<>());
                        }

                        if (!wordPlaceMap.get(word.toLowerCase()).contains(new PlaceLoosenessPair(current, level.get(current)))) {
                            wordPlaceMap.get(word.toLowerCase()).add(new PlaceLoosenessPair(current, level.get(current)));
                        }
                    }
                }

            }
        }

        //vertices.get(placeID).connectedNodes.clear();
        //vertices.remove(placeID);
    }

    public void createPlaceWordMap(HashMap<Integer, Vertex> vertices) {
        int[] index = {0};

        Comparator<WordLoosenessPair> comparator = new Comparator<WordLoosenessPair>() {
            @Override
            public int compare(WordLoosenessPair o1, WordLoosenessPair o2) {
                return o1.word.compareTo(o2.word);
            }
        };


        HashMap<Integer, TreeSet<WordLoosenessPair>> wordPlaceMap = new HashMap<>();

        var entrySet = vertices.entrySet().toArray();
        for (int i = 0; i < entrySet.length; i++) {
            var e = (Map.Entry<Integer, Vertex>) entrySet[i];

            if (e.getValue().isPlace) {
                updatePlaceWordMapWithBFS(e.getKey(), vertices, wordPlaceMap, Constants.MAX_DEEP);
            }

            if(i % 10000 == 0 && i != 0){
                Utils.writeHashMap(wordPlaceMap, "PWM_55\\PWM_" + Constants.MAX_DEEP + "_" + i);
                wordPlaceMap = new HashMap<>();
                System.out.println("Index: " + i + "/" + entrySet.length);
//                long heapSize = Runtime.getRuntime().totalMemory() / (1024 * 1024);
//                long heapMaxSize = Runtime.getRuntime().maxMemory() / (1024 * 1024);
//                long heapFreeSize = Runtime.getRuntime().freeMemory() / (1024 * 1024);
//                System.out.println("Memory: " + heapSize + " / " + heapMaxSize);
//                System.gc();

            }

        }


        /*
        vertices.entrySet().forEach(e -> {
            if (e.getValue().isPlace) {
                updatePlaceWordMapWithBFS(e.getKey(), vertices, wordPlaceMap, Constants.MAX_DEEP);
                index[0] += 1;
                if (index[0] % 10000 == 0) {
                    Utils.writeHashMap(wordPlaceMap, "PWM_5\\PWM_" + Constants.MAX_DEEP + "_" + index[0]);
                    wordPlaceMap.clear();
                    System.out.println(index[0]);
                    // Get current size of heap in bytes
                    long heapSize = Runtime.getRuntime().totalMemory() / (1024 * 1024);

// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
                    long heapMaxSize = Runtime.getRuntime().maxMemory() / (1024 * 1024);

                    // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
                    long heapFreeSize = Runtime.getRuntime().freeMemory() / (1024 * 1024);

                    System.out.println(heapSize + " / " + heapMaxSize);
                    System.gc();
                }
            }

        });
        */

        //return wordPlaceMap;
    }

    public void updatePlaceWordMapWithBFS(int placeID, HashMap<Integer, Vertex> vertices, HashMap<Integer, TreeSet<WordLoosenessPair>> placeWordMap, int maxLevel) {

        HashMap<Integer, Integer> level = new HashMap<>();
        HashMap<Integer, Boolean> marked = new HashMap<>();

        Queue<Integer> queue = new LinkedList<Integer>();

        queue.add(placeID);

        level.put(placeID, 0);

        while (queue.size() > 0) {

            var current = queue.peek();
            queue.remove();


            if (marked.containsKey(current) == false) {
                marked.put(current, true);
                if (placeWordMap.containsKey(placeID) == false) {
                    placeWordMap.put(current, new TreeSet<>());
                }


                if (level.get(current) < maxLevel) {
                    for (Integer connectedNode : vertices.get(current).connectedNodes) {
//                        if (placeWordMap.containsKey(connectedNode)) {
//                            var temp = placeWordMap.get(connectedNode);
//                            for (WordLoosenessPair wordLoosenessPair : temp) {
//
//                                    placeWordMap.get(current).add(new WordLoosenessPair(wordLoosenessPair.word, wordLoosenessPair.looseness + level.get(current)));
//                            }
//                        } else {
                            queue.add(connectedNode);
                            level.put(connectedNode, level.get(current) + 1);
                       // }


                    }
                }
                var words = vertices.get(current).name.split(",|\\s+|_|/(|/)");
                for (String word : words) {
                    if (word.length() > 2 /*&& word.toLowerCase().startsWith("a")*/) {

                        boolean found = false;
                        var tempObj = new WordLoosenessPair(word.toLowerCase(), level.get(current));
                        if(placeWordMap.get(placeID).contains(tempObj)){

//                            int count = (int)placeWordMap.get(placeID).stream().filter(p -> p.word.equals(word) && p.looseness < level.get(current)).count();
//                            if(count == 0){
//                                placeWordMap.get(placeID).remove(tempObj);
//                                placeWordMap.get(placeID).add(tempObj);
//                            }
                        }else{
                            placeWordMap.get(placeID).add(tempObj);
                        }

//                        for (WordLoosenessPair pair : placeWordMap.get(current)) {
//                            if(pair.word.equals(word)){
//                                if(pair.looseness > level.get(current)){
//
//                                    pair.looseness = level.get(current);
//                                    found = true;
//                                    break;
//                                }
//                            }
//                        }
//                        if(!found)
//                            placeWordMap.get(current).add(new WordLoosenessPair(word.toLowerCase(), level.get(current)));
                    }
                }


            }
        }
    }
}
