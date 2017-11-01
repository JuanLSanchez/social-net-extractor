package es.juanlsanchez.socialnetextractor.searchservice.web.manager.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.repository.SearchRepository;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.SearchEditDTO;
import es.juanlsanchez.socialnetextractor.searchservice.web.manager.SearchManager;
import es.juanlsanchez.socialnetextractor.searchservice.web.mapper.SearchMapper;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SearchManagerImpl implements SearchManager {

  private final SearchRepository searchRepository;
  private final SearchMapper searchMapper;

  @Override
  public Page<Search> findAll(Pageable pageable) {
    return searchRepository.findAll(pageable);
  }

  @Override
  public Search getOne(String searchId) {
    return Optional.ofNullable(searchRepository.findOne(searchId))
        .orElseThrow(() -> new IllegalArgumentException("Not found the search: " + searchId));
  }

  @Override
  public Search create(SearchEditDTO searchEditDTO) {
    Search search = searchMapper.fromSearchEditDTO(searchEditDTO);
    Instant now = Instant.now();
    search.setCreatedAt(now);
    search.setUpdatedAt(now);
    return searchRepository.save(search);
  }

  @Override
  public Search update(SearchEditDTO searchEditDTO, String searchId) {
    Search search = getOne(searchId);
    searchMapper.updateFromSearchEditDTO(searchEditDTO, search);
    search.setUpdatedAt(Instant.now());
    return searchRepository.save(search);
  }

  @Override
  public void delete(String searchId) {
    searchRepository.delete(searchId);
  }

}
