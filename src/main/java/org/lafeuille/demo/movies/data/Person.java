package org.lafeuille.demo.movies.data;

import org.springframework.data.neo4j.core.schema.Id;

import javax.validation.constraints.NotNull;
import java.time.Year;

public record Person(@NotNull @Id String name, @NotNull Year born) {
}
