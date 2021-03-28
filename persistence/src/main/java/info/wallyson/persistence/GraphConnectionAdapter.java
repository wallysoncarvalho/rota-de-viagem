package info.wallyson.persistence;

import info.wallyson.core.domain.Connection;
import info.wallyson.persistence.exceptions.GraphFileReadException;
import info.wallyson.core.ports.GraphConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GraphConnectionAdapter implements GraphConnection {
  private final String LINE_SEPARATOR = System.getProperty("line.separator");

  @Override
  public boolean add(String graphIdentifier, Connection connection) {
    GraphFileValidator.validateFile(graphIdentifier);

    var path = Paths.get(graphIdentifier);
    var connectionStr = connection.toString();

    try {
      if (graphHasConnection(graphIdentifier, connection) || connection.getCost() <= 0)
        return false;

      var newLineBytes =
          String.format("%s%s", LINE_SEPARATOR, connectionStr).getBytes(StandardCharsets.UTF_8);

      var countLines = Files.lines(Paths.get(graphIdentifier)).count();

      if (countLines == 0) {
        newLineBytes = connectionStr.getBytes(StandardCharsets.UTF_8);
      }

      Files.write(path, newLineBytes, StandardOpenOption.APPEND);

    } catch (IOException e) {
      e.printStackTrace();
      throw new GraphFileReadException();
    }
    return true;
  }

  private boolean graphHasConnection(String graphIdentifier, Connection connection)
      throws IOException {
    return Files.lines(Paths.get(graphIdentifier))
        .anyMatch(
            line -> {
              var lineArr = line.split(",");
              return lineArr[0].trim().equals(connection.getSource())
                  && lineArr[1].trim().equals(connection.getDestination());
            });
  }
}
