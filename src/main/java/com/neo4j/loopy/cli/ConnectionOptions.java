package com.neo4j.loopy.cli;

import picocli.CommandLine.Help.Visibility;
import picocli.CommandLine.Option;

/**
 * Shared Neo4j connection options, reused as a flat {@code @Mixin} (Benchmark/TestConnection)
 * or as a headed {@code @ArgGroup} (RunOptions).
 */
public class ConnectionOptions {

    @Option(names = {"--neo4j-uri", "-a"},
            description = "Neo4j connection URI (supports bolt://, neo4j://, bolt+s://, neo4j+s://, bolt+ssc://, neo4j+ssc://)",
            defaultValue = "${LOOPY_NEO4J_URI:-neo4j://localhost:7687}")
    private String neo4jUri;

    @Option(names = {"--username", "-u"},
            description = "Neo4j username",
            defaultValue = "${LOOPY_USERNAME:-neo4j}")
    private String username;

    @Option(names = {"--password", "-p"},
            description = "Neo4j password",
            interactive = true,
            arity = "0..1",
            showDefaultValue = Visibility.NEVER,
            defaultValue = "${LOOPY_PASSWORD:-password}")
    private String password;

    @Option(names = {"--database", "-d"},
            description = "Neo4j database name to connect to",
            defaultValue = "${LOOPY_DATABASE:-neo4j}")
    private String database;

    public String getNeo4jUri() { return neo4jUri; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getDatabase() { return database; }
}
