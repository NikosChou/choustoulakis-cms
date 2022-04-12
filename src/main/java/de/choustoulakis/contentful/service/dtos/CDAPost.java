package de.choustoulakis.contentful.service.dtos;

import com.contentful.java.cda.TransformQuery.ContentfulEntryModel;
import com.contentful.java.cda.TransformQuery.ContentfulField;
import lombok.Data;

@Data
@ContentfulEntryModel("post")
public class CDAPost {
  @ContentfulField private String title;

  @ContentfulField private String body;

  @ContentfulField private String media;
}
