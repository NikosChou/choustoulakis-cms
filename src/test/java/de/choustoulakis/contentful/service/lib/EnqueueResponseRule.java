package de.choustoulakis.contentful.service.lib;

import de.choustoulakis.contentful.service.BaseTest;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EnqueueResponseRule implements BeforeEachCallback {

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    Enqueue enqueue =
        context.getTestMethod().map(m -> m.getAnnotation(Enqueue.class)).orElseThrow();
    var o = context.getTestInstance().orElseThrow();

    if (enqueue != null) {
      if (!(o instanceof BaseTest)) {
        throw new RuntimeException(
            "Test class must extend "
                + BaseTest.class.getName()
                + "when using @"
                + Enqueue.class.getSimpleName());
      }
      List<TestResponse> responses = new ArrayList<>(enqueueToTestResponse(enqueue));
      ((BaseTest) o).setResponseQueue(responses);
    }
  }

  private Collection<? extends TestResponse> enqueueToTestResponse(Enqueue enqueue) {
    final List<TestResponse> responses =
        new ArrayList<>(enqueue.complex().length + enqueue.value().length);

    for (final EnqueueResponse response : enqueue.complex()) {
      responses.add(new TestResponse(response.code(), response.fileName(), response.headers()));
    }

    for (final String response : enqueue.defaults()) {
      responses.add(new TestResponse(200, response, new String[] {}));
    }

    for (final String response : enqueue.value()) {
      responses.add(new TestResponse(200, response, new String[] {}));
    }

    return responses;
  }
}
