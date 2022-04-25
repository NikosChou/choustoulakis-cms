package de.choustoulakis.contentful.service;

import de.choustoulakis.contentful.BaseTest;
import de.choustoulakis.contentful.client.ContentfulClient;
import de.choustoulakis.contentful.lib.Enqueue;
import de.choustoulakis.contentful.mappers.CDAMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentfulServiceTest extends BaseTest {

  private ContentfulService sut;

  @BeforeEach
  void setup() {
    this.sut = new ContentfulService(new ContentfulClient(client), new CDAMapper());
  }

  @Test
  @Enqueue("contentful/workExperience.json")
  void shouldSortTheWorkExperience() {
    var actual = this.sut.getProfileWorkExperience();

    assertThat(actual).isNotEmpty();
    assertThat(actual.get(0).getCompany()).isEqualTo("ISTA (Festanstellung)");
    assertThat(actual.get(1).getCompany()).isEqualTo("P&C (Festanstellung)");
    assertThat(actual.get(2).getCompany()).isEqualTo("Toyota Kreditbank (Externe)");
    assertThat(actual.get(3).getCompany()).isEqualTo("Brainbits (Externe)");
    assertThat(actual.get(4).getCompany()).isEqualTo("Check24 (Festanstellung)");
    assertThat(actual.get(5).getCompany())
        .isEqualTo("Treelogics Software Services GmbH (Praktikant)");
  }

  @Test
  @Enqueue("contentful/valueCard.json")
  void shouldSortTheSkills() {
    var actual = this.sut.getProfileSkills();

    assertThat(actual).isNotEmpty();
    assertThat(actual.get(0).getName()).isEqualToIgnoringCase("Html");
    assertThat(actual.get(0).getValue()).isEqualTo(100);

    assertThat(actual.get(1).getName()).isEqualToIgnoringCase("Java");
    assertThat(actual.get(1).getValue()).isEqualTo(95);

    assertThat(actual.get(2).getName()).isEqualToIgnoringCase("Vue");
    assertThat(actual.get(2).getValue()).isEqualTo(90);

    assertThat(actual.get(3).getName()).isEqualToIgnoringCase("Css");
    assertThat(actual.get(3).getValue()).isEqualTo(80);

    assertThat(actual.get(4).getName()).isEqualToIgnoringCase("Js");
    assertThat(actual.get(4).getValue()).isEqualTo(80);

    assertThat(actual.get(5).getName()).isEqualToIgnoringCase("Scala");
    assertThat(actual.get(5).getValue()).isEqualTo(80);
  }

  @Test
  @Enqueue("contentful/valueCard.json")
  void shouldOrderLanguages() {
    var actual = this.sut.getLanguageSkills();

    assertThat(actual).isNotEmpty();
    assertThat(actual.get(0).getName()).isEqualToIgnoringCase("Griechisch");
    assertThat(actual.get(0).getValue()).isEqualTo(100);

    assertThat(actual.get(1).getName()).isEqualToIgnoringCase("Deutsch");
    assertThat(actual.get(1).getValue()).isEqualTo(90);

    assertThat(actual.get(2).getName()).isEqualToIgnoringCase("Englisch");
    assertThat(actual.get(2).getValue()).isEqualTo(80);
  }
}
