package info.wallyson.rest;

import info.wallyson.rest.exceptions.RestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class RestApp {
  private final Logger LOG = LoggerFactory.getLogger(RestApp.class);

  @Value("${graph.path}")
  String filePath;

  public static void main(String[] args) {
    SpringApplication.run(RestApp.class, args);
  }

  @PostConstruct
  public void init() {
    LOG.info("Graph file inputted '{}'", filePath);

    if (!Files.exists(Paths.get(filePath))) {
      LOG.error("File inpputed on server startup do not exists.");
    }
  }
}
