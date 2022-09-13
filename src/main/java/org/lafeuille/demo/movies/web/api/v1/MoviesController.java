package org.lafeuille.demo.movies.web.api.v1;

import org.lafeuille.demo.movies.data.Movie;
import org.lafeuille.demo.movies.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/movies")
public class MoviesController {

    private final MovieService service;

    public MoviesController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<Page<Movie>> getMovies(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("{id}")
    public Mono<Movie> getMovie(@PathVariable String id) {
        return service.findById(id);
    }
}
