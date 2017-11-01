package es.juanlsanchez.socialnetextractor.searchservice.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import es.juanlsanchez.socialnetextractor.searchservice.model.Search;
import es.juanlsanchez.socialnetextractor.searchservice.web.dto.SearchEditDTO;

@Mapper(componentModel = "spring")
public interface SearchMapper {

  @Mappings({@Mapping(target = Search.J_ID, ignore = true),
      @Mapping(target = Search.J_CREATED_AT, ignore = true),
      @Mapping(target = Search.J_UPDATED_AT, ignore = true)})
  public Search fromSearchEditDTO(SearchEditDTO searchEditDTO);

  @Mappings({@Mapping(target = Search.J_ID, ignore = true),
      @Mapping(target = Search.J_CREATED_AT, ignore = true),
      @Mapping(target = Search.J_UPDATED_AT, ignore = true)})
  public void updateFromSearchEditDTO(SearchEditDTO searchEditDTO, @MappingTarget Search search);

}
