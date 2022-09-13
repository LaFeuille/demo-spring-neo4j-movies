package org.lafeuille.demo.movies.util;

import reactor.core.publisher.Mono;

public enum ReactivePreconditions {
    ;

    public static <T> Mono<T> requireNonNull(T object) {
        if (object == null) {
            return Mono.error(NullPointerException::new);
        }
        return Mono.just(object);
    }
}
