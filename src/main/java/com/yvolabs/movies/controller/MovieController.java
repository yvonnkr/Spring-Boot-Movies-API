package com.yvolabs.movies.controller;

import com.yvolabs.movies.dto.MovieDto;
import com.yvolabs.movies.dto.MovieRequestDto;
import com.yvolabs.movies.dto.MovieResponseDto;
import com.yvolabs.movies.model.Movie;
import com.yvolabs.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MovieResponseDto>> getMovies() {

        List<MovieResponseDto> movies = movieService.getMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable String id) {

        MovieResponseDto movie = movieService.getMovie(id);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createMovie(@RequestBody MovieRequestDto movieRequestDto) {
        Movie movie = movieService.createMovie(movieRequestDto);
        return entityWithLocation(movie.getId());

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponseDto> updateMovie(@PathVariable String id, @RequestBody MovieDto updateRequest) {
        MovieResponseDto updatedMovie = movieService.updateMovie(id, updateRequest);
        return updatedMovie != null ? ResponseEntity.ok(updatedMovie) : ResponseEntity.notFound().build();

    }

    private ResponseEntity<Void> entityWithLocation(Object resourceId) {

        // Determines URL of child resource based on the full URL of the given
        // request, appending the path info with the given resource Identifier
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(resourceId)
                .toUri();

        // Return an HttpEntity object - it will be used to build the
        // HttpServletResponse
        return ResponseEntity.created(location).build();
    }


}
