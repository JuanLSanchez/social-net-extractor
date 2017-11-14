package es.juanlsanchez.socialnetextractor.taskstatusservice.web.dto;

import es.juanlsanchez.socialnetextractor.taskstatusservice.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEditDTO {

  private TaskStatus status;

}
