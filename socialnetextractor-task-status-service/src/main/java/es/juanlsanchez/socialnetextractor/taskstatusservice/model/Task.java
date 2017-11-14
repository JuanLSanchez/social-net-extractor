package es.juanlsanchez.socialnetextractor.taskstatusservice.model;

import java.time.Instant;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import es.juanlsanchez.socialnetextractor.taskstatusservice.model.enums.TaskStatus;
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
public class Task extends BaseEntity {

  public static final String J_UNTIL = "until";
  public static final String J_SINCE = "since";
  public static final String J_QUERY_ID = "queryId";
  public static final String J_STATUS = "status";

  @Indexed
  @JsonProperty(J_STATUS)
  private TaskStatus status;
  @Indexed
  @JsonProperty(J_QUERY_ID)
  private String queryId;
  @JsonProperty(J_SINCE)
  private Instant since;
  @Indexed
  @JsonProperty(J_UNTIL)
  private Instant until;

}
