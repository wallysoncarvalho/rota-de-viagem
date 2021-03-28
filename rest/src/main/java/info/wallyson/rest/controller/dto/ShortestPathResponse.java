package info.wallyson.rest.controller.dto;

public class ShortestPathResponse {
  private String path;
  private String cost;

  public ShortestPathResponse(String pathData) {
    var dataArr = pathData.split(">");

    this.path = dataArr[0].trim();
    this.cost = dataArr[1].trim();
  }

  public String getPath() {
    return path;
  }

  public String getCost() {
    return cost;
  }
}
