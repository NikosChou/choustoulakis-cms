package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
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
