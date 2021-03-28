package info.wallyson.core.domain;

import org.junit.platform.commons.util.StringUtils;

public class Connection {
  private String source;
  private String destination;
  private int cost;

  public Connection(String source, String destination, int cost) {
    assert StringUtils.isNotBlank(source) : "Source node cannot be blank!";
    assert StringUtils.isNotBlank(destination) : "Destination node cannot be blank!";
    assert cost > 0 : "Connection cost have to be greater than 0!";

    this.source = source.trim();
    this.destination = destination.trim();
    this.cost = cost;
  }

  public String getSource() {
    return source;
  }

  public String getDestination() {
    return destination;
  }

  public int getCost() {
    return cost;
  }

  @Override
  public String toString() {
    return String.format("%s,%s,%d", source, destination, cost);
  }
}
