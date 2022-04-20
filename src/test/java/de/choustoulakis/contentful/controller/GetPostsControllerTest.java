package de.choustoulakis.contentful.controller;

import de.choustoulakis.contentful.BaseTest;
import de.choustoulakis.contentful.lib.Enqueue;
import de.choustoulakis.contentful.model.BasicInfos;
import de.choustoulakis.contentful.model.Posts;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetPostsControllerTest extends BaseTest {

  @Test
  @Enqueue("contentful/post.json")
  void shouldNotBeEmpty() {
    webClient
        .get()
        .uri("/posts/homepage")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Posts.class)
        .value(posts -> assertThat(posts.getData()).isNotEmpty());
  }

  @Test
  @Enqueue({
    "contentful/simpleCard.json",
    "contentful/simpleCard.json",
    "contentful/post.json",
    "contentful/workExperience.json",
    "contentful/valueCard.json",
    "contentful/valueCard.json",
    "contentful/simpleCard.json"
  })
  void shouldGetProfile() {
    webClient
        .get()
        .uri("/posts/profile")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(BasicInfos.class)
        .value(basicInfos -> assertThat(basicInfos.getInfos()).isNotEmpty())
        .value(basicInfos -> assertThat(basicInfos.getEducations()).isNotEmpty())
        .value(basicInfos -> assertThat(basicInfos.getLanguages()).isNotEmpty())
        .value(basicInfos -> assertThat(basicInfos.getSkills()).isNotEmpty())
        .value(basicInfos -> assertThat(basicInfos.getSocial()).isNotEmpty())
        .value(basicInfos -> assertThat(basicInfos.getWorkExperiences()).isNotEmpty())
        .value(basicInfos -> assertThat(basicInfos.getSummary()).isNotNull());
  }
}
