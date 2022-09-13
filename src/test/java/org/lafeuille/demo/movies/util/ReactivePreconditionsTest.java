package org.lafeuille.demo.movies.util;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class ReactivePreconditionsTest {

    @Test
    void require_non_null_on_null_gives_error() {
        ReactivePreconditions.requireNonNull(null)
                .as(StepVerifier::create)
                .verifyError(NullPointerException.class);
    }

    @Test
    void require_non_null_on_string_gives_stuff() {
        ReactivePreconditions.requireNonNull("stuff")
                .as(StepVerifier::create)
                .assertNext(value -> assertThat(value).isEqualTo("stuff"))
                .verifyComplete();
    }
}
