package com.fizal.xunit.dbunit.util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.fizal.xunit.dbunit.util.StringUtil.*;
import static java.io.File.separator;
import static java.nio.charset.Charset.defaultCharset;

/**
 * This program takes in a schema-extract file as input (provided by DBAs) which contain DDL for all
 * the tables, indices, constraints, etc and an output directory where the SQL files will be written.
 * <p/>
 * After parsing and processing, it spits out one SQL file per table - each file named in the format :
 * <schema>.<table>.sql
 * <p/>
 * Created by fmohamed on 9/14/2016.
 */
public final class SchemaExtractSplitter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaExtractSplitter.class);

    @Parameter(
            names = {"-i", "--schemaFile"},
            description = "Input schema extract file to process",
            required = true
    )
    private String schemaExtractFile;

    @Parameter(
            names = {"-o", "--outputDir"},
            description = "Output directory where the SQL files will be created",
            required = true
    )
    private String outputDir;

    public static void main(String[] args) {
        SchemaExtractSplitter util = new SchemaExtractSplitter();
        util.parseCommandLineArgs(args);

        Map<String, List<String>> commandGroup = util.parse();
        util.createOutputFiles(commandGroup);
    }

    protected void parseCommandLineArgs(String[] args) {
        JCommander jCommander = new JCommander(this);
        try {
            jCommander.parse(args);
        } catch (Exception e) {
            StringBuilder builder = new StringBuilder();
            jCommander.usage(builder);
            throw new RuntimeException(builder.toString(), e);
        }
    }

    protected Map<String, List<String>> parse() {
        String fileContent = getFileContent(getSchemaExtractFile());
        List<String> commands = splitCommands(fileContent);

        return groupCommandsByTable(commands);
    }

    protected void createOutputFiles(Map<String, List<String>> commandGroup) {
        String outputDir = getOutputDir();
        File file = new File(outputDir);
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("Output directory does not exist or is not a directory: " + outputDir);
        }

        for (Map.Entry<String, List<String>> entry : commandGroup.entrySet()) {
            String tableName = entry.getKey();
            tableName = tableName.replace("\"", "");
            LOGGER.info("--------------------{}--------------------", tableName);

            File outputFile = new File(outputDir + separator + tableName + ".sql");

            try {
                String outputFileContent = mergeCommands(entry.getValue());

                LOGGER.info("Table: {} File: {}", tableName, outputFile);

                FileUtils.writeStringToFile(outputFile, outputFileContent, defaultCharset(), false);
            } catch (IOException e) {
                throw new RuntimeException("Error writing SQL for table '" + tableName + "' to file: " + outputFile, e);
            }
        }
    }

    protected String getFileContent(String schemaExtractFile) {
        try {
            return FileUtils.readFileToString(new File(schemaExtractFile), defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException("Error reading schema extract file: " + schemaExtractFile, e);
        }
    }

    protected Map<String, List<String>> groupCommandsByTable(List<String> commands) {
        final Map<String, List<String>> commandGroup = new LinkedHashMap<>();

        for (String command : commands) {
            String tableName = getTableName(command);
            if (tableName.equals("")) {
                LOGGER.error("*** No table name in command : ***\n{}\n***", command);
            } else {
                List<String> tableCommands = commandGroup.get(tableName);

                if (tableCommands == null) {
                    tableCommands = new LinkedList<>();
                }

                tableCommands.add(command);
                commandGroup.put(tableName, tableCommands);
            }
        }
        return commandGroup;
    }

    public String getSchemaExtractFile() {
        return schemaExtractFile;
    }

    public String getOutputDir() {
        return outputDir;
    }
}
