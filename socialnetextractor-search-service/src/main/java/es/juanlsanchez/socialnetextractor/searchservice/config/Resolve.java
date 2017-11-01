package es.juanlsanchez.socialnetextractor.searchservice.config;

import javax.inject.Inject;

import org.springframework.core.env.Environment;

public class Resolve {

  @Inject
  private Environment env;

  public String resolve(String... text) {
    String result = "";
    for (String t : text) {
      result += this.env.resolvePlaceholders(t);
    }
    return result;
  }
}
