package es.juanlsanchez.socialnetextractor.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Source {
  twitter(Constants.TWITTER);

  private final String value;

  public static class Constants {

    public static final String TWITTER = "twitter";

  }

}
