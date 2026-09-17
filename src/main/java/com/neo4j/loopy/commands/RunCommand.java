package com.neo4j.loopy.commands;

import com.neo4j.loopy.LoopyApplication;
import com.neo4j.loopy.cli.RunOptions;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.ParentCommand;

import java.util.concurrent.Callable;

/**
 * Run command - Execute load test (default behavior)
 */
@Command(name = "run",
         description = "Execute Neo4j load test",
         mixinStandardHelpOptions = true,
         showDefaultValues = true,
         sortOptions = false,
         footer = {
             "%nExamples:",
             "  loopy run -a bolt://localhost:7687 -u neo4j -p password -t 4 -D 60",
             "  loopy run --cypher-file=example-workload.yaml -a bolt://localhost:7687 -u neo4j -p password",
             "  loopy run -m managed-write -g 5 -t 4 -D 60 -a bolt://localhost:7687 -u neo4j -p password",
             "  loopy run -t 4 -D 300 --csv-logging --csv-file=results.csv -a bolt://localhost:7687 -u neo4j -p password",
             "  loopy run -t 4 -D 300 --stats-format=json -a bolt://localhost:7687 -u neo4j -p password"
         })
public class RunCommand implements Callable<Integer> {

    @ParentCommand
    private LoopyApplication parent;

    @Mixin
    private RunOptions options;

    @Override
    public Integer call() throws Exception {
        // Use this subcommand's own parsed options, not the parent's (unset when args follow "run")
        return parent.execute(options);
    }
}