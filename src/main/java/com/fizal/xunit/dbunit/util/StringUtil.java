package com.fizal.xunit.dbunit.util;

import com.fizal.xunit.dbunit.model.DDLType;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.collectionToDelimitedString;

/**
 * Created by fmohamed on 9/15/2016.
 */
public final class StringUtil {

    public static final Set<String> tokenize(String line) {
        Set<String> set = new LinkedHashSet<>();
        Collections.addAll(set, line.split(" "));
        return set;
    }

    public static String extractObjectDef(String ddl) {
        int openCount = 0;
        int closeCount = 0;
        char[] chars = ddl.toCharArray();
        int i = 0;
        for (; i < chars.length; i++) {
            char c = chars[i];

            if (c == '(') {
                openCount++;
            }

            if (c == ')') {
                closeCount++;

                if (openCount == closeCount) {
                    break;
                }
            }
        }

        return ddl.substring(0, i + 1);
    }

    public static List<String> splitCommands(String sql) {
        return Arrays.stream(sql.split(";"))
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());
    }

    public static String mergeCommands(List<String> commands) {
        String mergedCommands = collectionToDelimitedString(commands, ";\n\n");

        if (!mergedCommands.endsWith(";")) {
            mergedCommands = mergedCommands + ";";
        }

        return mergedCommands;
    }

    public static String getIndexOrConstraintName(String command) {
        String[] lines = command.split("\n");

        String indexOrConstraintName = "";

        if (lines.length < 1) {
            return indexOrConstraintName;
        }

        String line1 = lines[0];
        List<Integer> indices = getQuotePositions(line1);

        if ((line1.contains("CREATE INDEX") || line1.contains("CREATE UNIQUE INDEX")) && indices.size() >= 4) {
            return line1.substring(indices.get(0), indices.get(3) + 1);
        }

        if (line1.contains("ALTER TABLE") && line1.contains("ADD CONSTRAINT") && indices.size() >= 6) {
            return line1.substring(indices.get(4), indices.get(5) + 1);
        }
        return indexOrConstraintName;
    }

    public static String getTableName(String command) {
        String[] lines = command.split("\n");

        String table = "";

        if (lines.length < 2) {
            return table;
        }

        String line1 = lines[0];
        String line2 = lines[1];

        Optional<DDLType> commandType = DDLType.getDDLType(line1);

        if (commandType.isPresent()) {
            switch (commandType.get()) {
                case TABLE:
                case CONSTRAINT:
                    table = extractTableName(line1);
                    break;

                case INDEX:
                case UNIQUE_INDEX:
                    table = extractTableName(line2);
                    break;

                default:
                    break;
            }
        }

        return table;
    }

    protected static String extractTableName(String line) {
        List<Integer> indices = getQuotePositions(line);
        if (indices.size() < 4) {
            return "";
        }

        return line.substring(indices.get(0), indices.get(3) + 1);
    }

    protected static List<Integer> getQuotePositions(String line) {
        List<Integer> indices = new ArrayList<>();

        char[] chars = line.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '"') {
                indices.add(i);
            }
        }

        return indices;
    }

}
