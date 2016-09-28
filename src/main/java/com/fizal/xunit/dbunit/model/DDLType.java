package com.fizal.xunit.dbunit.model;

import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by fmohamed on 9/13/2016.
 */
public enum DDLType {
    TABLE("CREATE TABLE"),
    AUXILIARY_TABLE("CREATE AUXILIARY TABLE"),
    INDEX("CREATE INDEX"),
    UNIQUE_INDEX("CREATE UNIQUE INDEX"),
    CONSTRAINT("ADD CONSTRAINT");

    private String commandPrefix;

    DDLType(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public static Optional<DDLType> getDDLType(String ddl) {
        if (StringUtils.isEmpty(ddl)) {
            return Optional.empty();
        }

        for (DDLType commandType : values()) {
            if (ddl.contains(commandType.commandPrefix)) {
                return Optional.of(commandType);
            }
        }

        return Optional.empty();
    }
}
