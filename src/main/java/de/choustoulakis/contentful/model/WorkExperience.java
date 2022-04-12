package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkExperience {
  String title;
  String description;
  String company;
  String time;
  String points;
}
