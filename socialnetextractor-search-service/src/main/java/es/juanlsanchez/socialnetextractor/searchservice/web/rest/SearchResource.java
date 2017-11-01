package es.juanlsanchez.socialnetextractor.searchservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

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

import es.juanlsanchez.socialnetextractor.searchservice.config.Resolve;
import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.SearchEditDTO;
import es.juanlsanchez.socialnetextractor.searchservice.web.manager.SearchManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(SearchResource.URL)
public class SearchResource extends Resolve {

  public static final String URL =
      "${socialnetextractor-search-service.api.prefix}${socialnetextractor-search-service.api.version}/search";
  private static final String SEARCH_ID_PARAM = "{searchId}";
  private static final String ID = "/id/" + SEARCH_ID_PARAM;

  private final SearchManager searchManager;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Search>> findAll(Pageable pageable) {
    log.info("REST request to list all search with pageable: {}", pageable);
    Page<Search> body = searchManager.findAll(pageable);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Search> getOne(@PathVariable String searchId) {
    log.info("REST request to get the search with id: {}", searchId);
    Search body = searchManager.getOne(searchId);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Search> create(@Valid @RequestBody SearchEditDTO searchEditDTO)
      throws URISyntaxException {
    log.info("REST request to create the search: {}", searchEditDTO);
    Search body = searchManager.create(searchEditDTO);
    URI location = new URI(this.resolve(URL + ID.replace(SEARCH_ID_PARAM, body.getId())));
    return ResponseEntity.created(location).body(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Search> update(@PathVariable String searchId,
      @Valid @RequestBody SearchEditDTO searchEditDTO) {
    log.info("REST request to update search {} with : {}", searchId, searchEditDTO);
    Search body = searchManager.update(searchEditDTO, searchId);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable String searchId) {
    log.info("REST request to delete the search with id: {}", searchId);
    searchManager.delete(searchId);
    return ResponseEntity.ok().build();
  }

}
