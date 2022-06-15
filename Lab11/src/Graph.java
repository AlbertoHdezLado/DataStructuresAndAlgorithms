import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.ArrayList;

public class Graph {
    int arr[][];
    ArrayList<String> nodes = new ArrayList<>();

    public ArrayList<String> findNeighbours(String x)
    {
        int nodeIndex = -1;

        ArrayList<String> neighbours = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).equals(x))
            {
                nodeIndex = i;
                break;
            }
        }

        if (nodeIndex != -1)
        {
            for (int j = 0; j < arr[nodeIndex].length; j++)
            {
                if (arr[nodeIndex][j] == 1)
                {
                    neighbours.add(nodes.get(j));
                }
            }
        }
        return neighbours;
    }

    private Map<String, Integer> strToInt = new HashMap<>();
    private Map<Integer, String> intToStr = new HashMap<>();

    private int getIndex(String str)
    {
        return strToInt.get(str);
    }

    // The argument type depend on a selected collection in the Main class
    public Graph(SortedMap<String,Document> internet){
        int size = internet.size();
        arr = new int[size][size];
        int index = 0;
        for (Entry<String, Document> entry : internet.entrySet())
        {
            strToInt.put(entry.getKey(), index);
            intToStr.put(index, entry.getKey());
            index++;
        }
        for (Entry<String, Document> entry : internet.entrySet())
        {
            Document document = entry.getValue();
            for (Entry<String, Link> link : document.link.entrySet())
            {
                int x = getIndex(entry.getKey());
                int y = getIndex(link.getKey());
                arr[x][y] = link.getValue().weight;
            }
            nodes.add(entry.getKey());
        }
    }

    public String bfs(String start) {
        if (!strToInt.containsKey(start))
        {
            return null;
        }

        StringBuilder result = new StringBuilder();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty())
        {
            String element = queue.remove();
            result.append(element).append(", ");
            ArrayList<String> neighbours = findNeighbours(element);
            for (int i = 0; i < neighbours.size(); i++)
            {
                String n = neighbours.get(i);
                if (n != null && !visited.contains(n))
                {
                    queue.add(n);
                    visited.add(n);
                }
            }

        }
        if (result.length() > 0)
        {
            return result.substring(0, result.length() - 2).trim();
        }
        return null;
    }

    private void dfs(StringBuilder builder, Set<String> visited, String node)
    {
        builder.append(node).append(", ");
        ArrayList<String> neighbours = findNeighbours(node);
        visited.add(node);
        for (int i = 0; i < neighbours.size(); i++)
        {
            String n = neighbours.get(i);
            if (n != null && !visited.contains(n))
            {
                dfs(builder, visited, n);
            }
        }
    }


    public String dfs(String start) {
        if (!strToInt.containsKey(start))
        {
            return null;
        }
        StringBuilder result = new StringBuilder();
        dfs(result, new HashSet<>(), start);
        if (result.length() > 0)
        {
            return result.substring(0, result.length() - 2).trim();
        }
        return null;
    }

    public int connectedComponents() {
        DisjointSetForest deepForest = new DisjointSetForest(arr.length);
        for (int i = 0; i < arr.length; i++)
        {
            deepForest.makeSet(i);
        }
        for (int x = 0; x < arr.length; x++)
        {
            for (int y = 0; y < arr.length; y++)
            {
                if (arr[x][y] == 1)
                {
                    if (deepForest.findSet(x) != deepForest.findSet(y))
                    {
                        deepForest.union(x, y);
                    }
                }
            }
        }
        return deepForest.countSets();
    }

    public String DijkstraSSSP(String startVertexStr) {
        if (!strToInt.containsKey(startVertexStr))
        {
            return null;
        }

        int startVertex = strToInt.get(startVertexStr);
        int size = arr.length;

        boolean[] added = new boolean[size];
        int[] previous = new int[size];
        int[] shortest = new int[size];

        for (int vertexIndex = 0; vertexIndex < size; vertexIndex++)
        {
            added[vertexIndex] = false;
            shortest[vertexIndex] = Integer.MAX_VALUE;
        }

        shortest[startVertex] = 0;
        previous[startVertex] = -1;

        for (int i = 1; i < size; i++) //size-1 times
        {
            int nearest = -1;
            int shortestDist = Integer.MAX_VALUE; //define as infinite
            for (int vertexIndex = 0; vertexIndex < size; vertexIndex++)
            {
                if (!added[vertexIndex] && shortest[vertexIndex] < shortestDist) //if is not added and is shorter than shortestDist
                {
                    nearest = vertexIndex; //the nearest is this vertex
                    shortestDist = shortest[vertexIndex]; //shorterDist is the vertex shortest
                }
            }
            if (nearest == -1) //in case we don't found any shorter vertex
                continue;

            added[nearest] = true; //we add the shorter vertex

            for (int vertexIndex = 0; vertexIndex < size; vertexIndex++) //for each vertex
            {
                int edgeDist = arr[nearest][vertexIndex];
                if (edgeDist > 0 && ((shortestDist + edgeDist) < shortest[vertexIndex]))
                {
                    shortest[vertexIndex] = shortestDist + edgeDist;
                    previous[vertexIndex] = nearest;
                }
            }
        }

        return printResult(shortest, previous);
    }

    private String printResult(int[] distances, int[] parents)
    {
        int nVertices = distances.length;
        StringBuilder result = new StringBuilder();

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
        {
            int dist = distances[vertexIndex];
            String vertexValue = intToStr.get(vertexIndex);
            if (dist == Integer.MAX_VALUE)
            {
                result.append("no path to ").append(vertexValue).append('\n');
            }
            else
            {
                printVertexPath(vertexIndex, parents, result);
                result.setLength(result.length() - 2);
                result.append("=").append(dist);
                result.append('\n');
            }
        }
        return result.toString();
    }


    private void printVertexPath(int currentVertex, int[] parents, StringBuilder result)
    {

        if (currentVertex == -1)
        {
            return;
        }
        printVertexPath(parents[currentVertex], parents, result);
        result.append(intToStr.get(currentVertex)).append("->");
    }
}