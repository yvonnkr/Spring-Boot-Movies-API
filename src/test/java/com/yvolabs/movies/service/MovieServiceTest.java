package com.yvolabs.movies.service;

import com.yvolabs.movies.dto.MovieDto;
import com.yvolabs.movies.dto.MovieRequestDto;
import com.yvolabs.movies.dto.MovieResponseDto;
import com.yvolabs.movies.model.Movie;
import com.yvolabs.movies.utils.MovieTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieService movieService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGetMovies() {
        when(movieService.getMovies())
                .thenReturn(MovieTestData.getMoviesResponse());

        List<MovieResponseDto> movies = movieService.getMovies();

        verify(movieService, times(1)).getMovies();
        assertEquals(2, movies.size());
        assertEquals("someId", movies.get(0).getId());
        assertEquals("someId2", movies.get(1).getId());

    }

    @Test
    void shouldGetMovie() {
        String movieId = "someId";
        when(movieService.getMovie(movieId))
                .thenReturn(MovieTestData.getSingleMovieResponse());

        MovieResponseDto movie = movieService.getMovie(movieId);

        assertNotNull(movie);
        verify(movieService, times(1)).getMovie(movieId);
        assertEquals("someId", movie.getId());
        assertEquals("abc123", movie.getImdbId());

    }

    @Test
    void shouldNotGetMovieIfNotFound() {
        String movieId = "wrongId";
        when(movieService.getMovie(movieId))
                .thenReturn(null);

        MovieResponseDto movie = movieService.getMovie(movieId);

        verify(movieService, times(1)).getMovie(movieId);
        assertNull(movie);

    }

    @Test
    void shouldCreateMovie() {
        MovieRequestDto movieRequest = MovieTestData.newMovieRequest();
        Movie newMovie = MovieTestData.newMovie();
        when(movieService.createMovie(movieRequest))
                .thenReturn(newMovie);

        Movie movie = movieService.createMovie(movieRequest);

        verify(movieService, times(1)).createMovie(movieRequest);
        assertNotNull(movie);
        assertEquals(newMovie.getId(), movie.getId());
        assertEquals("new123", movie.getImdbId());
        assertEquals("newSomeDate", movie.getReleaseDate());


    }

    @Test
    void shouldUpdateMovie() {
        String movieId = "someId";
        MovieDto movieRequest = MovieTestData.updateMovieRequest();
        MovieResponseDto movieResponse = MovieTestData.getSingleMovieResponse();
        when(movieService.updateMovie(movieId, movieRequest))
                .thenReturn(movieResponse);

        MovieResponseDto updatedMovie = movieService.updateMovie(movieId, movieRequest);

        verify(movieService, times(1)).updateMovie(movieId, movieRequest);
        assertNotNull(updatedMovie);
        assertEquals("abc123", updatedMovie.getImdbId());
        assertEquals("someDate", updatedMovie.getReleaseDate());
    }

    @Test
    void deleteMovie() {
        String movieId = "someId";
        movieService.deleteMovie(movieId);

        verify(movieService).deleteMovie(movieId);
    }
}