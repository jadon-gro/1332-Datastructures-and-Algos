import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 * Your implementation of various graph algorithms.
 *
 * @author Jadon Grossberg
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start vertex is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        List<Vertex<T>> visitedList = new ArrayList<Vertex<T>>();
        return dfsRecursive(graph, start, visitedList);


    }
    /**
     * Helper method for DFS recursive call
     * @param <T>          The generic typing of the data
     * @param graph        The graph to be traversed
     * @param current      The current vertex in the traversal
     * @param visitedList  The running list of verticies visited
     * @return             The list o verticies in visited order
     */
    private static <T> List<Vertex<T>> dfsRecursive(Graph<T> graph, Vertex<T> current, List<Vertex<T>> visitedList) {
        visitedList.add(current);
        for (VertexDistance<T> adj : graph.getAdjList().get(current)) {
            if (!visitedList.contains(adj.getVertex())) {
                dfsRecursive(graph, adj.getVertex(), visitedList);
            }
        }
        return visitedList;
            
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start vertex is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }                                    
        List<Vertex<T>> visited = new ArrayList<Vertex<T>>();
        Queue<VertexDistance<T>> priorityQueue = new PriorityQueue<VertexDistance<T>>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<Vertex<T>, Integer>();
        Integer inf = Integer.MAX_VALUE;
        for (Vertex<T> vertex : graph.getVertices()) {
            distanceMap.put(vertex, inf);
        }
        priorityQueue.add(new VertexDistance<T>(start, 0));
        while (!priorityQueue.isEmpty() && visited.size() != graph.getVertices().size()) {
            VertexDistance<T> temp = priorityQueue.remove();
            if (!visited.contains(temp.getVertex())) {
                visited.add(temp.getVertex());
                distanceMap.put(temp.getVertex(), temp.getDistance());
                for (VertexDistance<T> vd : graph.getAdjList().get(temp.getVertex())) {
                    priorityQueue.add(new VertexDistance<T>(vd.getVertex(), vd.getDistance() + temp.getDistance()));
                }
            }
        }
        return distanceMap;

    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use java.util.PriorityQueue, java.util.Set, and any
     * class that implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the adjacency
     * list from graph. DO NOT create new instances of Map for this method
     * (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start vertex is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        List<Vertex<T>> visited = new ArrayList<Vertex<T>>();
        Set<Edge<T>> mst = new HashSet<Edge<T>>();
        Queue<Edge<T>> priorityQueue = new PriorityQueue<Edge<T>>();
        for (Edge<T> edge : graph.getEdges()) {
            if (edge.getU().equals(start)) {
                priorityQueue.add(edge);
            }
        }
        visited.add(start);
        while (!priorityQueue.isEmpty() && visited.size() != graph.getVertices().size()) {
            Edge<T> temp = priorityQueue.remove();
            if (!visited.contains(temp.getV())) {
                visited.add(temp.getV());
                mst.add(temp);
                mst.add(new Edge<T>(temp.getV(), temp.getU(), temp.getWeight()));
                for (Edge<T> edge : graph.getEdges()) {
                    if (edge.getU().equals(temp.getV()) && !visited.contains(edge.getV())) {    
                        priorityQueue.add(edge);
                    }
                }
            }
        }
        if (mst.size() / 2 != graph.getVertices().size() - 1) {
            return null;
        }
        return mst;
    }
}
