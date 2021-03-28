package info.wallyson.core.domain;

import java.util.*;

public class Node {
  private final String name;
  private int cost = Integer.MAX_VALUE;
  private final Map<Node, Integer> adjacentNodes = new HashMap<>();
  private Node parent;

  public Node(String name) {
    this.name = name;
  }

  public void setAdjacentNodes(Node neighbor, int cost) {
    adjacentNodes.put(neighbor, cost);
  }

  public Map<Node, Integer> getAdjacentNodes() {
    return adjacentNodes;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public int getCost() {
    return cost;
  }

  public String getName() {
    return name;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node node = (Node) o;
    return Objects.equals(name, node.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
