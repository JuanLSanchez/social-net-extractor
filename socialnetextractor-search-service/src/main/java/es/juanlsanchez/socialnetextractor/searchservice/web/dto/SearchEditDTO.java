package es.juanlsanchez.socialnetextractor.searchservice.web.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEditDTO {

  @JsonProperty("name")
  private String name;
  @JsonProperty("queryIds")
  private Set<String> queryIds;

}
