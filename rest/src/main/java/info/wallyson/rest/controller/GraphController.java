package info.wallyson.rest.controller;

import info.wallyson.rest.controller.dto.ConnectionRequest;
import info.wallyson.rest.controller.dto.ShortestPathResponse;
import info.wallyson.rest.service.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "api/graph")
public class GraphController {
  private final GraphService graphService;

  public GraphController(GraphService graphService) {
    this.graphService = graphService;
  }

  @GetMapping("dijkstra")
  public ResponseEntity<ShortestPathResponse> findShortestPath(
      @RequestParam @NotBlank String source, @RequestParam @NotBlank String destination) {

    var shortPathStr = graphService.findShortestPath(source, destination);

    return ResponseEntity.ok(new ShortestPathResponse(shortPathStr));
  }

  @PostMapping
  public ResponseEntity<?> registerConnection(@RequestBody ConnectionRequest connection) {
    var isConnectionAdded = graphService.addConnectionToGraph(connection);
    var status = isConnectionAdded ? 200 : 400;
    return ResponseEntity.status(status).build();
  }
}
