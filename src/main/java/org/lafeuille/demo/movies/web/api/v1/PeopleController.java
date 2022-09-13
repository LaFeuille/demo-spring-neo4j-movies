package org.lafeuille.demo.movies.web.api.v1;

import org.lafeuille.demo.movies.data.Movie;
import org.lafeuille.demo.movies.data.Person;
import org.lafeuille.demo.movies.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/people")
public class PeopleController {

    private final PersonService service;

    public PeopleController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<Page<Person>> getPeople(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("{id}")
    public Mono<Person> getPerson(@PathVariable String id) {
        return service.findById(id);
    }
}
