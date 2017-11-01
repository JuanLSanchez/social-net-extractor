package es.juanlsanchez.socialnetextractor.searchservice.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = BaseEntity.J_ID)
public abstract class BaseEntity {

  public static final String J_UPDATED_AT = "updatedAt";
  public static final String J_CREATED_AT = "createdAt";
  public static final String J_ID = "id";

  @Id
  @Indexed
  @JsonProperty(J_ID)
  private String id;

  @Indexed
  @JsonProperty(J_CREATED_AT)
  private Instant createdAt;

  @Indexed
  @JsonProperty(J_UPDATED_AT)
  private Instant updatedAt;

}
