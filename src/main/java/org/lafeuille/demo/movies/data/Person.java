package org.lafeuille.demo.movies.data;

import org.lafeuille.demo.movies.infra.data.neo4j.YearNeo4jPersistentPropertyConverter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Year;

public record Person(@NotBlank @Id String name,
                     @NotNull @ConvertWith(converter = YearNeo4jPersistentPropertyConverter.class) Year born) {
}
