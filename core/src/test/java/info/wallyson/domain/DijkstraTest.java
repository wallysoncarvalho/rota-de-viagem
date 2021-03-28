package info.wallyson.domain;

import info.wallyson.core.domain.Dijkstra;
import info.wallyson.core.domain.GetCostToNode;
import info.wallyson.core.exceptions.NullNodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DijkstraTest {

  @Test
  @DisplayName("Should get shortest path from node A to F")
  void shouldGetShortestPathFromAtoF() {
    var graph = GraphCreator.getGraph();
    var sourceNode = graph.get("A");
    var destinationNode = graph.get("F");

    Dijkstra.findShortestPathToNodes(sourceNode);

    var shortestPath = GetCostToNode.to(destinationNode);

    assertEquals("A - B - D - F > $23", shortestPath);
  }

  @Test
  @DisplayName("Should return cost $0 to same node")
  void shouldReturnCostZeroToSameNode() {
    var graph = GraphCreator.getGraph();
    var sourceNode = graph.get("A");
    var destinationNode = graph.get("A");

    Dijkstra.findShortestPathToNodes(sourceNode);

    var shortestPath = GetCostToNode.to(destinationNode);

    assertEquals("A > $0", shortestPath);
  }

  @Test
  @DisplayName("Should throw exception when source node don't exists")
  void shouldThrowExceptionWhenSourceNodeDontExists() {
    var graph = GraphCreator.getGraph();
    var sourceNode = graph.get("Z");

    assertThrows(NullNodeException.class, () -> Dijkstra.findShortestPathToNodes(sourceNode));
  }


}
