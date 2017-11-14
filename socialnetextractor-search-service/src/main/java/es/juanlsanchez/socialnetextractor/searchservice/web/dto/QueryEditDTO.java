package es.juanlsanchez.socialnetextractor.searchservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.juanlsanchez.socialnetextractor.searchservice.model.enums.Source;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryEditDTO {

  @JsonProperty("search")
  private String search;
  @JsonProperty("source")
  private Source source;
  @JsonProperty("active")
  private boolean active;

}
