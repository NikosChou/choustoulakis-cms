package de.choustoulakis.contentful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class ContentfulApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContentfulApplication.class, args);
  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("Greece"));
  }
}
