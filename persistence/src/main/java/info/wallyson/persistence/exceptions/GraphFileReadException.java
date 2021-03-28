package info.wallyson.persistence.exceptions;

import info.wallyson.core.exceptions.CoreExceptions;

public class GraphFileReadException extends CoreExceptions {

  public GraphFileReadException() {
    super("Error reading lines of the graph file.");
  }
}
