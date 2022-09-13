package org.lafeuille.demo.movies.services;

import org.lafeuille.demo.movies.data.Movie;
import org.lafeuille.demo.movies.data.Person;
import org.lafeuille.demo.movies.data.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

import static org.lafeuille.demo.movies.util.ReactivePreconditions.requireNonNull;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Mono<Page<Person>> findAll(@NotNull Pageable pageable) {
        return requireNonNull(pageable).then(Mono.defer(() -> Mono.zip(
                repository.count(),
                repository.findBy(pageable).collectList(),
                (count, list) -> new PageImpl<>(list, pageable, count)
        )));
    }

    public Mono<Person> findById(@NotNull String id) {
        return requireNonNull(id).then(Mono.defer(() -> repository.findById(id)));
    }
}
