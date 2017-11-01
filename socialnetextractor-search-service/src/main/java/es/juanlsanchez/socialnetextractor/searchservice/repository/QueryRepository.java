package es.juanlsanchez.socialnetextractor.searchservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.juanlsanchez.socialnetextractor.searchservice.model.Query;

@Repository
public interface QueryRepository extends MongoRepository<Query, String> {

}
