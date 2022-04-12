package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Education {
  String title;
  String organization;
  String href;
  String icon;
}
