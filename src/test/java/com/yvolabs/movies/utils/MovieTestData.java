package com.yvolabs.movies.utils;

import com.yvolabs.movies.dto.MovieDto;
import com.yvolabs.movies.dto.MovieRequestDto;
import com.yvolabs.movies.dto.MovieResponseDto;
import com.yvolabs.movies.mapper.MovieMapper;
import com.yvolabs.movies.model.Movie;
import org.bson.types.ObjectId;

import java.util.List;

public class MovieTestData {
    public static List<MovieResponseDto> getMoviesResponse() {

        MovieResponseDto movie1 = MovieResponseDto.builder()
                .id("someId")
                .imdbId("abc123")
                .title("someTitle")
                .releaseDate("someDate")
                .trailerLink("someTrailerLink")
                .poster("somePoster")
                .build();

        MovieResponseDto movie2 = MovieResponseDto.builder()
                .id("someId2")
                .imdbId("abc123_2")
                .title("someTitle_2")
                .releaseDate("someDate_2")
                .trailerLink("someTrailerLink_2")
                .poster("somePoster_2")
                .build();

        return List.of(movie1, movie2);
    }

    public static MovieResponseDto getSingleMovieResponse() {
        return MovieResponseDto.builder()
                .id("someId")
                .imdbId("abc123")
                .title("someTitle")
                .releaseDate("someDate")
                .trailerLink("someTrailerLink")
                .poster("somePoster")
                .build();
    }

    public static Movie newMovie() {
        MovieRequestDto movieRequestDto = newMovieRequest();
        Movie movie = MovieMapper.INSTANCE.createMovieFromDto(movieRequestDto);
        movie.setId(new ObjectId());
        return movie;
    }

    public static MovieRequestDto newMovieRequest() {
        return MovieRequestDto.builder()
                .imdbId("new123")
                .title("newSomeTitle")
                .releaseDate("newSomeDate")
                .trailerLink("newSomeTrailerLink")
                .poster("newSomePoster")
                .build();

    }


    public static MovieDto updateMovieRequest() {
        return MovieDto.builder()
                .imdbId("update123")
                .title("updatedTitle")
                .releaseDate("updatedDate")
                .trailerLink("updatedTrailerLink")
                .poster("updatedPoster")
                .build();
    }
}
