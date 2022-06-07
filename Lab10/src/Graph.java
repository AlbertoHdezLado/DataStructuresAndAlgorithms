import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.*;

public class Graph {

    int[][] arr;
    static ArrayList<String> nodes = new ArrayList<>();

    public ArrayList<String> findNeighbours(String x) {
        int nodeIndex = -1;

        ArrayList<String> neighbours = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(x)) {
                nodeIndex = i;
                break;
            }
        }

        if (nodeIndex != -1) {
            for (int j = 0; j < arr[nodeIndex].length; j++) {
                if (arr[nodeIndex][j] == 1) {
                    neighbours.add(nodes.get(j));
                }
            }
        }
        return neighbours;
    }

    private final Map<String, Integer> strToInt = new HashMap<>();

    private int getIndex(String str) {
        return strToInt.get(str);
    }

    // The argument type depend on a selected collection in the Main class
    public Graph(SortedMap<String, Document> internet) {
        int size = internet.size();
        arr = new int[size][size];

        int index = 0;
        for (Entry<String, Document> entry : internet.entrySet()) {
            //Store on HashMap string and index
            strToInt.put(entry.getKey(), index);
            index++;
        }
        for (Entry<String, Document> entry : internet.entrySet()) {
            Document document = entry.getValue();
            //For each link (entry.getValue().link) entries
            for (Entry<String, Link> link : document.link.entrySet()) {
                int x = getIndex(entry.getKey());
                int y = getIndex(link.getKey());
                arr[x][y] = 1; //add connections
            }
            //Add key to node list
            nodes.add(entry.getKey());
        }
    }

    //Breadth first search
    public String bfs(String start) {
        if (!strToInt.containsKey(start)) {
            return null;
        }

        //Defining auxiliary structures
        StringBuilder result = new StringBuilder();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String element = queue.remove();
            result.append(element).append(", ");
            ArrayList<String> neighbours = findNeighbours(element); //find every neighbours
            for (int i = 0; i < neighbours.size(); i++) {
                String n = neighbours.get(i);
                if (n != null && !visited.contains(n)) {
                    queue.add(n);
                    visited.add(n);
                }
            }

        }
        if (result.length() > 0) {
            return result.substring(0, result.length() - 2).trim();
        } else
            return null;
    }

    //Depth first search
    public String dfs(String start) {
        if (!strToInt.containsKey(start)) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        dfs(result, new HashSet<>(), start);
        if (result.length() > 0) {
            return result.substring(0, result.length() - 2).trim();
        }
        return null;
    }

    private void dfs(StringBuilder builder, Set<String> visited, String node) {
        builder.append(node).append(", ");
        ArrayList<String> neighbours = findNeighbours(node);
        visited.add(node);
        for (int i = 0; i < neighbours.size(); i++) {
            String n = neighbours.get(i);
            if (n != null && !visited.contains(n)) {
                dfs(builder, visited, n);
            }
        }
    }

    public int connectedComponents() {
        DisjointSetForest deepForest = new DisjointSetForest(arr.length);

        for (int i = 0; i < arr.length; i++) {
            deepForest.makeSet(i);
        }

        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr.length; y++) {
                if (arr[x][y] == 1) {
                    if (deepForest.findSet(x) != deepForest.findSet(y)) {
                        deepForest.union(x, y);
                    }
                }
            }
        }
        return deepForest.countSets();
    }
}