package info.wallyson.rest.config;

import info.wallyson.core.ports.GetGraph;

import info.wallyson.core.ports.GraphConnection;

import info.wallyson.persistence.GetGraphAdapter;
import info.wallyson.persistence.GraphConnectionAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {

  @Bean
  public GetGraph getGraph() {
    return new GetGraphAdapter();
  }

  @Bean
  public GraphConnection graphConnection() {
    return new GraphConnectionAdapter();
  }
}
