package org.lafeuille.demo.movies.data;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface PersonRepository extends ReactiveNeo4jRepository<Person, String> {
}
