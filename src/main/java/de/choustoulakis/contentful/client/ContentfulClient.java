package de.choustoulakis.contentful.client;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ContentfulClient {

  private final CDAClient client;

  public List<CDAResource> getWithContentType(String contentType, String locale) {
    return this.client
        .fetch(CDAEntry.class)
        .withContentType(contentType)
        .withLocale(locale)
        .all()
        .items();
  }
}
