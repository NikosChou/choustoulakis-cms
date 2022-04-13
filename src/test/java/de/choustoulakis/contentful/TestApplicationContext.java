package de.choustoulakis.contentful;

import de.choustoulakis.contentful.service.ContentfulService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestApplicationContext {

  @Autowired ContentfulService service;

  @Test
  void testContext() {
    assertThat(service).isNotNull();
  }
}
