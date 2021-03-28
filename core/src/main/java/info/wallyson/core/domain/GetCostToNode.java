package info.wallyson.core.domain;

import info.wallyson.core.exceptions.NullNodeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class GetCostToNode {

  public static String to(Node destination) {
    if (Objects.isNull(destination)) throw new NullNodeException();

    var totalCost = destination.getCost();
    var path = new ArrayList<String>();

    var parent = destination;

    while (parent != null) {
      path.add(parent.getName());
      parent = parent.getParent();
    }

    Collections.reverse(path);

    var pathStr = String.join(" - ", path);

    return String.format("%s > $%d", pathStr, totalCost);
  }
}
