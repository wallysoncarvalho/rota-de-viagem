package info.wallyson.persistence;

import info.wallyson.core.ports.GetGraph;
import info.wallyson.persistence.exceptions.GraphFileDontExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetGraphAdapterTest {
  private final GetGraph getGraph = new GetGraphAdapter();
  private String testGraphFileDir;

  @BeforeEach
  void setUp() {
    var fileName = "test_graph_file.csv";
    this.testGraphFileDir =
        Paths.get("src", "test", "resources", fileName).toFile().getAbsolutePath();
  }

  @Test
  @DisplayName("Should get graph structure")
  void shouldGetGraphStructure() {
    var graph = getGraph.get(testGraphFileDir);
    var graphNodes = graph.entrySet();
    assertEquals(6, graphNodes.size());
  }

  @Test
  @DisplayName("Should fail to get graph when file doesn't exist")
  void shouldFailToGetGraphWhenFileDontExist() {
    assertThrows(GraphFileDontExistsException.class, () -> getGraph.get("invalid_graph_file"));
  }
}
