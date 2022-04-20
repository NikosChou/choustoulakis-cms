package de.choustoulakis.contentful.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Posts {
  List<Post> data;
}
