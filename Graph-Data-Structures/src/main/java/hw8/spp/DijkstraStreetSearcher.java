package hw8.spp;

import exceptions.InsertionException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.SparseGraph;
import hw8.graph.Vertex;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraStreetSearcher extends StreetSearcher {

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
  }

  @Override
  public void findShortestPath(String startName, String endName) {
    try {
      checkValidEndpoint(startName);
      checkValidEndpoint(endName);

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return;
    }

    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    double totalDist = -1;  // totalDist must be update below

    // Algorithm Setup: Set up Priority Queue
    PriorityQueue<Submission> priorityQueue = new PriorityQueue<>();
    // For each vertex, update the distance
    HashMap<Vertex<String>,Submission> distances = new HashMap<>();
    for (Vertex<String> curr : graph.vertices()) {
      Submission sub = new Submission(curr, MAX_DISTANCE);
      distances.put(curr,sub);
    }
    distances.get(start).distance = 0;
    priorityQueue.add(new Submission(start, 0)); //add source vertex to priority queue
    HashSet<Vertex<String>> exploredVertices = new HashSet<>();
    //repeat N times
    while (!priorityQueue.isEmpty()) {
      //let v be the unexplored vertex with the smallest distance
      Submission best = priorityQueue.poll();
      if (best.vertex.equals(end)) {
        break;
      }
      //"mark" vertex as explored
      exploredVertices.add(best.vertex);
      //for every unexplored neighbor u in v
      for (Edge<String> edge: graph.outgoing(best.vertex)) {
        Vertex<String> neighbor = graph.to(edge);
        if (!exploredVertices.contains(neighbor)) {
          //d = distance[v] + weight[u,v]
          double d = best.distance + (double)graph.label(edge);
          double neighborDist = (distances.get(neighbor)).distance;
          // if d < distance[u]
          if (d < neighborDist) {
            (distances.get(neighbor)).distance = d;
            graph.label(neighbor,edge);
            priorityQueue.add(distances.get(neighbor));
          }
        }
      }
      totalDist = (distances.get(end)).distance;
    }
    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }

  private final class Submission implements Comparable<Submission> {
    private double distance;
    private Vertex<String> vertex;

    /**
     * Create a submission for the given vertex.
     * @param vertex the current intersection for the path.
     */
    Submission(Vertex<String> vertex, double dist) {
      this.vertex = vertex;
      this.distance = dist;
    }

    @Override
    public int compareTo(Submission o) {
      return (int) (this.distance - o.distance);
    }
  }
}


