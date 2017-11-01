package es.juanlsanchez.socialnetextractor.searchservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.juanlsanchez.socialnetextractor.searchservice.model.Search;

@Repository
public interface SearchRepository extends MongoRepository<Search, String> {

  public List<Search> findAllByQueryIds(String queryId);

}
