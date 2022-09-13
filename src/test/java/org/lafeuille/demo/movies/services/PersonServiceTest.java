package org.lafeuille.demo.movies.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lafeuille.demo.movies.data.Person;
import org.lafeuille.demo.movies.data.PersonRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Year;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonServiceTest {

    private PersonRepository repository;

    private PersonService service;

    @BeforeEach
    void setUp() {
        repository = mock(PersonRepository.class);
        service = new PersonService(repository);
    }

    @Test
    void get_people() {
        when(repository.findBy(any())).thenReturn(
                Flux.fromStream(IntStream.range(0, 5)
                        .mapToObj(i -> new Person("Person " + i, Year.of(2000 + i)))));
        when(repository.count()).thenReturn(Mono.just(10L));

        service.findAll(Pageable.ofSize(5))
                .as(StepVerifier::create)
                .assertNext(page -> {
                    assertThat(page).hasSize(5);
                    assertThat(page.getTotalPages()).isEqualTo(2);
                    assertThat(page.getTotalElements()).isEqualTo(10);
                })
                .verifyComplete();
    }

    @Test
    void get_people_with_null_pageable() {
        service.findAll(null)
                .as(StepVerifier::create)
                .verifyError(NullPointerException.class);
    }

}
