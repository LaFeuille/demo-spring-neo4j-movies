package org.lafeuille.demo.movies;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.testcontainers.containers.BindMode.READ_ONLY;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
class ApplicationTest {

    private static final String CONTAINER_CYPHER_FILE_PATH = "/tmp/data.cypher";

    private static final String NEO4J = "neo4j";

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.4")
            .withoutAuthentication()
            .withClasspathResourceMapping("scripts/movies.cypher", CONTAINER_CYPHER_FILE_PATH, READ_ONLY);

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> NEO4J);
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @BeforeAll
    static void setUp() throws IOException, InterruptedException {
        var execResult = neo4jContainer.execInContainer("./bin/cypher-shell", "-u", NEO4J, "-f", CONTAINER_CYPHER_FILE_PATH);
        assumeThat(execResult.getExitCode()).isEqualTo(0);
    }

    @Test
    void get_movies() {
        webTestClient.get().uri("/api/v1/movies")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isMap()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content.length()").isEqualTo(20)
                .jsonPath("$.content[0].title").isEqualTo("The Matrix")
                .jsonPath("$.content[0].released").isEqualTo("1999")
                .jsonPath("$.pageable").isMap()
                .jsonPath("$.totalPages").isEqualTo(2)
                .jsonPath("$.totalElements").isEqualTo(38)
                .jsonPath("$.last").isEqualTo(false)
                .jsonPath("$.size").isEqualTo(20)
                .jsonPath("$.number").isEqualTo(0);
    }

    @Test
    void get_people() {
        webTestClient.get().uri("/api/v1/people")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isMap()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content.length()").isEqualTo(20)
                .jsonPath("$.content[0].name").isEqualTo("Keanu Reeves")
                .jsonPath("$.content[0].born").isEqualTo("1964")
                .jsonPath("$.pageable").isMap()
                .jsonPath("$.totalPages").isEqualTo(7)
                .jsonPath("$.totalElements").isEqualTo(133)
                .jsonPath("$.last").isEqualTo(false)
                .jsonPath("$.size").isEqualTo(20)
                .jsonPath("$.number").isEqualTo(0);
    }

}
