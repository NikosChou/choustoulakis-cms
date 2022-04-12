package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Skill {
  String name;
  String icon;
  Double value;
}
