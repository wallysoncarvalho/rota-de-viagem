package info.wallyson.core.ports;

import info.wallyson.core.domain.Node;

import java.util.HashMap;

public interface GetGraph {
  HashMap<String, Node> get(String graphIdentifier);
}
