package de.choustoulakis.contentful.service;

import de.choustoulakis.contentful.client.ContentfulClient;
import de.choustoulakis.contentful.mappers.CDAMapper;
import de.choustoulakis.contentful.service.lib.Enqueue;
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
  void assertSutIsNotNull() {
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
}
