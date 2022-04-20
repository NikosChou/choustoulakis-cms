package de.choustoulakis.contentful.controller;

import de.choustoulakis.contentful.model.BasicInfos;
import de.choustoulakis.contentful.service.ContentfulService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class GetProfileController {

  private final ContentfulService service;

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
    return ResponseEntity.ok(basicInfos);
  }
}
