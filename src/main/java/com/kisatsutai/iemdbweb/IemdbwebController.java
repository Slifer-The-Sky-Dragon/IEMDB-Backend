package com.kisatsutai.iemdbweb;

import base.Iemdb.Iemdb;
import base.Models.Actor;
import base.Models.Movie;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
public class IemdbwebController {
    private final Iemdb iemdb = new Iemdb();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return iemdb.getMoviesList();
    }

    @GetMapping("/movies/{id}")
    public Movie getMovieById(@PathVariable String id) {
        return iemdb.getMovieById(Integer.parseInt(id));
    }

    @GetMapping("/actors")
    public List<Actor> getActors() {
        return iemdb.getActorsList();
    }

    @GetMapping("/actors/{id}")
    public Actor GetActorById(@PathVariable String id) {
        return iemdb.getActorById(Integer.parseInt(id));
    }

    @GetMapping("/actors/{id}/movies")
    public List<Movie> GetActorMovies(@PathVariable String id) { return iemdb.getActorMovies(Integer.parseInt(id)); }
}
