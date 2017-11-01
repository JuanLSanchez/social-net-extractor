package es.juanlsanchez.socialnetextractor.searchservice.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import es.juanlsanchez.socialnetextractor.searchservice.model.Query;
import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.QueryEditDTO;

@Mapper(componentModel = "spring")
public interface QueryMapper {

  @Mappings({@Mapping(target = Search.J_ID, ignore = true),
      @Mapping(target = Search.J_CREATED_AT, ignore = true),
      @Mapping(target = Search.J_UPDATED_AT, ignore = true)})
  public Query fromQueryEditDTO(QueryEditDTO queryEditDTO);

  @Mappings({@Mapping(target = Search.J_ID, ignore = true),
      @Mapping(target = Search.J_CREATED_AT, ignore = true),
      @Mapping(target = Search.J_UPDATED_AT, ignore = true)})
  public void updateQueryEditDTO(QueryEditDTO queryEditDTO, @MappingTarget Query query);

}
