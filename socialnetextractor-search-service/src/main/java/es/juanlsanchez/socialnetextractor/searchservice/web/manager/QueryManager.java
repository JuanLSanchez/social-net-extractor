package es.juanlsanchez.socialnetextractor.searchservice.web.manager;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.socialnetextractor.searchservice.model.Query;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.QueryEditDTO;

public interface QueryManager {

  public Page<Query> findAll(Pageable pageable);

  public Query getOne(String queryId);

  public Query create(QueryEditDTO queryEditDTO);

  public Query update(QueryEditDTO queryEditDTO, String queryId);

  public void delete(String queryId);

  public List<Query> findAllBySearchId(String searchId);

}
