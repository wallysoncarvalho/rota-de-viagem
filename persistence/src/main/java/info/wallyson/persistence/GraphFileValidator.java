package info.wallyson.persistence;

import info.wallyson.persistence.exceptions.GraphFileDontExistsException;
import info.wallyson.persistence.exceptions.GraphFileReadException;
import info.wallyson.persistence.exceptions.InvalidGraphFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

abstract class GraphFileValidator {

  public static void validateFile(String filePath) {
    if (Objects.isNull(filePath) || !Files.exists(Paths.get(filePath)))
      throw new GraphFileDontExistsException(filePath);

    try {
      var fileLines = Files.lines(Paths.get(filePath)).collect(Collectors.toList());

      var invalidLines =
          fileLines.stream()
              .filter(GraphFileValidator::isLineInvalid)
              .collect(Collectors.toList());

      if (!invalidLines.isEmpty()) {
        throw new InvalidGraphFile("Invalid line(s): " + invalidLines);
      }

    } catch (IOException e) {
      e.printStackTrace();
      throw new GraphFileReadException();
    }
  }

  private static boolean isLineInvalid(String line) {
    var isEmpty = line.isEmpty();
    var isRightLength = line.split(",").length == 3;

    return isEmpty || !isRightLength;
  }
}
