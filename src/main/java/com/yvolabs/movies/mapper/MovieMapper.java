package com.yvolabs.movies.mapper;

import com.yvolabs.movies.dto.MovieDto;
import com.yvolabs.movies.dto.MovieRequestDto;
import com.yvolabs.movies.dto.MovieResponseDto;
import com.yvolabs.movies.model.Movie;
import org.bson.types.ObjectId;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Movie createMovieFromDto(MovieRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    void updateMovieFromDto(MovieDto dto, @MappingTarget Movie entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    MovieResponseDto getMovieResponseFromMovie(Movie movie);

    @Named("objectIdToString")
    static String objectIdToString(ObjectId value) {
        return value.toString();
    }

    @Named("stringToObjectId")
    static ObjectId stringToObjectId(String value) {
        return new ObjectId(value);
    }

}
