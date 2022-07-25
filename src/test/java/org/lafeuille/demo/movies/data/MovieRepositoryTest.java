package org.lafeuille.demo.movies.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.testcontainers.containers.BindMode.READ_ONLY;

@SpringBootTest // @DataNeo4jTest
@Testcontainers
class MovieRepositoryTest {

    private static final String CONTAINER_CYPHER_FILE_PATH = "/tmp/data.cypher";

    private static final String NEO4J = "neo4j";

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.4")
            .withoutAuthentication()
            .withClasspathResourceMapping("scripts/movies.cypher", CONTAINER_CYPHER_FILE_PATH, READ_ONLY);

    @Autowired
    private MovieRepository repository;

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
    void find_all() {
        repository.findAll()
                .collectList()
                .as(StepVerifier::create)
                .assertNext(movies ->
                        assertThat(movies).hasSize(38))
                .verifyComplete();
    }

}
