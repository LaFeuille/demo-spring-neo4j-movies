package org.lafeuille.demo.movies.services;

import org.lafeuille.demo.movies.data.Movie;
import org.lafeuille.demo.movies.data.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

import static org.lafeuille.demo.movies.util.ReactivePreconditions.requireNonNull;

@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Mono<Page<Movie>> findAll(@NotNull Pageable pageable) {
        return requireNonNull(pageable).then(Mono.defer(() -> Mono.zip(
                repository.count(),
                repository.findBy(pageable).collectList(),
                (count, list) -> new PageImpl<>(list, pageable, count)
        )));
    }

    public Mono<Movie> findById(@NotNull String id) {
        return requireNonNull(id).then(Mono.defer(() -> repository.findById(id)));
    }
}
