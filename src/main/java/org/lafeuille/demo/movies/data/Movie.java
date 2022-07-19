package org.lafeuille.demo.movies.data;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public record Movie(@Id String title) {
}
