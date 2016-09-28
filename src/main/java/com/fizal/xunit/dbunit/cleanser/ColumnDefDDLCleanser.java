package com.fizal.xunit.dbunit.cleanser;

import com.fizal.xunit.dbunit.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This implementation is to cleanse DB2-specific data types and other column definition keywords
 * like
 * CONSTRAINT null PRIMARY KEY
 * ROWID DEFAULT ROWID GENERATED BY DEFAULT
 * <p/>
 * Created by fmohamed on 9/16/2016.
 */
public class ColumnDefDDLCleanser implements DDLCleanser {

    private static final Map<String, String> REPLACE_KEYWORDS = new LinkedHashMap<>();

    static {
        REPLACE_KEYWORDS.put("FOR SBCS DATA", "");
        REPLACE_KEYWORDS.put("WITH DEFAULT", "DEFAULT");
        REPLACE_KEYWORDS.put("TIME NOT NULL DEFAULT", "TIME NOT NULL DEFAULT CURRENT_TIME()");
        REPLACE_KEYWORDS.put("DOUBLE NOT NULL DEFAULT", "DOUBLE NOT NULL DEFAULT 0");
        REPLACE_KEYWORDS.put("BIGINT NOT NULL DEFAULT", "BIGINT NOT NULL DEFAULT 0");
        REPLACE_KEYWORDS.put("ROWID NOT NULL GENERATED ALWAYS", "INT AUTO_INCREMENT");
        REPLACE_KEYWORDS.put("ROWID NOT NULL GENERATED BY DEFAULT", "INT AUTO_INCREMENT");
    }

    @Override
    public String cleanse(String ddl) {
        if (ddl.contains("LINK_RESOURCE")) {
            System.out.println();
        }

        for (Map.Entry<String, String> entry : REPLACE_KEYWORDS.entrySet()) {
            ddl = ddl.replace(entry.getKey(), entry.getValue());
        }

        ddl = cleanDefaultValueColumns(ddl);
        ddl = cleanIdentityColumns(ddl);
        return ddl;
    }

    protected String cleanDefaultValueColumns(String ddl) {
        StringBuilder builder = new StringBuilder();

        for (String line : ddl.split("\n")) {
            Set<String> tokens = StringUtil.tokenize(line);

            if (line.contains("DEFAULT")) {
                if ((line.contains("CHAR") || tokens.contains("BLOB")) && !line.contains("'")) {
                    line = line.replace("DEFAULT", "DEFAULT ''");
                }

                //TODO find a better solution
                if (tokens.contains("INTEGER") && !line.contains("DEFAULT 0,")) {
                    line = line.replace("DEFAULT", "DEFAULT 0");
                }

                //TODO find a better solution
                if (tokens.contains("SMALLINT") && !line.contains("DEFAULT 1,")
                        && !line.contains("DEFAULT 840,")) {
                    line = line.replace("DEFAULT", "DEFAULT 0");
                }

                //TODO find a better solution
                if (line.contains("DECIMAL") && !line.contains("DEFAULT -1")
                        && !line.contains("DEFAULT 0")) {
                    line = line.replace("DEFAULT", "DEFAULT 0.0");
                }

                if (tokens.contains("DATE") && !line.contains("'")) {
                    line = line.replace("DEFAULT", "DEFAULT CURRENT_DATE()");
                }

                if (tokens.contains("TIMESTAMP") && !line.contains("'")) {
                    line = line.replace("DEFAULT", "DEFAULT CURRENT_TIMESTAMP()");
                }

                if (line.contains("BLOB")) {
                    boolean endsWithComma = line.trim().endsWith(",");
                    line = line.substring(0, line.indexOf("BLOB")) + " VARCHAR(100) DEFAULT ''";

                    if (endsWithComma) {
                        line += ",";
                    }
                }
            }

            builder.append(line).append("\n");
        }

        return builder.toString();
    }

    protected String cleanIdentityColumns(String ddl) {
        String identityToken = "GENERATED ALWAYS AS IDENTITY (";
        if (ddl.contains(identityToken)) {
            int startIndex = ddl.indexOf(identityToken);
            int endIndex = ddl.indexOf(")", startIndex + 1);

            ddl = ddl.substring(0, startIndex) + " AUTO_INCREMENT " + ddl.substring(endIndex + 1, ddl.length());
        }

        return ddl;
    }
}
