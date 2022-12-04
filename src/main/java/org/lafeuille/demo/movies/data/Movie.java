package org.lafeuille.demo.movies.data;

import org.lafeuille.demo.movies.infra.data.neo4j.YearNeo4jPersistentPropertyConverter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Year;

@Node
public record Movie(@NotBlank @Id String title,
                    @NotNull @ConvertWith(converter = YearNeo4jPersistentPropertyConverter.class) Year released) {
}
