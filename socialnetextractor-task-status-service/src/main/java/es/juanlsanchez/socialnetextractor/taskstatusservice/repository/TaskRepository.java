package es.juanlsanchez.socialnetextractor.taskstatusservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.juanlsanchez.socialnetextractor.taskstatusservice.model.Task;
import es.juanlsanchez.socialnetextractor.taskstatusservice.model.enums.TaskStatus;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

  public Page<Task> findAllByQueryIdAndStatus(String queryId, TaskStatus status, Pageable pageable);

  public Page<Task> findAllByStatus(TaskStatus status, Pageable pageable);

  public Page<Task> findAllByQueryId(String queryId, Pageable pageable);

  public List<Task> findAllByStatusNot(TaskStatus stored);

}
