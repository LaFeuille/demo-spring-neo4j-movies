package org.lafeuille.demo.movies.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

class PersonRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void find_all() {
        repository.findAll()
                .as(StepVerifier::create)
                .verifyComplete();
    }

}
