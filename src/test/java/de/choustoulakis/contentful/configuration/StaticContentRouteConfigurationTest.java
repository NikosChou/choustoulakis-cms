package de.choustoulakis.contentful.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("Test")
@SpringBootTest(classes = {StaticContentRouteConfiguration.class})
class StaticContentRouteConfigurationTest {

  @Autowired private WebTestClient webClient;

  @Test
  void foo() {
    this.webClient.get().uri("/static/choustoulakis_cv.pdf").exchange().expectStatus().isOk();
  }
}
