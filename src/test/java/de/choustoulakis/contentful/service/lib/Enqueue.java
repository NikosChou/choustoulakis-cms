package de.choustoulakis.contentful.service.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Enqueue {
  String[] defaults() default {"contentful/locales.json", "contentful/content_types.json"};

  String[] value() default {};

  EnqueueResponse[] complex() default {};
}
