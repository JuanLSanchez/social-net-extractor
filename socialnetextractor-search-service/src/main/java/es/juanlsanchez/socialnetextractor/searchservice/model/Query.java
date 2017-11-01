package es.juanlsanchez.socialnetextractor.searchservice.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
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
@CompoundIndex(name = "unique_search_source", unique = true, def = "{'search' : 1, 'source' : 1}")
public class Query extends BaseEntity {

  public static final String J_ACTIVE = "active";
  public static final String J_SOURCE = "source";
  public static final String J_SEARCH = "search";

  @JsonProperty(J_SEARCH)
  private String search;
  @Indexed
  @JsonProperty(J_SOURCE)
  private Source source;
  @JsonProperty(J_ACTIVE)
  private boolean active;


}
