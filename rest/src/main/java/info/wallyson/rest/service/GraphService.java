package info.wallyson.rest.service;

import info.wallyson.core.domain.Dijkstra;
import info.wallyson.core.domain.GetCostToNode;
import info.wallyson.core.domain.Connection;
import info.wallyson.core.ports.GetGraph;
import info.wallyson.core.ports.GraphConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GraphService {
  private final String filePath;
  private final GetGraph getGraph;
  private final GraphConnection graphConnection;

  public GraphService(
      @Value("${graph.path}") String filePath, GetGraph getGraph, GraphConnection graphConnection) {
    this.filePath = filePath;
    this.getGraph = getGraph;
    this.graphConnection = graphConnection;
  }

  public String findShortestPath(String source, String destination) {
    var graph = getGraph.get(filePath);
    var sourceNode = graph.get(source);
    var destinationNode = graph.get(destination);

    Dijkstra.findShortestPathToNodes(sourceNode);

    return GetCostToNode.to(destinationNode);
  }

  public boolean addConnectionToGraph(Connection connection) {
    return graphConnection.add(filePath, connection);
  }
}
