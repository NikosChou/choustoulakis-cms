package de.choustoulakis.contentful.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Log4j2
@Configuration
public class StaticContentRouteConfiguration {

  private final Resource cv;

  public StaticContentRouteConfiguration(
      @Value("classpath:/static/CV_Nikolaos_Choustoulakis.pdf") Resource cv) {
    this.cv = cv;
  }

  @Bean
  public RouterFunction<ServerResponse> staticRoute() {
    return route(GET("/static/choustoulakis_cv.pdf"), this::getChoustoulakisCv);
  }

  @NonNull
  private Mono<ServerResponse> getChoustoulakisCv(ServerRequest request) {
    log.info("Downloading CV");
    return ok().contentType(MediaType.APPLICATION_PDF).bodyValue(cv);
  }
}
