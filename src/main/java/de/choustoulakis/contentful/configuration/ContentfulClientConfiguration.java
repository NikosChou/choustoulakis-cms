package de.choustoulakis.contentful.configuration;

import com.contentful.java.cda.CDAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentfulClientConfiguration {

  @Bean
  public CDAClient client(
      @Value("${contentful.spaceid}") String spaceId,
      @Value("${contentful.accesstoken}") String accessToken) {
    return CDAClient.builder().setSpace(spaceId).setToken(accessToken).build();
  }
}
