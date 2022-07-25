package org.lafeuille.demo.movies.infra.data.neo4j;

import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

import java.time.Year;

public class YearNeo4jPersistentPropertyConverter implements Neo4jPersistentPropertyConverter<Year> {
    @Override
    public Value write(Year source) {
        return source == null ? Values.NULL : Values.value(source.getValue());
    }

    @Override
    public Year read(Value source) {
        return source.isNull() ? null : Year.of(source.asInt());
    }
}
