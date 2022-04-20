package de.choustoulakis.contentful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ContentfulApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContentfulApplication.class, args);
  }

  static {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Athens"));
  }
}
