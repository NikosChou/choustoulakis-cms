package de.choustoulakis.contentful.service;

import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAType;
import de.choustoulakis.contentful.client.ContentfulClient;
import de.choustoulakis.contentful.mappers.CDAMapper;
import de.choustoulakis.contentful.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
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

  private final ContentfulClient client;
  private final CDAMapper cdaMapper;

  public List<Post> getPosts(List<String> references) {
    return this.client.getWithContentType(POST, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> references.contains((String) entry.getField(REFERENCE)))
        .map(cdaMapper::map)
        .toList();
  }

  public List<Info> getProfileInfos() {
    return this.client.getWithContentType(SIMPLE_CARD, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> PROFILE_INFOS.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapInfo)
        .toList();
  }

  public List<Social> getProfileSocial() {
    return this.client.getWithContentType(SIMPLE_CARD, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> PROFILE_SOCIAL.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSocial)
        .toList();
  }

  public Post getProfileSummary() {
    return this.client.getWithContentType(POST, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> APPLICATION_SUMMARY_TEXT.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSummary)
        .findFirst()
        .orElse(null);
  }

  public List<WorkExperience> getProfileWorkExperience() {
    Comparator<CDAEntry> objectComparator =
        Comparator.comparing(e -> e.getField(CDAMapper.START_DATE));

    return this.client.getWithContentType(WORK_EXPERIENCE, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .sorted(objectComparator.reversed())
        .map(cdaMapper::mapWorkExperience)
        .toList();
  }

  public List<Skill> getProfileSkills() {
    return this.client.getWithContentType(VALUE_CARD, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> PROGRAMMING.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSkills)
        .sorted(Comparator.comparing(Skill::getValue).reversed())
        .toList();
  }

  public List<Skill> getLanguageSkills() {
    return this.client.getWithContentType(VALUE_CARD, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> LANGUAGE.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapSkills)
        .toList();
  }

  public List<Education> getEducations() {
    return this.client.getWithContentType(SIMPLE_CARD, Locale.GERMAN.toString()).stream()
        .filter(cdaResource -> cdaResource.type().equals(CDAType.ENTRY))
        .map(cdaResource -> (CDAEntry) cdaResource)
        .filter(entry -> EDUCATION.equals(entry.getField(REFERENCE)))
        .map(cdaMapper::mapEducation)
        .toList();
  }
}
