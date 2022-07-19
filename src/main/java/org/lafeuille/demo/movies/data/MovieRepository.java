package org.lafeuille.demo.movies.data;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface MovieRepository extends ReactiveNeo4jRepository<Movie, String> {
}
