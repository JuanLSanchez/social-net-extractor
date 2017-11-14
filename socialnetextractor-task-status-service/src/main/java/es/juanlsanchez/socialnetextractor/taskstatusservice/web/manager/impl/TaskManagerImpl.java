package es.juanlsanchez.socialnetextractor.taskstatusservice.web.manager.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.mapstruct.ap.internal.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import es.juanlsanchez.socialnetextractor.taskstatusservice.config.properties.Properties;
import es.juanlsanchez.socialnetextractor.taskstatusservice.model.Task;
import es.juanlsanchez.socialnetextractor.taskstatusservice.model.enums.TaskStatus;
import es.juanlsanchez.socialnetextractor.taskstatusservice.repository.TaskRepository;
import es.juanlsanchez.socialnetextractor.taskstatusservice.web.dto.TaskEditDTO;
import es.juanlsanchez.socialnetextractor.taskstatusservice.web.manager.TaskManager;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TaskManagerImpl implements TaskManager {

  private final TaskRepository taskRepository;
  private final Properties properties;

  @Override
  public Page<Task> findAllByQueryIdAndStatus(final String queryId, final TaskStatus status,
      Pageable pageable) {
    final Page<Task> result;
    if (Strings.isEmpty(queryId) && status == null) {
      result = this.taskRepository.findAll(pageable);
    } else {
      if (Strings.isEmpty(queryId)) {
        result = this.taskRepository.findAllByStatus(status, pageable);
      } else if (status == null) {
        result = this.taskRepository.findAllByQueryId(queryId, pageable);
      } else {
        result = this.taskRepository.findAllByQueryIdAndStatus(queryId, status, pageable);
      }
    }
    return result;
  }

  @Override
  public Task getOne(final String taskId) {
    return Optional.ofNullable(this.taskRepository.findOne(taskId))
        .orElseThrow(() -> new IllegalArgumentException("Not found task with id" + taskId));
  }

  @Override
  public Task update(TaskEditDTO taskEditDTO, String taskId) {
    Task task = getOne(taskId);
    task.setStatus(taskEditDTO.getStatus());
    task.setUpdatedAt(Instant.now());
    Task result = this.taskRepository.save(task);
    return result;
  }

  @Override
  public List<Task> nextTasks(String queryId) {
    List<Task> result = Lists.newLinkedList();

    final Instant until = Instant.now();
    // Value by default
    Instant since = until.minus(Duration.ofDays(properties.getBackwardDays()));

    // Get last task by query id
    final Pageable pageable = new PageRequest(0, 1, new Sort(Direction.DESC, Task.J_UNTIL));
    final Page<Task> lastTask = this.taskRepository.findAllByQueryId(queryId, pageable);

    if (!lastTask.getContent().isEmpty()) {
      since = lastTask.getContent().get(0).getUntil();
      // Check not stored
      final Instant maxInstantToStore =
          Instant.now().minus(Duration.ofHours(properties.getMaxTimeToStoreInHours()));
      this.taskRepository.findAllByStatusNot(TaskStatus.stored).stream()
          .filter(task -> task.getUpdatedAt().isBefore(maxInstantToStore))
          .forEach(task -> result.add(task));
    }
    this.addRangeWithSinceAndUntilByQueryId(since, until, queryId, result);
    return result;
  }

  private void addRangeWithSinceAndUntilByQueryId(final Instant since, final Instant until,
      final String queryId, List<Task> result) {
    Instant instant = Instant.from(since);
    boolean inRange = true;
    while (inRange) {
      final Instant now = Instant.now();
      Task task = new Task(TaskStatus.queued, queryId, instant,
          instant.plus(Duration.ofHours(properties.getMaxTaskWindowsTimeInHours())));
      instant = task.getUntil();
      task.setCreatedAt(now);
      task.setUpdatedAt(now);

      if (instant.isAfter(until) || instant.equals(until)) {
        inRange = false;
        // In the last loop the until of object is until
        task.setUntil(until);
      }

      if (ChronoUnit.MINUTES.between(since, until) >= properties.getMinTaskWindowsTimeInMinutes()) {
        result.add(this.taskRepository.save(task));
      }
    }

  }

}
