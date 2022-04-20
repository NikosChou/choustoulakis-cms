package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Education {
  String title;
  String organization;
  String href;
  String icon;
}
