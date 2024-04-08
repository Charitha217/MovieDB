package com.example.MovieDB.Controller;

import com.example.MovieDB.Model.Movie;
import com.example.MovieDB.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService; // MovieRepository handles CRUD operations

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "add";
    }

    @GetMapping("/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMovie(@PathVariable("id") Long id, @ModelAttribute Movie updatedMovie) {
        movieService.updateMovie(id, updatedMovie);
        return "redirect:/movies";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movie") Movie movie) {
        // Add validation and save the movie using the movieService
        movieService.addMovie(movie);
        return "redirect:/movies"; // Redirect to the movies page after adding the movie
    }
    
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

    @GetMapping("/rate/{id}")
    public String enterRating(@PathVariable("id") Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "rate";
    }

    @PostMapping("/rate/{id}")
    public String rateMovie(@PathVariable Long id, @RequestParam("rating") double rating) {
        movieService.rateMovie(id, rating);
        return "redirect:/movies";
    }
}
