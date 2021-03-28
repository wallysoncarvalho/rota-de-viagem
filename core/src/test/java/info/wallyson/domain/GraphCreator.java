package info.wallyson.domain;

import info.wallyson.core.domain.Node;

import java.util.HashMap;

public class GraphCreator {

  public static HashMap<String, Node> getGraph() {
    var graph = new HashMap<String, Node>();

    graph.put("A", new Node("A"));
    graph.put("B", new Node("B"));
    graph.put("C", new Node("C"));
    graph.put("D", new Node("D"));
    graph.put("E", new Node("E"));
    graph.put("F", new Node("F"));

    graph.get("A").setAdjacentNodes(graph.get("B"), 10);
    graph.get("A").setAdjacentNodes(graph.get("C"), 15);

    graph.get("B").setAdjacentNodes(graph.get("F"), 15);
    graph.get("B").setAdjacentNodes(graph.get("D"), 12);

    graph.get("C").setAdjacentNodes(graph.get("E"), 10);

    graph.get("D").setAdjacentNodes(graph.get("F"), 1);
    graph.get("D").setAdjacentNodes(graph.get("E"), 2);

    graph.get("F").setAdjacentNodes(graph.get("E"), 5);

    return graph;
  }
}
