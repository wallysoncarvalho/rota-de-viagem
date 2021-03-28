package info.wallyson.rest.controller;

import info.wallyson.core.exceptions.NullNodeException;
import info.wallyson.rest.service.GraphService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GraphController.class)
public class GraphControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private GraphService graphService;

  @Test
  @DisplayName("Should find shortest path between two nodes")
  void shouldFindShortestPath() throws Exception {
    var sourceNode = "A";
    var destinationNode = "F";

    when(graphService.findShortestPath(sourceNode, destinationNode))
        .thenReturn("A - B - D - F > $23");

    this.mockMvc
        .perform(
            get("/api/graph/dijkstra")
                .param("source", sourceNode)
                .param("destination", destinationNode)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.path").value("A - B - D - F"))
        .andExpect(jsonPath("$.cost").value("$23"))
        .andReturn();

    verify(graphService, times(1)).findShortestPath(sourceNode, destinationNode);
  }

  @Test
  @DisplayName("Should return bad request when no source param is provided")
  void shouldReturnBadRequestWhenNoSourceParamIsProvided() throws Exception {
    this.mockMvc
        .perform(get("/api/graph/dijkstra").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  @DisplayName("Should return bad request when no destination param is provided")
  void shouldReturnBadRequestWhenNoDestinationParamIsProvided() throws Exception {
    this.mockMvc
        .perform(get("/api/graph/dijkstra").param("source", "A").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  @DisplayName("Should return bad request when nodes don't exist")
  void shouldReturnBadRequestWhenNodesDontExist() throws Exception {
    var sourceNode = "Z";
    var destinationNode = "G";

    when(graphService.findShortestPath(sourceNode, destinationNode))
        .thenThrow(NullNodeException.class);

    this.mockMvc
        .perform(
            get("/api/graph/dijkstra")
                .param("source", sourceNode)
                .param("destination", destinationNode)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andReturn();

    verify(graphService, times(1)).findShortestPath(sourceNode, destinationNode);
  }
}
