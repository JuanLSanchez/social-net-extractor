package es.juanlsanchez.socialnetextractor.searchservice.config.properties;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "socialnetextractor-search-service")
public class Properties {

  @Valid
  @NotNull
  private Api api;

  @Getter
  @Setter
  public static class Api {
    private String prefix;
    private String version;
  }
}
