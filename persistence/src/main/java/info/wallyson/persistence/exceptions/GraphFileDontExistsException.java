package info.wallyson.persistence.exceptions;

import info.wallyson.core.exceptions.CoreExceptions;

public class GraphFileDontExistsException extends CoreExceptions {
  public GraphFileDontExistsException(String fileName) {
    super(String.format("Graph file %s do not exists.", fileName));
  }
}
