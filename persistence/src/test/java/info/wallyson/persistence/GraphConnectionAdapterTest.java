package info.wallyson.persistence;

import info.wallyson.core.domain.Connection;
import info.wallyson.core.ports.GraphConnection;
import info.wallyson.persistence.exceptions.GraphFileDontExistsException;
import info.wallyson.persistence.exceptions.InvalidGraphFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphConnectionAdapterTest {
  private final GraphConnection graphConnection = new GraphConnectionAdapter();

  @Test
  @DisplayName("Should add connections to file")
  void shouldAddConnectionsToFile(@TempDir Path tempDir) throws IOException {
    Path graphFilePath = tempDir.resolve("graph.csv");
    Files.createFile(graphFilePath);

    var aTob = new Connection("A", "B", 10);
    var aToc = new Connection("A", "C", 2);
    var cTod = new Connection("C", "D", 190);

    List<String> connectionsStr = getConnectionStrList(aTob, aToc, cTod);

    assertAll(
        () -> assertTrue(graphConnection.add(graphFilePath.toString(), aTob)),
        () -> assertTrue(graphConnection.add(graphFilePath.toString(), aToc)),
        () -> assertTrue(graphConnection.add(graphFilePath.toString(), cTod)));

    assertAll(
        () -> assertTrue(Files.exists(graphFilePath), "File should exist"),
        () -> assertLinesMatch(connectionsStr, Files.readAllLines(graphFilePath)));
  }

  @Test
  @DisplayName("Should not add existing connection")
  void shouldNotAddExistingConnection(@TempDir Path tempDir) throws IOException {
    Path graphFilePath = tempDir.resolve("graph.csv");
    Files.createFile(graphFilePath);

    var aTob = new Connection("A", "B", 10);
    var aToc = new Connection("A", "C", 2);
    var cTod = new Connection("C", "D", 190);

    List<String> connectionsStr = getConnectionStrList(aTob, aToc, cTod, aTob);

    assertAll(
        () -> assertTrue(graphConnection.add(graphFilePath.toString(), aTob)),
        () -> assertTrue(graphConnection.add(graphFilePath.toString(), aToc)),
        () -> assertTrue(graphConnection.add(graphFilePath.toString(), cTod)),
        () -> assertFalse(graphConnection.add(graphFilePath.toString(), aTob)));

    assertAll(
        () -> assertTrue(Files.exists(graphFilePath), "File should exist"),
        () -> assertNotEquals(connectionsStr.size(), Files.readAllLines(graphFilePath).size()));
  }

  @Test
  @DisplayName("Should fail to add connection to file that do not exists")
  void shouldFailToAddConnectionToFileThatDoNotExists() {
    var invalidFileDir = "wrong_graph.csv";

    var aTob = new Connection("A", "B", 10);

    assertThrows(
        GraphFileDontExistsException.class, () -> graphConnection.add(invalidFileDir, aTob));
  }

  @Test
  @DisplayName("Should fail to add connection to file with invalid content")
  void shouldFailToAddConnectionToFileWithInvalidContent(@TempDir Path tempDir) throws IOException {
    Path graphFilePath = tempDir.resolve("graph.csv");

    Files.createFile(graphFilePath);
    Files.write(graphFilePath, "random_text".getBytes(StandardCharsets.UTF_8));

    var aTob = new Connection("A", "B", 10);

    assertThrows(InvalidGraphFile.class, () -> graphConnection.add(graphFilePath.toString(), aTob));
  }

  private List<String> getConnectionStrList(Connection... connections) {
    var connList = new ArrayList<String>(connections.length);
    Arrays.stream(connections).forEach(c -> connList.add(c.toString()));
    return connList;
  }
}
