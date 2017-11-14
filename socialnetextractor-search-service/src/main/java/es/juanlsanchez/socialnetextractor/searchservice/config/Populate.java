package es.juanlsanchez.socialnetextractor.searchservice.config;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import es.juanlsanchez.socialnetextractor.searchservice.model.Query;
import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.model.enums.Source;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.QueryEditDTO;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.SearchEditDTO;
import es.juanlsanchez.socialnetextractor.searchservice.web.manager.QueryManager;
import es.juanlsanchez.socialnetextractor.searchservice.web.manager.SearchManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("populate")
public class Populate {

  private static final int SEARCH_NUMBER = 20;
  private static final int QUERY_NUMBER = 20;
  private static final int MAX_NUMBER_OF_QUERY_BY_SEARCH = 7;
  private static final int MIN_NUMBER_OF_QUERY_BY_SEARCH = 1;

  private final QueryManager queryManager;
  private final SearchManager searchManager;
  @Getter
  private final List<Query> queries = Lists.newArrayList();
  @Getter
  private final List<Search> searchs = Lists.newArrayList();
  private boolean populate = false;

  public Populate(final QueryManager queryManager, final SearchManager searchManager) {
    this.queryManager = queryManager;
    this.searchManager = searchManager;

  }

  public void init() {
    if (!this.populate) {
      populate = true;
      log.info("Adding queries...");
      createQueries();
      log.info("Added {} queries", queries.size());
      List<String> queriesIds =
          queries.stream().map(query -> query.getId()).collect(Collectors.toList());
      log.info("Adding searchs...");
      createSearchs(queriesIds);
      log.info("Added {} searchs", searchs.size());
    }
  }

  private void createSearchs(List<String> queriesIds) {
    for (int i = 0; i < SEARCH_NUMBER; i++) {
      List<String> queriesIdsCopy = Lists.newArrayList(queriesIds);
      Set<String> queryIds = Sets.newHashSet();
      for (int j = 0; j < (new Random()
          .nextInt(MAX_NUMBER_OF_QUERY_BY_SEARCH - MIN_NUMBER_OF_QUERY_BY_SEARCH + 1)
          + MIN_NUMBER_OF_QUERY_BY_SEARCH); j++) {
        queryIds.add(queriesIdsCopy.remove(new Random().nextInt(queriesIdsCopy.size())));
      }
      searchs.add(searchManager.create(new SearchEditDTO(UUID.randomUUID().toString(), queryIds)));
    }
  }

  private void createQueries() {
    for (int i = 0; i < QUERY_NUMBER; i++) {
      queries.add(queryManager.create(new QueryEditDTO(UUID.randomUUID().toString(),
          Source.values()[new Random().nextInt(Source.values().length)],
          new Random().nextBoolean())));
    }
  }

}
