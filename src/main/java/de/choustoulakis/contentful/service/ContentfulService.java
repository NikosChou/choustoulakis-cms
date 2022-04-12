package de.choustoulakis.contentful.service;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAType;
import de.choustoulakis.contentful.mappers.CDAMapper;
import de.choustoulakis.contentful.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ContentfulService {

  private static final String PROFILE_INFOS = "profile-infos";
  private static final String PROFILE_SOCIAL = "profile-social";
  private static final String POST = "post";
  private static final String SIMPLE_CARD = "simpleCard";
  private static final String REFERENCE = "reference";
  public static final String APPLICATION_SUMMARY_TEXT = "application.summary.text";
  public static final String WORK_EXPERIENCE = "workExperience";
  public static final String VALUE_CARD = "valueCard";
  public static final String PROGRAMMING = "programming";
  public static final String LANGUAGE = "language";
  public static final String EDUCATION = "education";
  private final CDAClient client;
  private final CDAMapper cdaMapper;

  public ContentfulService(
      @Value("${contentful.spaceid}") String spaceId,
      @Value("${contentful.accesstoken}") String accessToken,
      CDAMapper cdaMapper) {
    this.client = CDAClient.builder().setSpace(spaceId).setToken(accessToken).build();
    this.cdaMapper = cdaMapper;
  }

  public List<Post> getPosts(List<String> references) {
    return this.client
        .fetch(CDAEntry.class)
        .withContentType(POST)
        .withLocale(Locale.GERMAN.toString())
        .all()
        .items()
        .stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> references.contains((String) entry.getField(REFERENCE)))
        .map(cdaMapper::map)
        .toList();
  }

  public List<Info> getProfileInfos() {
    return this.client
        .fetch(CDAEntry.class)
        .withContentType(SIMPLE_CARD)
        .withLocale(Locale.GERMAN.toString())
        .all()
        .items()
        .stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> PROFILE_INFOS.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapInfo)
        .toList();
  }

  public List<Social> getProfileSocial() {

    return this.client
        .fetch(CDAEntry.class)
        .withContentType(SIMPLE_CARD)
        .withLocale(Locale.GERMAN.toString())
        .all()
        .items()
        .stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> PROFILE_SOCIAL.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSocial)
        .toList();
  }

  public Post getProfileSummary() {
    return this.client
        .fetch(CDAEntry.class)
        .withContentType(POST)
        .withLocale(Locale.GERMAN.toString())
        .all()
        .items()
        .stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> APPLICATION_SUMMARY_TEXT.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSummary)
        .findFirst()
        .orElse(null);
  }

  public List<WorkExperience> getProfileWorkExperience() {
    return this.client
        .fetch(CDAEntry.class)
        .withContentType(WORK_EXPERIENCE)
        .withLocale(Locale.GERMAN.toString())
        .all()
        .items()
        .stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .map(cdaMapper::mapWorkExperience)
        .toList();
  }

  public List<Skill> getProfileSkills() {
    return this.client
        .fetch(CDAEntry.class)
        .withContentType(VALUE_CARD)
        .withLocale(Locale.GERMAN.toString())
        .all()
        .items()
        .stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> PROGRAMMING.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSkills)
        .toList();
  }

  public List<Skill> getLanguageSkills() {
    return this.client
        .fetch(CDAEntry.class)
        .withContentType(VALUE_CARD)
        .withLocale(Locale.GERMAN.toString())
        .all()
        .items()
        .stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> LANGUAGE.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSkills)
        .toList();
  }

  public List<Education> getEducations() {
    return this.client
            .fetch(CDAEntry.class)
            .withContentType(SIMPLE_CARD)
            .withLocale(Locale.GERMAN.toString())
            .all()
            .items()
            .stream()
            .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
            .map(cdaResource -> (CDAEntry) cdaResource)
            .filter(entry -> EDUCATION.equals(entry.getField(REFERENCE)))
            .map(cdaMapper::mapEducation)
            .toList();
  }
}
