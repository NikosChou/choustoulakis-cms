package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Info {
  String icon;
  String text;
  String href;
}
