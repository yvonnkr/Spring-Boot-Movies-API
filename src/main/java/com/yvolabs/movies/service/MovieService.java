package com.yvolabs.movies.service;

import com.yvolabs.movies.dto.MovieDto;
import com.yvolabs.movies.dto.MovieRequestDto;
import com.yvolabs.movies.dto.MovieResponseDto;
import com.yvolabs.movies.model.Movie;

import java.util.List;

public interface MovieService {

    List<MovieResponseDto> getMovies();

    MovieResponseDto getMovie(String id);

    Movie createMovie(MovieRequestDto movieRequest);

    MovieResponseDto updateMovie(String movieId, MovieDto movie);

    void deleteMovie(String id);
}
