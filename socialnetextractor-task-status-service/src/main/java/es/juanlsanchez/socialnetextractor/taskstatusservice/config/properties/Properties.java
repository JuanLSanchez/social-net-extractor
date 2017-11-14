package es.juanlsanchez.socialnetextractor.taskstatusservice.config.properties;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(prefix = "socialnetextractor-task-status-service")
public class Properties {

  @Valid
  @NotNull
  private Api api;

  @NotNull
  @Min(1l)
  private Long backwardDays;

  @NotNull
  @Min(1l)
  private Long maxTaskWindowsTimeInHours;

  @NotNull
  @Min(1l)
  private Long minTaskWindowsTimeInMinutes;

  @NotNull
  @Min(1l)
  private Long maxTimeToStoreInHours;

  @Getter
  @Setter
  public static class Api {
    private String prefix;
    private String version;
  }
}
