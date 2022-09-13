package org.lafeuille.demo.movies.web.api.v1;

import org.junit.jupiter.api.Test;
import org.lafeuille.demo.movies.services.MovieService;
import org.lafeuille.demo.movies.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(PeopleController.class)
class PeopleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonService service;

    @Test
    void get_movies() {
        when(service.findAll(any()))
                .thenReturn(Mono.just(new PageImpl<>(List.of())));

        webTestClient.get().uri("/api/v1/people")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content.length()").isEqualTo(0)
                .jsonPath("$.totalElements").isEqualTo(0);
    }

}
