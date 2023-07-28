package hw8.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  HashSet<VertexNode<V>> vertices; // Vertex Collection
  HashSet<EdgeNode<E>> edges; // Edge Collection

  /**
   *  Constructs an instance of Sparse Graph.
   */
  public SparseGraph() {
    vertices = new HashSet<>();
    edges = new HashSet<>();
  }

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts and edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    //create vertex with data
    VertexNode<V> vertex = new VertexNode<>(v);
    vertex.owner = this;
    //If v is null or already in this Graph
    if (v == null || vertices.contains(vertex)) { //TODO: complexity of this function
      throw new InsertionException();
    }
    //add vertex to the collection
    vertices.add(vertex);
    return vertex; //double check casting
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {
    if (from == null || to == null) {
      throw new PositionException();
    }
    //convert vertex into vertexnode
    VertexNode<V> fromV = convert(from);
    VertexNode<V> toV = convert(to);
    //create new edge
    EdgeNode<E> edge = new EdgeNode<>(fromV, toV, e);
    edge.owner = this;
    // ERROR HANDLING --------------------------
    if (!vertices.contains(fromV) || !vertices.contains(toV)) { //If the vertices don't exist in graph
      throw new PositionException();
    }
    if (fromV.equals(toV) || edges.contains(edge)) { // self loop or duplicate edge
      throw new InsertionException();
    }
    //---------------------------------
    //add edge to the collection
    edges.add(edge);
    //add incoming edge to TO vertex and outgoing edge to FROM vertex
    edge.to.incoming.add(edge);
    edge.from.outgoing.add(edge);
    //finish - return edge
    return edge;
  }

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    //convert vertex to vertexNode
    VertexNode<V> vertex = convert(v);
    //removal case: does vertex still have incident edges?
    if (!(vertex.outgoing.isEmpty() && vertex.incoming.isEmpty())) { //if one/both are not empty
      throw new RemovalException();
    }
    //store data from vertex
    V data = vertex.get();
    //remove vertex from the collection
    if (!vertices.remove(vertex)) {
      throw new PositionException();
    }
    //return the vertex data
    return data;
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    EdgeNode<E> edge = convert(e);
    //remove edge from the vertices lists
    edge.from.outgoing.remove(edge);
    edge.to.incoming.remove(edge);
    //store data from edge
    E data = edge.get();
    //remove edge from the collection
    if (!edges.remove(edge)) {
      throw new PositionException();
    }
    //return the edge data
    return data;
  }

  @Override
  public Iterable<Vertex<V>> vertices() {
    //create an unmodifiable version
    return Collections.unmodifiableCollection(vertices);
  }

  @Override
  public Iterable<Edge<E>> edges() {
    //create an unmodifiable version
    return Collections.unmodifiableCollection(edges);
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    //create an unmodifiable version
    VertexNode<V> vertex = convert(v);
    return Collections.unmodifiableCollection(vertex.outgoing);
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    //create an unmodifiable version
    VertexNode<V> vertex = convert(v);
    return Collections.unmodifiableCollection(vertex.incoming);
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    // convert edge
    EdgeNode<E> edge = convert(e);
    return edge.from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    // convert edge
    EdgeNode<E> edge = convert(e);
    return edge.to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    VertexNode<V> vertex = convert(v);
    vertex.label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    EdgeNode<E> edge = convert(e);
    edge.label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    VertexNode<V> vertex = convert(v);
    return vertex.label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    EdgeNode<E> edge = convert(e);
    return edge.label;
  }

  @Override
  public void clearLabels() {
    // Can be linear!
    //possible strategy: iterate through each vertex and edge collection and clear
    for (Vertex<V> item : vertices()) {
      VertexNode<V> vertex = convert(item);
      vertex.label = null;
    }
    for (Edge<E> item : edges()) {
      EdgeNode<E> edge = convert(item);
      edge.label = null;
    }

  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }

  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;
    ArrayList<EdgeNode<E>> incoming;
    ArrayList<EdgeNode<E>> outgoing;

    VertexNode(V v) {
      this.data = v;
      this.label = null;
      incoming = new ArrayList<>();
      outgoing = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
      // If the object is compared with itself then return true
      if ((o == null) || (o.getClass() != this.getClass())) {
        return false;
      }

      if (o == this) {
        return true;
      }
      // typecast o to vertex node
      VertexNode<V> v = (VertexNode<V>) o;

      // Compare the data members and return accordingly
      //Note: check owners!!!!
      return (v.data).equals(this.data); //assuming
    }

    @Override
    public int hashCode() {
      //return data.hashCode();
      return Objects.hash(data, owner);
    }

    @Override
    public V get() {
      return this.data;
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    @Override
    public boolean equals(Object o) {
      // If the object is compared with itself then return true
      if ((o == null) || (o.getClass() != this.getClass())) {
        return false;
      }

      if (o == this) {
        return true;
      }

      // typecast o to vertex node
      EdgeNode<E> e = (EdgeNode<E>) o;

      // Compare the data members and return accordingly
      return e.to.equals(this.to) && e.from.equals(this.from);
    }

    @Override
    public int hashCode() {
      return Objects.hash(data, owner, from.data, to.data);
    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
