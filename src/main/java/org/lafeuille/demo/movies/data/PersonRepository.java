package org.lafeuille.demo.movies.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveNeo4jRepository<Person, String> {

    Flux<Person> findBy(Pageable pageable);
}
