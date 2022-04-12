package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Posts {
  List<Post> data;
}
