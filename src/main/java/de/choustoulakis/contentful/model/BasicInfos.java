package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BasicInfos {
  List<Info> infos;
  List<Social> social;
  Post summary;
  List<WorkExperience> workExperiences;
  List<Skill> skills;
  List<Skill> languages;
  List<Education> educations;
}
