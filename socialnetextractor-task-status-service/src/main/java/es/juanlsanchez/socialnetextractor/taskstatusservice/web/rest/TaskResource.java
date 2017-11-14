package es.juanlsanchez.socialnetextractor.taskstatusservice.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.juanlsanchez.socialnetextractor.taskstatusservice.model.Task;
import es.juanlsanchez.socialnetextractor.taskstatusservice.model.enums.TaskStatus;
import es.juanlsanchez.socialnetextractor.taskstatusservice.web.dto.TaskEditDTO;
import es.juanlsanchez.socialnetextractor.taskstatusservice.web.manager.TaskManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(TaskResource.URL)
public class TaskResource {

  public static final String URL =
      "${socialnetextractor-task-status-service.api.prefix}${socialnetextractor-task-status-service.api.version}/task";
  private static final String TASK_ID_PARAM = "{taskId}";
  private static final String QUERY_ID_PARAM = "{queryId}";
  private static final String ID = "/id/" + TASK_ID_PARAM;
  private static final String QUERY_ID = "/query/id/" + QUERY_ID_PARAM;

  private final TaskManager taskManager;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Task>> findAll(String queryId, TaskStatus status, Pageable pageable) {
    log.info("REST request to list all task by query id {}, and status {}, with pageable: {}",
        queryId, status, pageable);
    Page<Task> body = taskManager.findAllByQueryIdAndStatus(queryId, status, pageable);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Task> getOne(@PathVariable String taskId) {
    log.info("REST request to get the task with id: {}", taskId);
    Task body = taskManager.getOne(taskId);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Task> update(@PathVariable String taskId,
      @Valid @RequestBody TaskEditDTO taskEditDTO) {
    log.info("REST request to update task {} with : {}", taskId, taskEditDTO);
    Task body = taskManager.update(taskEditDTO, taskId);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = QUERY_ID, method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Task>> nextTasks(@PathVariable String queryId) {
    log.info("REST request to get next tasks for query {}", queryId);
    List<Task> body = taskManager.nextTasks(queryId);
    return ResponseEntity.ok(body);
  }

}
