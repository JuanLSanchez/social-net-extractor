package es.juanlsanchez.socialnetextractor.searchservice.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Search extends BaseEntity {

  public static final String J_QUERY_IDS = "queryIds";
  public static final String J_NAME = "name";

  @JsonProperty(J_NAME)
  private String name;
  @JsonProperty(J_QUERY_IDS)
  private Set<String> queryIds;
}
