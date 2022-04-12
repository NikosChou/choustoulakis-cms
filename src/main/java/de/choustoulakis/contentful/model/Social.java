package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Social {
  String icon;
  String href;
}
