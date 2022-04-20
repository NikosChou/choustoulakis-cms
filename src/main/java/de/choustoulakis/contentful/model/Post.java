package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
  String title;
  String body;
  String image;
  String action;
  String reference;
}
