package de.choustoulakis.contentful.controller;

import de.choustoulakis.contentful.model.BasicInfos;
import de.choustoulakis.contentful.model.Posts;
import de.choustoulakis.contentful.service.ContentfulService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@RequestMapping("/posts")
@AllArgsConstructor
@Controller
public class GetPostsController {

  private final ContentfulService service;

  @GetMapping(value = "/homepage", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Posts> getHomepagePosts() {
    log.info("Entering homepage");
    var posts =
        Posts.builder().data(this.service.getPosts(List.of("homepage-2", "homepage-1"))).build();
    return ResponseEntity.ok(log.traceExit(posts));
  }

  @GetMapping("/profile")
  public ResponseEntity<BasicInfos> getProfile() {
    log.info("Entering profile");

    var basicInfos =
        BasicInfos.builder()
            .infos(service.getProfileInfos())
            .social(service.getProfileSocial())
            .summary(service.getProfileSummary())
            .workExperiences(service.getProfileWorkExperience())
            .skills(service.getProfileSkills())
            .languages(service.getLanguageSkills())
            .educations(service.getEducations())
            .build();

    return ResponseEntity.ok(log.traceExit(basicInfos));
  }
}
