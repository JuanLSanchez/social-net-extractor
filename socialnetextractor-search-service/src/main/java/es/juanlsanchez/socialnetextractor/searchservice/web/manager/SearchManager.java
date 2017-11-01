package es.juanlsanchez.socialnetextractor.searchservice.web.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.SearchEditDTO;

public interface SearchManager {

  public Page<Search> findAll(Pageable pageable);

  public Search getOne(String searchId);

  public Search create(SearchEditDTO searchEditDTO);

  public Search update(SearchEditDTO searchEditDTO, String searchId);

  public void delete(String searchId);

}
