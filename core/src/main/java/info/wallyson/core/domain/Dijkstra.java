package info.wallyson.core.domain;

import info.wallyson.core.exceptions.NullNodeException;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Dijkstra {

  public static void findShortestPathToNodes(Node source) {
    if(Objects.isNull(source)) throw new NullNodeException();

    var unvisited = new HashSet<Node>();
    var visited = new HashSet<Node>();

    source.setCost(0);
    unvisited.add(source);

    while (unvisited.size() != 0) {
      var closestNode = getNodeWithLowestCost(unvisited);

      unvisited.remove(closestNode);

      for (var nodeIntegerEntry : closestNode.getAdjacentNodes().entrySet()) {
        var neighborNode = nodeIntegerEntry.getKey();

        if (!visited.contains(neighborNode)) {

          var currentCost = closestNode.getCost() + nodeIntegerEntry.getValue();

          if (currentCost < neighborNode.getCost()) {
            neighborNode.setCost(currentCost);
            neighborNode.setParent(closestNode);
          }

          unvisited.add(neighborNode);
        }
      }

      visited.add(closestNode);
    }
  }

  private static Node getNodeWithLowestCost(Set<Node> nodeSet) {
    return nodeSet.stream().min(Comparator.comparing(Node::getCost)).orElseThrow();
  }
}
