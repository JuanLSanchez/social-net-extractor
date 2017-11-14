package es.juanlsanchez.socialnetextractor.searchservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import es.juanlsanchez.socialnetextractor.searchservice.config.Populate;
import es.juanlsanchez.socialnetextractor.searchservice.config.Resolve;
import es.juanlsanchez.socialnetextractor.searchservice.model.Query;
import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.model.enums.Source;
import es.juanlsanchez.socialnetextractor.searchservice.repository.QueryRepository;
import es.juanlsanchez.socialnetextractor.searchservice.repository.SearchRepository;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.QueryEditDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("populate")
@WebAppConfiguration
public class QueryResourceTest extends Resolve {

  private MockMvc mockMvc;

  @Inject
  private WebApplicationContext webApplicationContext;
  @Inject
  private Populate populate;
  @Inject
  private QueryRepository queryRepository;
  @Inject
  private SearchRepository searchRepository;


  @Before
  public void init() {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
    populate.init();
  }

  @Test
  public void create_query() throws IOException, Exception {
    // Give
    String search = UUID.randomUUID().toString();
    Source source = Source.values()[new Random().nextInt(Source.values().length)];
    boolean active = new Random().nextBoolean();

    QueryEditDTO queryEditDTO = new QueryEditDTO(search, source, active);

    String url = resolve(QueryResource.URL);

    // When
    ResultActions createResponse =
        mockMvc.perform(post(url).content(TestUtilites.convertObjectToJsonBytes(queryEditDTO))
            .contentType(TestUtilites.CONTENT_TYPE_JSON));

    // Then
    createResponse.andExpect(status().isCreated());
    createResponse.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    createResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_ACTIVE, Matchers.equalTo(active)));
    createResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_SEARCH, Matchers.equalTo(search)));
    createResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_SOURCE,
        Matchers.equalTo(source.toString())));
    createResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_CREATED_AT, Matchers.notNullValue()));
    createResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_UPDATED_AT, Matchers.notNullValue()));
    createResponse
        .andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_ID, Matchers.notNullValue()));
  }

  @Test
  public void get_all_query() throws Exception {
    // Give
    String url = resolve(QueryResource.URL);
    Long numberOfQueries = queryRepository.count();

    // When
    ResultActions getResponse =
        mockMvc.perform(get(url).contentType(TestUtilites.CONTENT_TYPE_JSON));

    // Then
    getResponse.andExpect(status().isOk());
    getResponse.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + "totalElements",
        Matchers.equalTo(numberOfQueries.intValue())));
  }

  @Test
  public void get_all_query_by_search() throws Exception {
    // Give
    Search search = searchRepository.findAll().get(0);
    String url = resolve(QueryResource.urlSearchId(search.getId()));

    // When
    ResultActions getResponse =
        mockMvc.perform(get(url).contentType(TestUtilites.CONTENT_TYPE_JSON));

    // Then
    getResponse.andExpect(status().isOk());
    getResponse.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    getResponse.andExpect(jsonPath("$", Matchers.hasSize(search.getQueryIds().size())));
  }

  @Test
  public void get_a_query() throws Exception {
    // Give
    Query query = populate.getQueries().get(new Random().nextInt(populate.getQueries().size()));
    String url = resolve(QueryResource.urlId(query.getId()));

    // When
    ResultActions getResponse =
        mockMvc.perform(get(url).contentType(TestUtilites.CONTENT_TYPE_JSON));

    // Then
    getResponse.andExpect(status().isOk());
    getResponse.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_ACTIVE,
        Matchers.equalTo(query.isActive())));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_SEARCH,
        Matchers.equalTo(query.getSearch())));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_SOURCE,
        Matchers.equalTo(query.getSource().toString())));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_CREATED_AT,
        Matchers.equalTo(query.getCreatedAt().toString())));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_UPDATED_AT,
        Matchers.equalTo(query.getUpdatedAt().toString())));
    getResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_ID, Matchers.equalTo(query.getId())));
  }

  @Test
  public void put_a_query() throws Exception {
    // Give
    Query query = populate.getQueries().get(new Random().nextInt(populate.getQueries().size()));
    String url = resolve(QueryResource.urlId(query.getId()));

    String search = UUID.randomUUID().toString();
    Source source = Source.values()[new Random().nextInt(Source.values().length)];
    boolean active = new Random().nextBoolean();

    QueryEditDTO queryEditDTO = new QueryEditDTO(search, source, active);

    // When
    ResultActions getResponse =
        mockMvc.perform(put(url).content(TestUtilites.convertObjectToJsonBytes(queryEditDTO))
            .contentType(TestUtilites.CONTENT_TYPE_JSON));

    // Then
    getResponse.andExpect(status().isOk());
    getResponse.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    getResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_ACTIVE, Matchers.equalTo(active)));
    getResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_SEARCH, Matchers.equalTo(search)));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_SOURCE,
        Matchers.equalTo(source.toString())));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_CREATED_AT,
        Matchers.equalTo(query.getCreatedAt().toString())));
    getResponse.andExpect(jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_UPDATED_AT,
        Matchers.not(Matchers.equalTo(query.getUpdatedAt().toString()))));
    getResponse.andExpect(
        jsonPath(TestUtilites.CONTENT_SELECTOR + Query.J_ID, Matchers.equalTo(query.getId())));
  }

  @Test
  public void delete_a_query() throws Exception {
    // Give
    final String queryId =
        Lists.newArrayList(searchRepository.findAll().get(0).getQueryIds()).get(0);
    final String url = resolve(QueryResource.urlId(queryId));

    Long numberOfQueriesBefore = queryRepository.count();

    // When
    ResultActions getResponse =
        mockMvc.perform(delete(url).contentType(TestUtilites.CONTENT_TYPE_JSON));

    // Then
    getResponse.andExpect(status().isOk());
    assertThat(numberOfQueriesBefore - 1).isEqualTo(queryRepository.count());
    assertThat(searchRepository.findAll().stream()
        .anyMatch(search -> search.getQueryIds().contains(queryId))).isFalse();
  }

}
