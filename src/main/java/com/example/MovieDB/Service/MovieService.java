package com.example.MovieDB.Service;

import com.example.MovieDB.Model.Movie;
import com.example.MovieDB.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        Iterable<Movie> movieIterable = movieRepository.findAll();
        return StreamSupport.stream(movieIterable.spliterator(), false)
                            .collect(Collectors.toList());
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElse(null);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        // private int releaseYear;
        // private String genre;
        // private String director;
        // private double averageRating;
        Movie existingMovie = getMovieById(id);
        if (existingMovie != null) {
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setGenre(updatedMovie.getGenre());
            existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
            existingMovie.setDirector(updatedMovie.getDirector());
            //existingMovie.setAverageRating(updatedMovie.getAverageRating());
            return movieRepository.save(existingMovie);
        }
        return null;
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public void rateMovie(Long id, double rating) {
        Movie existingMovie = getMovieById(id);
        if (existingMovie != null) {
            double new_rating=(existingMovie.getAverageRating()*existingMovie.getRatingCount() + rating)/(existingMovie.getRatingCount()+1);
            new_rating=Math.round(new_rating * 10.0) / 10.0;
            existingMovie.setAverageRating(new_rating);
            existingMovie.setRatingCount(existingMovie.getRatingCount()+1);
            movieRepository.save(existingMovie);
        }
    }
}
