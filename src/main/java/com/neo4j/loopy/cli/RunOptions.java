package com.neo4j.loopy.cli;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Option;

/**
 * All options accepted by the {@code run} command (and, via mixin, the bare {@code loopy}
 * invocation). Grouped into nested {@code @ArgGroup} sections purely for headed display in
 * {@code --help}; groups are non-exclusive so any combination of options may be supplied.
 */
public class RunOptions {

    @Option(names = {"--config", "-c"}, description = "Configuration file path")
    private String configFile;

    @ArgGroup(exclusive = false, heading = "%nConnection:%n")
    private ConnectionOptions connection = new ConnectionOptions();

    @ArgGroup(exclusive = false, heading = "%nExecution:%n")
    private ExecutionGroup execution = new ExecutionGroup();

    @ArgGroup(exclusive = false, heading = "%nYAML Workload:%n")
    private WorkloadGroup workload = new WorkloadGroup();

    @ArgGroup(exclusive = false, heading = "%nTransaction Mode:%n")
    private TransactionGroup transaction = new TransactionGroup();

    @ArgGroup(exclusive = false, heading = "%nOutput & Reporting:%n")
    private OutputGroup output = new OutputGroup();

    public static class ExecutionGroup {
        @Option(names = {"--threads", "-t"},
                description = "Number of worker threads (1-100)",
                defaultValue = "${LOOPY_THREADS:-4}")
        private Integer threads;

        @Option(names = {"--duration", "-d"},
                description = "Test duration in seconds (minimum 1)",
                defaultValue = "${LOOPY_DURATION:-300}")
        private Integer duration;

        @Option(names = {"--write-ratio", "-w"},
                description = "Write operation ratio (0.0-1.0)",
                defaultValue = "0.7")
        private Double writeRatio;

        @Option(names = {"--batch-size", "-b"},
                description = "Batch size for operations (minimum 1)",
                defaultValue = "100")
        private Integer batchSize;

        @Option(names = {"--node-labels", "-n"}, description = "Comma-separated node labels")
        private String nodeLabels;

        @Option(names = {"--relationship-types", "-r"}, description = "Comma-separated relationship types")
        private String relationshipTypes;

        @Option(names = {"--property-size"},
                description = "Property size in bytes (minimum 1)",
                defaultValue = "1024")
        private Integer propertySize;
    }

    public static class WorkloadGroup {
        @Option(names = {"--cypher-file", "-f"},
                description = "Path to YAML workload file containing Cypher queries")
        private String cypherFile;

        @Option(names = {"--dry-run"},
                description = "Validate YAML and test connection without executing workload")
        private boolean dryRun = false;

        @Option(names = {"--fail-fast"},
                description = "Abort on first query failure (default: continue with next query)")
        private boolean failFast = false;

        @Option(names = {"--verbose-stats"},
                description = "Enable per-query statistics (default: aggregated only)")
        private boolean verboseStats = false;
    }

    public static class TransactionGroup {
        @Option(names = {"--transaction-mode", "-m"},
                description = "Transaction mode: auto-commit, explicit, managed-read, managed-write, execute-query",
                defaultValue = "${LOOPY_TRANSACTION_MODE:-auto-commit}")
        private String transactionMode;

        @Option(names = {"--transaction-group-size", "-g"},
                description = "Number of operations grouped into a single explicit/managed transaction (default: 1, no grouping). Applies to programmatic (non-YAML) mode.",
                defaultValue = "${LOOPY_TRANSACTION_GROUP_SIZE:-1}")
        private Integer transactionGroupSize;
    }

    public static class OutputGroup {
        @Option(names = {"--report-interval"},
                description = "Statistics reporting interval in seconds (minimum 1)",
                defaultValue = "10")
        private Integer reportInterval;

        @Option(names = {"--csv-logging"}, description = "Enable CSV logging")
        private Boolean csvLogging;

        @Option(names = {"--csv-file"}, description = "CSV output file path")
        private String csvFile;

        @Option(names = {"--stats-format"},
                description = "Statistics output format: summary, detailed, json",
                defaultValue = "summary")
        private String statsFormat;

        @Option(names = {"--quiet", "-q"}, description = "Quiet mode - minimal output")
        private boolean quiet = false;

        @Option(names = {"--verbose", "-v"}, description = "Verbose mode - detailed output")
        private boolean verbose = false;
    }

    public String getConfigFile() { return configFile; }
    public String getNeo4jUri() { return connection.getNeo4jUri(); }
    public String getUsername() { return connection.getUsername(); }
    public String getPassword() { return connection.getPassword(); }

    public Integer getThreads() { return execution.threads; }
    public Integer getDuration() { return execution.duration; }
    public Double getWriteRatio() { return execution.writeRatio; }
    public Integer getBatchSize() { return execution.batchSize; }
    public String getNodeLabels() { return execution.nodeLabels; }
    public String getRelationshipTypes() { return execution.relationshipTypes; }
    public Integer getPropertySize() { return execution.propertySize; }

    public String getCypherFile() { return workload.cypherFile; }
    public boolean isDryRun() { return workload.dryRun; }
    public boolean isFailFast() { return workload.failFast; }
    public boolean isVerboseStats() { return workload.verboseStats; }

    public String getTransactionMode() { return transaction.transactionMode; }
    public Integer getTransactionGroupSize() { return transaction.transactionGroupSize; }

    public Integer getReportInterval() { return output.reportInterval; }
    public Boolean getCsvLogging() { return output.csvLogging; }
    public String getCsvFile() { return output.csvFile; }
    public String getStatsFormat() { return output.statsFormat; }
    public boolean isQuiet() { return output.quiet; }
    public boolean isVerbose() { return output.verbose; }
}
