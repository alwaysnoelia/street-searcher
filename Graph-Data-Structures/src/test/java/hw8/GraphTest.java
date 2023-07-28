package hw8;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.SparseGraph;
import hw8.graph.Vertex;
import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {
    assertEquals(1,1,"true");
    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }

  @Test
  @DisplayName("can remove a vertex after insert")
  public void canRemoveVertexAfterInsert() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Vertex<String> v3 = graph.insert("3");
    Vertex<String> v4 = graph.insert("4");
    Vertex<String> v5 = graph.insert("5");
    String data = graph.remove(v2);
    assertEquals("2", data);
  }

  @Test
  @DisplayName("can remove an edge after insert")
  public void canRemoveEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Vertex<String> v3 = graph.insert("3");
    Vertex<String> v4 = graph.insert("4");
    Vertex<String> v5 = graph.insert("5");
    Edge<String> edge1 = graph.insert(v2,v1,"v1-v2");
    String data = graph.remove(edge1);
    assertEquals("v1-v2", data);
  }

  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {

    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
    String data = graph.remove(e1);
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenfirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(V, null, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenSecondVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(v, null, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("duplicate vertices throw insertion error")
  public void duplicateVerticesFail() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("null vertex throws insertion error")
  public void nullVertexFail() {
    try {
      Vertex<String> v1 = graph.insert(null);
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("edge - vertices don't exist in the graph")
  public void insertEdgeVerticesDontExist() {
    try {
      Graph<String, String> second = new SparseGraph<>();
      Vertex<String> v1 = second.insert("v1");
      Vertex<String> v2 = second.insert("v2");
      Edge<String> edge = graph.insert(v1,v2,"v1-v2");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("self loop for an edge fails")
  public void insertEdgeSelfLoop() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Edge<String> edge = graph.insert(v1,v1,"v1-v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("duplicate edge fails")
  public void insertDuplicateEdge() {
    try {
      Vertex<String> v1 = graph.insert("1");
      Vertex<String> v2 = graph.insert("2");
      Edge<String> edge = graph.insert(v1,v2,"v1-v2");
      Edge<String> duplicate = graph.insert(v1,v2,"v1-v2");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("removing a vertex with incident edges")
  public void removeVertexWithIncidentEdges() {
    try {
      Vertex<String> v1 = graph.insert("1");
      Vertex<String> v2 = graph.insert("2");
      Edge<String> edge = graph.insert(v1,v2,"v1-v2");
      String data = graph.remove(v2);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  @DisplayName("removing all the vertices is empty graph")
  public void removeVerticesResultsEmptyGraph() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    String data1 = graph.remove(v1);
    String data2 = graph.remove(v2);
    int counter = 0;
    for(Vertex<String> item : graph.vertices()) {
      // System.out.println(item.get());
      counter++;
    }
    assertEquals(0,counter);
  }

  @Test
  @DisplayName("Removing an edge which doesn't exist")
  public void removeEdgeWhichDoesntExist() {
    try {
      Vertex<String> v1 = graph.insert("1");
      Vertex<String> v2 = graph.insert("2");
      Edge<String> edge = graph.insert(v1,v2,"v1-v2");
      String data = graph.remove(edge);
      String fail = graph.remove(edge); //removing it again - error
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }

  }

  @Test
  @DisplayName("Removing a vertex which doesn't exist")
  public void removeVertexWhichDoesntExist() {
    try {
      Vertex<String> v1 = graph.insert("1");
      Vertex<String> v2 = graph.insert("2");
      String data = graph.remove(v2);
      String fail = graph.remove(v2); //removing it again - error
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }

  }

  @Test
  @DisplayName("returning from endpoint of an edge")
  public void getFromVertexInEdge() {
      Vertex<String> v1 = graph.insert("1");
      Vertex<String> v2 = graph.insert("2");
      Edge<String> edge = graph.insert(v1,v2,"v1-v2");
      Vertex<String> from = graph.from(edge);
      assertEquals(v1.get(),from.get());
  }

  @Test
  @DisplayName("returning To endpoint of an edge")
  public void getToVertexInEdge() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Edge<String> edge = graph.insert(v1,v2,"v1-v2");
    Vertex<String> to = graph.to(edge);
    assertEquals(v2.get(),to.get());
  }

  @Test
  @DisplayName("returning To endpoint of an edge in the wrong graph")
  public void getEndpointDoesntExist() {
    try {
      Graph<String, String> second = new SparseGraph<>();
      Vertex<String> v1 = second.insert("v1");
      Vertex<String> v2 = second.insert("v2");
      Edge<String> edge = second.insert(v1,v2,"v1-v2");
      Vertex<String> fail = graph.to(edge);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }


  @Test
  @DisplayName("iterating over vertices")
  public void vertexIterator() {
    String[] arr = {"4", "5","1", "2", "3"};
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Vertex<String> v3 = graph.insert("3");
    Vertex<String> v4 = graph.insert("4");
    Vertex<String> v5 = graph.insert("5");
    int i = 0;
    for(Vertex<String> item : graph.vertices()) {
      //System.out.println(item.get());
      i++;
    }
    assertEquals(5,i);
  }

  @Test
  @DisplayName("successfully add a label to a vertex")
  public void addLabelToVertex() {
    Vertex<String> v1 = graph.insert("1");
    graph.label(v1, "label for vertex");
    String label = (String) graph.label(v1);
    assertEquals("label for vertex", label);

  }

  @Test
  @DisplayName("attempt to label an vertex not in graph")
  public void addLabelToInvalidVertex() {
    try {
      Graph<String, String> second = new SparseGraph<>();
      Vertex<String> v1 = second.insert("1");
      graph.label(v1, "label for vertex");
      fail("expected exception was not thrown");
    } catch (PositionException ignored) {
      return;
    }
  }

  @Test
  @DisplayName("add a label to an edge")
  public void addLabelToEdge() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Edge<String> edge = graph.insert(v1,v2,"v1-v2");
    graph.label(edge, "label for edge");
    String label = (String) graph.label(edge);
    assertEquals("label for edge", label);
  }

  @Test
  @DisplayName("attempt to label an edge not in graph")
  public void addLabelToInvalidEdge() {
    try {
      Graph<String, String> second = new SparseGraph<>();
      Vertex<String> v1 = second.insert("v1");
      Vertex<String> v2 = second.insert("v2");
      Edge<String> edge = second.insert(v1,v2,"v1-v2");
      graph.label(edge, "label for vertex");
      fail("expected exception was not thrown");
    } catch (PositionException ignored) {
      return;
    }
  }

  @Test
  @DisplayName("update a vertex label")
  public void updateVertexLabel() {
      Vertex<String> v1 = graph.insert("1");
      graph.label(v1, "label for vertex");
      String label = (String) graph.label(v1);
      assertEquals("label for vertex", label);
      graph.label(v1, "UPDATED!");
      String label2 = (String) graph.label(v1);
      assertEquals("UPDATED!", label2);
  }

  @Test
  @DisplayName("add a label to an edge")
  public void updateEdgeLabel() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Edge<String> edge = graph.insert(v1,v2,"v1-v2");
    graph.label(edge, "label for edge");
    String label = (String) graph.label(edge);
    assertEquals("label for edge", label);
    graph.label(edge, "Updated!");
    String label2 = (String) graph.label(edge);
    assertEquals("Updated!", label2);
  }

  @Test
  @DisplayName("Clearing All Vertex Labels")
  public void clearingAllVertexLabels() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Vertex<String> v3 = graph.insert("3");
    Vertex<String> v4 = graph.insert("4");
    Vertex<String> v5 = graph.insert("5");
    graph.label(v1, "label for");
    graph.label(v2, "label for");
    graph.label(v3, "label for");
    graph.label(v4, "label for");
    graph.label(v5, "label for");
    graph.clearLabels();
    for(Vertex<String> item : graph.vertices()) {
      // System.out.println(item.get());
      assertNull(graph.label(item));
    }
  }

  @Test
  @DisplayName("Clearing All Edge Labels")
  public void clearingAllEdgeLabels() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Vertex<String> v3 = graph.insert("3");
    Vertex<String> v4 = graph.insert("4");
    Vertex<String> v5 = graph.insert("5");
    Edge<String> edge1 = graph.insert(v1,v2,"v1-v2");
    Edge<String> edge2 = graph.insert(v2,v3,"v2-v3");
    Edge<String> edge3 = graph.insert(v3,v4,"v3-v4");
    Edge<String> edge4 = graph.insert(v4,v5,"v4-v5");
    graph.label(edge1, "label for");
    graph.label(edge2, "label for");
    graph.label(edge3, "label for");
    graph.label(edge4, "label for");
    graph.clearLabels();
    for(Edge<String> item : graph.edges()) {
      // System.out.println(item.get());
      assertNull(graph.label(item));
    }
  }

  @Test
  @DisplayName("iterating through the outgoing of v1")
  public void iteratingOutgoing() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Vertex<String> v3 = graph.insert("3");
    Vertex<String> v4 = graph.insert("4");
    Vertex<String> v5 = graph.insert("5");
    Edge<String> edge1 = graph.insert(v1,v2,"v1-v2");
    Edge<String> edge2 = graph.insert(v1,v3,"v1-v2");
    Edge<String> edge3 = graph.insert(v1,v4,"v1-v2");
    Edge<String> edge4 = graph.insert(v1,v5,"v1-v2");
    int counter = 0;
    for(Edge<String> item : graph.outgoing(v1)) {
      counter++;
    }
    assertEquals(4,counter);
  }

  @Test
  @DisplayName("iterate through the incoming for v1")
  public void iteratingIncoming() {
    Vertex<String> v1 = graph.insert("1");
    Vertex<String> v2 = graph.insert("2");
    Vertex<String> v3 = graph.insert("3");
    Vertex<String> v4 = graph.insert("4");
    Vertex<String> v5 = graph.insert("5");
    Edge<String> edge1 = graph.insert(v2,v1,"v1-v2");
    Edge<String> edge2 = graph.insert(v3,v1,"v1-v2");
    Edge<String> edge3 = graph.insert(v4,v1,"v1-v2");
    Edge<String> edge4 = graph.insert(v5,v1,"v1-v2");
    int counter = 0;
    for(Edge<String> item : graph.incoming(v1)) {
      counter++;
    }
    assertEquals(4,counter);
  }



}
