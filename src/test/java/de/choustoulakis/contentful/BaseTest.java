package de.choustoulakis.contentful;

import com.contentful.java.cda.CDAClient;
import de.choustoulakis.contentful.lib.EnqueueResponseRule;
import de.choustoulakis.contentful.lib.TestResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static lombok.Lombok.checkNotNull;
import static org.apache.commons.io.FileUtils.readFileToString;

@ActiveProfiles("test")
@SpringBootTest(
    classes = TestApplicationContext.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(EnqueueResponseRule.class)
public class BaseTest {

  @Autowired protected CDAClient client;

  @Autowired Integer mockServerPort;

  MockWebServer server;
  List<TestResponse> responseQueue;

  @Rule public EnqueueResponseRule enqueueResponse = new EnqueueResponseRule();

  @LocalServerPort private Integer port;

  protected WebTestClient webClient;

  @BeforeEach
  protected void setUp() throws IOException {
    server = new MockWebServer();
    server.start(InetAddress.getByName("localhost"), mockServerPort);
    this.webClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + this.port).build();

    if (responseQueue != null) {
      for (TestResponse response : responseQueue) {
        enqueue(response);
      }
    }
  }

  @AfterEach
  protected void tearDown() throws IOException {
    server.shutdown();
    client.clearCache();
  }

  protected void enqueue(TestResponse response) throws IOException {
    URL resource = getClass().getClassLoader().getResource(response.getFileName());
    checkNotNull(resource, "File not found: " + response.getFileName());
    final MockResponse mock =
        new MockResponse()
            .setResponseCode(response.getCode())
            .setBody(readFileToString(new File(resource.getFile()), defaultCharset()));

    if (response.headers().size() > 0) {
      mock.setHeaders(response.headers());
    }

    server.enqueue(mock);
  }

  public BaseTest setResponseQueue(List<TestResponse> responseQueue) {
    this.responseQueue = responseQueue;
    return this;
  }
}
