package es.juanlsanchez.socialnetextractor.searchservice.web.manager.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import es.juanlsanchez.socialnetextractor.searchservice.model.Query;
import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.repository.QueryRepository;
import es.juanlsanchez.socialnetextractor.searchservice.repository.SearchRepository;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.QueryEditDTO;
import es.juanlsanchez.socialnetextractor.searchservice.web.manager.QueryManager;
import es.juanlsanchez.socialnetextractor.searchservice.web.mapper.QueryMapper;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class QueryManagerImpl implements QueryManager {

  public final QueryRepository queryRepository;
  public final SearchRepository searchRepository;
  public final QueryMapper queryMapper;

  @Override
  public Page<Query> findAll(Pageable pageable) {
    return queryRepository.findAll(pageable);
  }

  @Override
  public Query getOne(String queryId) {
    return Optional.ofNullable(queryRepository.findOne(queryId))
        .orElseThrow(() -> new IllegalArgumentException("Not found query with id: " + queryId));
  }

  @Override
  public Query create(QueryEditDTO queryEditDTO) {
    Query query = queryMapper.fromQueryEditDTO(queryEditDTO);
    Instant now = Instant.now();
    query.setCreatedAt(now);
    query.setUpdatedAt(now);
    return queryRepository.save(query);
  }

  @Override
  public Query update(QueryEditDTO queryEditDTO, String queryId) {
    Query query = this.getOne(queryId);
    queryMapper.updateQueryEditDTO(queryEditDTO, query);
    query.setUpdatedAt(Instant.now());
    return queryRepository.save(query);
  }

  @Override
  public void delete(String queryId) {
    List<Search> searchs = searchRepository.findAllByQueryIds(queryId);
    for (Search search : searchs) {
      search.getQueryIds().remove(queryId);
      searchRepository.save(search);
    }
    queryRepository.delete(queryId);
  }

  @Override
  public List<Query> findAllBySearchId(String searchId) {
    Search search = Optional.ofNullable(searchRepository.findOne(searchId))
        .orElseThrow(() -> new IllegalArgumentException("Not found search " + searchId));

    List<Query> result = Lists.newLinkedList();
    search.getQueryIds().forEach(queryId -> result.add(queryRepository.findOne(queryId)));
    return result;
  }

}
