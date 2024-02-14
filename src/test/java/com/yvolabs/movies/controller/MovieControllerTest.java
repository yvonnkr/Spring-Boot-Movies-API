package com.yvolabs.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yvolabs.movies.dto.MovieDto;
import com.yvolabs.movies.dto.MovieRequestDto;
import com.yvolabs.movies.model.Movie;
import com.yvolabs.movies.service.MovieService;
import com.yvolabs.movies.utils.MovieTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MovieController.class)
class MovieControllerTest {
    private static final String PATH = "/api/v1/movies";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;


    @Test
    void should_getMovies() throws Exception {
        given(movieService.getMovies())
                .willReturn(MovieTestData.getMoviesResponse());

        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("someId"))
                .andExpect(jsonPath("$[1].id").value("someId2"));

        verify(movieService).getMovies();
    }


    @Test
    void should_getMovieById() throws Exception {
        String movieId = "someId";
        given(movieService.getMovie(movieId))
                .willReturn(MovieTestData.getSingleMovieResponse());

        mockMvc.perform(get(PATH + "/" + movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("someId"))
                .andExpect(jsonPath("title").value("someTitle"));

        verify(movieService).getMovie(movieId);
    }

    @Test
    void should_return404WhenGetMovieByIdIsNotFound() throws Exception {
        String movieId = "notFoundId";
        given(movieService.getMovie(movieId))
                .willReturn(null);

        mockMvc.perform(get(PATH + "/" + movieId))
                .andExpect(status().isNotFound());

        verify(movieService).getMovie(movieId);
    }

    @Test
    void should_createMovie() throws Exception {
        Movie newMovie = MovieTestData.newMovie();
        given(movieService.createMovie(any(MovieRequestDto.class)))
                .willReturn(newMovie);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newMovie)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost" + PATH + "/" + newMovie.getId()));


        verify(movieService).createMovie(any(MovieRequestDto.class));
    }


    @Test
    void should_deleteMovieById() throws Exception {
        String movieId = "someId";
        String deletePath = PATH + "/{id}";
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(deletePath, movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(movieService).deleteMovie(movieId);
    }

    @Test
    void should_updateMovie() throws Exception {
        String movieId = "someId";
        String updatePath = PATH + "/{id}";

        MovieDto updateMovieRequest = MovieTestData.updateMovieRequest();
        given(movieService.updateMovie(movieId, updateMovieRequest))
                .willReturn(MovieTestData.getSingleMovieResponse());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(updatePath, movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateMovieRequest))
                )
                .andExpect(status().isOk());

        verify(movieService).updateMovie(movieId, updateMovieRequest);

    }

    @Test
    void should_return404IfMovieNotFoundWhenUpdatingAMovie() throws Exception {
        String movieId = "someId";
        String updatePath = PATH + "/{id}";

        MovieDto updateMovieRequest = MovieTestData.updateMovieRequest();
        given(movieService.updateMovie(movieId, updateMovieRequest))
                .willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(updatePath, movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateMovieRequest))
                )
                .andExpect(status().isNotFound());

        verify(movieService).updateMovie(movieId, updateMovieRequest);

    }

    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}