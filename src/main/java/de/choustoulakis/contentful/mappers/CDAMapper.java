package de.choustoulakis.contentful.mappers;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.rich.html.HtmlContext;
import com.contentful.rich.html.HtmlProcessor;
import de.choustoulakis.contentful.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component
public class CDAMapper {
  private static final String TITLE = "title";
  private static final String BODY = "body";
  private static final String MEDIA = "media";
  private static final String REFERENCE = "reference";
  private static final String ACTION = "action";
  private static final String ICON = "icon";
  private static final String TEXT = "text";
  private static final String HREF = "href";
  private static final String COMPANY = "company";
  private static final String DESCRIPTION = "description";
  private static final String POINTS = "points";
  private static final String NAME = "name";
  private static final String VALUE = "value";
  private static final String SECONDARY_TEXT = "secondaryText";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMMM.yyyy", Locale.GERMAN);
  final HtmlProcessor processor;
  final HtmlContext context;

  public CDAMapper() {
    this.processor = new HtmlProcessor();
    this.context = new HtmlContext();
  }

  public Post map(CDAEntry entry) {
    return Post.builder()
        .title(entry.getField(TITLE))
        .body(processor.process(context, entry.getField(BODY)))
        .image(getUrl(entry))
        .reference(entry.getField(REFERENCE))
        .action(entry.getField(ACTION))
        .build();
  }

  public Info mapInfo(CDAEntry entry) {
    return Info.builder()
        .icon(entry.getField(ICON))
        .text(entry.getField(TEXT))
        .href(entry.getField(HREF))
        .build();
  }

  public Social mapSocial(CDAEntry entry) {
    return Social.builder().icon(entry.getField(ICON)).href(entry.getField(HREF)).build();
  }

  private String getUrl(CDAEntry entry) {
    return Optional.ofNullable(entry.getField(MEDIA)).map(e -> ((CDAAsset) e).url()).orElse(null);
  }

  public Post mapSummary(CDAEntry entry) {
    return Post.builder()
        .title(entry.getField(TITLE))
        .body(processor.process(context, entry.getField(BODY)))
        .reference(entry.getField(REFERENCE))
        .build();
  }

  public WorkExperience mapWorkExperience(CDAEntry entry) {
    return WorkExperience.builder()
        .title(entry.getField(TITLE))
        .company(entry.getField(COMPANY))
        .description(processor.process(context, entry.getField(DESCRIPTION)))
        .points(entry.getField(POINTS))
        .time(this.mapTime(entry.getField("startDate"), entry.getField("endDate")))
        .build();
  }

  private String mapTime(String startDate, String endDate) {
    if (StringUtils.isBlank(endDate)) {
      return "from " + FORMATTER.format(ZonedDateTime.parse(startDate));
    }
    return FORMATTER.format(ZonedDateTime.parse(startDate))
        + " - "
        + FORMATTER.format(ZonedDateTime.parse(endDate));
  }

  public Skill mapSkills(CDAEntry entry) {
    return Skill.builder()
        .name(entry.getField(NAME))
        .icon(entry.getField(ICON))
        .value(entry.getField(VALUE))
        .build();
  }

  public Education mapEducation(CDAEntry entry) {
    return Education.builder()
        .title(entry.getField(TEXT))
        .href(entry.getField(HREF))
        .icon(entry.getField(ICON))
        .organization(entry.getField(SECONDARY_TEXT))
        .build();
  }
}
