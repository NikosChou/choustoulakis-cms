package de.choustoulakis.contentful;

import com.contentful.java.cda.CDAClient;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(ContentfulApplication.class)
@Configuration
public class TestApplicationContext {

  public static final String DEFAULT_TOKEN = "test_token";
  public static final String DEFAULT_SPACE = "test_space";

  @Bean
  public Integer mockServerPort() {
    return 9874;
  }

  @Bean
  public MockWebServer server() {
    return createServer();
  }

  @Bean
  public CDAClient client() {
    return createClient();
  }

  protected String serverUrl() {
    return "http://localhost:" + mockServerPort();
  }

  protected MockWebServer createServer() {
    return new MockWebServer();
  }

  protected CDAClient createClient() {
    return createBuilder().build();
  }

  protected CDAClient.Builder createBuilder() {
    return CDAClient.builder()
        .setSpace(DEFAULT_SPACE)
        .setToken(DEFAULT_TOKEN)
        .setEndpoint(serverUrl());
  }
}
