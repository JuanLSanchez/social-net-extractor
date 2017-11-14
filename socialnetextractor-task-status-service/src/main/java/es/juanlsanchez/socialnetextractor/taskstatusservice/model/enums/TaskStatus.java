package es.juanlsanchez.socialnetextractor.taskstatusservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus {
  queued(Constants.QUEUED), downloading(Constants.DOWNLOADING), downloaded(
      Constants.DOWNLOADED), stored(Constants.STORED);

  private final String value;

  public static class Constants {
    public static final String QUEUED = "queued";
    public static final String DOWNLOADING = "downloading";
    public static final String DOWNLOADED = "downloaded";
    public static final String STORED = "stored";
  }
}
