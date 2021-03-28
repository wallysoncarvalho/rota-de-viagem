package info.wallyson.persistence;

import info.wallyson.core.domain.Node;
import info.wallyson.persistence.exceptions.GraphFileDontExistsException;
import info.wallyson.persistence.exceptions.GraphFileReadException;
import info.wallyson.core.ports.GetGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class GetGraphAdapter implements GetGraph {

  @Override
  public HashMap<String, Node> get(String graphIdentifier) {
    GraphFileValidator.validateFile(graphIdentifier);

    var graph = new HashMap<String, Node>();

    try {
      Files.lines(Paths.get(graphIdentifier)).forEach(line -> addNodeToGraph(line, graph));
    } catch (IOException e) {
      e.printStackTrace();
      throw new GraphFileReadException();
    }

    return graph;
  }

  private void addNodeToGraph(String line, HashMap<String, Node> graph) {
    var connection = line.split(",");

    assert connection.length >= 3 : String.format("Invalid connection entry '%s'", line);

    var source = connection[0];
    var destination = connection[1];
    var cost = Integer.parseInt(connection[2]);

    assert cost >= 0 : "Cost value cannot be negative";

    var sourceNode = getNodeOrAddIfNotExists(source, graph);
    var destinationNode = getNodeOrAddIfNotExists(destination, graph);

    sourceNode.setAdjacentNodes(destinationNode, cost);
  }

  private Node getNodeOrAddIfNotExists(String key, HashMap<String, Node> nodes) {
    if (nodes.containsKey(key)) return nodes.get(key);
    var node = new Node(key);
    nodes.put(key, node);
    return node;
  }
}
