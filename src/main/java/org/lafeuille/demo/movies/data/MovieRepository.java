package org.lafeuille.demo.movies.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveNeo4jRepository<Movie, String> {

    Flux<Movie> findBy(Pageable pageable);
}
