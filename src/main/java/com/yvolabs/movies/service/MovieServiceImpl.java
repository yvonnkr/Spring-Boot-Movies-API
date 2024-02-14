package com.yvolabs.movies.service;

import com.yvolabs.movies.dto.MovieDto;
import com.yvolabs.movies.dto.MovieRequestDto;
import com.yvolabs.movies.dto.MovieResponseDto;
import com.yvolabs.movies.model.Movie;
import com.yvolabs.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yvolabs.movies.mapper.MovieMapper.INSTANCE;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<MovieResponseDto> getMovies() {
        List<Movie> movies = movieRepository.findAll();

        return movies.stream()
                .map(INSTANCE::getMovieResponseFromMovie)
                .toList();

    }

    @Override
    public MovieResponseDto getMovie(String id) {
        Optional<Movie> movieOpt = movieRepository.findById(new ObjectId(id));
        return movieOpt.map(INSTANCE::getMovieResponseFromMovie).orElse(null);
    }

    @Override
    public Movie createMovie(MovieRequestDto movieRequestDto) {
        Movie movie = INSTANCE.createMovieFromDto(movieRequestDto);
        movieRepository.save(movie);
        log.info("Movie {} saved", movie.getId());
        return movie;

    }

    @Override
    public MovieResponseDto updateMovie(String movieId, MovieDto movieDto) {
        Optional<Movie> movie = movieRepository.findById(new ObjectId(movieId));
        if (movie.isPresent()) {
            INSTANCE.updateMovieFromDto(movieDto, movie.get());
            movieRepository.save(movie.get());

            return INSTANCE.getMovieResponseFromMovie(movie.get());
        }

        return null;
    }

    @Override
    public void deleteMovie(String id) {
        movieRepository.deleteById(new ObjectId(id));
    }


}
