package es.juanlsanchez.socialnetextractor.searchservice.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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

import es.juanlsanchez.socialnetextractor.searchservice.config.Resolve;
import es.juanlsanchez.socialnetextractor.searchservice.model.Query;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.QueryEditDTO;
import es.juanlsanchez.socialnetextractor.searchservice.web.manager.QueryManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(QueryResource.URL)
public class QueryResource extends Resolve {

  public static final String URL =
      "${socialnetextractor-search-service.api.prefix}${socialnetextractor-search-service.api.version}/query";
  private static final String QUERY_ID_PARAM = "{queryId}";
  private static final String SEARCH_ID_PARAM = "{searchId}";
  private static final String ID = "/id/" + QUERY_ID_PARAM;
  private static final String SEARCH_ID = "/search/id" + SEARCH_ID_PARAM;

  private final QueryManager queryManager;

  public static String urlId(String id) {
    return URL + ID.replace(QUERY_ID_PARAM, id);
  }

  public static String urlSearchId(String id) {
    return URL + SEARCH_ID.replace(SEARCH_ID_PARAM, id);
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Query>> findAll(Pageable pageable) {
    log.info("REST request to list all query with pageable: {}", pageable);
    Page<Query> body = queryManager.findAll(pageable);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Query> getOne(@PathVariable String queryId) {
    log.info("REST request to get the query with id: {}", queryId);
    Query body = queryManager.getOne(queryId);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = SEARCH_ID, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Query>> findAllBySearch(@PathVariable String searchId) {
    log.info("REST request to fiad all quereis by search id: {}", searchId);
    List<Query> body = queryManager.findAllBySearchId(searchId);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Query> create(@Valid @RequestBody QueryEditDTO queryEditDTO)
      throws URISyntaxException {
    log.info("REST request to create the query: {}", queryEditDTO);
    Query body = queryManager.create(queryEditDTO);
    URI location = new URI(this.resolve(URL + ID.replace(QUERY_ID_PARAM, body.getId())));
    return ResponseEntity.created(location).body(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Query> update(@PathVariable String queryId,
      @Valid @RequestBody QueryEditDTO queryEditDTO) {
    log.info("REST request to update query {} with : {}", queryId, queryEditDTO);
    Query body = queryManager.update(queryEditDTO, queryId);
    return ResponseEntity.ok(body);
  }

  @RequestMapping(value = ID, method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable String queryId) {
    log.info("REST request to delete the query with id: {}", queryId);
    queryManager.delete(queryId);
    return ResponseEntity.ok().build();
  }

}
