package de.choustoulakis.contentful.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("Test")
@SpringBootTest(classes = {StaticContentRouteConfiguration.class})
class StaticContentRouteConfigurationTest {

  @Autowired private StaticContentRouteConfiguration staticContentRouteConfiguration;

  private WebTestClient webClient;

  @BeforeEach
  void setUp() {
    this.webClient =
        WebTestClient.bindToRouterFunction(staticContentRouteConfiguration.staticRoute()).build();
  }

  @Test
  void shouldServeStaticResource() {
    this.webClient
        .get()
        .uri("/static/choustoulakis_cv.pdf")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_PDF)
        .expectHeader()
        .contentLength(55336)
        .expectBody(Resource.class);
  }
}
