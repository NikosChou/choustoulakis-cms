package de.choustoulakis.contentful.service;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAResource;
import de.choustoulakis.contentful.service.lib.EnqueueResponseRule;
import de.choustoulakis.contentful.service.lib.TestCallback;
import de.choustoulakis.contentful.service.lib.TestResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.LogManager;

import static java.nio.charset.Charset.defaultCharset;
import static lombok.Lombok.checkNotNull;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EnqueueResponseRule.class)
public class BaseTest {
  public static final String DEFAULT_TOKEN = "test_token";
  public static final String DEFAULT_SPACE = "test_space";

  CDAClient client;

  MockWebServer server;

  List<TestResponse> responseQueue;

  @Rule public EnqueueResponseRule enqueueResponse = new EnqueueResponseRule();

  @BeforeEach
  public void setUp() throws IOException {
    LogManager.getLogManager().reset();
    server = createServer();
    server.start();

    client = createClient();

    if (responseQueue != null) {
      for (TestResponse response : responseQueue) {
        enqueue(response);
      }
    }
  }

  @AfterEach
  public void tearDown() throws IOException {
    server.shutdown();
  }

  protected CDAClient createClient() {
    return createBuilder().build();
  }

  protected CDAClient createPreviewClient() {
    return createBuilder().preview().setEndpoint(serverUrl()).build();
  }

  protected CDAClient.Builder createBuilder() {
    return CDAClient.builder()
        .setSpace(DEFAULT_SPACE)
        .setToken(DEFAULT_TOKEN)
        .setEndpoint(serverUrl());
  }

  protected String serverUrl() {
    return "http://" + server.getHostName() + ":" + server.getPort();
  }

  protected MockWebServer createServer() {
    return new MockWebServer();
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

  protected <T extends CDAResource> T assertCallback(TestCallback<T> callback) {
    assertThat(callback.error()).isNull();
    assertThat(callback.result()).isNotNull();
    return callback.result();
  }
}
