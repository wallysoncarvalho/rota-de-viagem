package info.wallyson.domain;

import info.wallyson.core.domain.GetCostToNode;
import info.wallyson.core.domain.Node;
import info.wallyson.core.exceptions.NullNodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetCostToNodeTest {

  @Test
  @DisplayName("Should throw exception when destination node don't exists")
  void shouldThrowExceptionWhenDestinationNodeDontExists() {
    assertThrows(NullNodeException.class, () -> GetCostToNode.to(null));
  }

  @Test
  @DisplayName("Should get cost to node")
  void shouldGetCostToNode() {
    var nodeA = new Node("A");
    var nodeB = new Node("B");

    nodeA.setAdjacentNodes(nodeB, 2);
    nodeB.setParent(nodeA);
    nodeB.setCost(2);

    var shortestPath = GetCostToNode.to(nodeB);

    assertEquals("A - B > $2", shortestPath);
  }
}
