package es.juanlsanchez.socialnetextractor.taskstatusservice.web.manager;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.socialnetextractor.taskstatusservice.model.Task;
import es.juanlsanchez.socialnetextractor.taskstatusservice.model.enums.TaskStatus;
import es.juanlsanchez.socialnetextractor.taskstatusservice.web.dto.TaskEditDTO;

public interface TaskManager {

  public Page<Task> findAllByQueryIdAndStatus(String queryId, TaskStatus status, Pageable pageable);

  public Task getOne(String taskId);

  public Task update(TaskEditDTO taskEditDTO, String taskId);

  public List<Task> nextTasks(String queryId);

}
