package info.wallyson.rest.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.wallyson.core.domain.Connection;

public class ConnectionRequest extends Connection {

  @JsonCreator
  public ConnectionRequest(
      @JsonProperty("source") String source,
      @JsonProperty("destination") String destination,
      @JsonProperty("cost") int cost) {
    super(source, destination, cost);
  }



}
