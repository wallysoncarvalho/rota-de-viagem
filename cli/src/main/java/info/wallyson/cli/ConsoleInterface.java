package info.wallyson.cli;

import info.wallyson.core.domain.Dijkstra;
import info.wallyson.core.domain.GetCostToNode;
import info.wallyson.persistence.GetGraphAdapter;

public class ConsoleInterface {
  private static final String STOP_INPUT = "exit";

  public static void start(String filePath) {
    var getGraphService = new GetGraphAdapter();
    var input = "";

    while (!input.equals(STOP_INPUT)) {
      input = System.console().readLine("please enter the route: ");

      var graph = getGraphService.get(filePath);
      var nodesName = input.split("-");

      if (nodesName.length != 2) continue;

      var source = nodesName[0].trim();
      var destination = nodesName[1].trim();

      if (graph.containsKey(source) && graph.containsKey(destination)) {
        Dijkstra.findShortestPathToNodes(graph.get(source));

        System.out.println("best route: " + GetCostToNode.to(graph.get(destination)));
      }
    }
  }
}
