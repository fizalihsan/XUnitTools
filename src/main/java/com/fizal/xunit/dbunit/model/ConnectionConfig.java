package com.fizal.xunit.dbunit.model;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.fizal.xunit.dbunit.model.DatabaseMode.IN_MEMORY;
import static com.fizal.xunit.dbunit.model.XUnitConstants.DEFAULT_DB_PASSORD;
import static com.fizal.xunit.dbunit.model.XUnitConstants.DEFAULT_DB_USER;

/**
 * A simple configuration class to abstract away the DB connection and url related
 * attributes away from the application logic
 * <p/>
 * Created by fmohamed on 9/13/2016.
 */
public final class ConnectionConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionConfig.class);
    private static final String SYSTEM_TEMP_DIR = System.getProperty("java.io.tmpdir");

    private String user = DEFAULT_DB_USER;
    private String password = DEFAULT_DB_PASSORD;
    private String dbName;
    private String defaultSchema;
    private DatabaseMode dbMode = IN_MEMORY;
    private boolean keepDbOpen = true;

    public ConnectionConfig user(String user) {
        this.user = user;
        return this;
    }

    public ConnectionConfig password(String password) {
        this.password = password;
        return this;
    }

    public ConnectionConfig dbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public ConnectionConfig defaultSchema(String defaultSchema) {
        this.defaultSchema = defaultSchema;
        return this;
    }

    /**
     * To keep the content of an in-memory database as long as the virtual machine is alive
     *
     * @param keepDbOpen
     * @return
     */
    public ConnectionConfig keepDbOpen(boolean keepDbOpen) {
        this.keepDbOpen = keepDbOpen;
        return this;
    }

    public ConnectionConfig databaseMode(DatabaseMode mode) {
        this.dbMode = mode;
        return this;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDefaultSchema() {
        return defaultSchema;
    }

    public DatabaseMode getDbMode() {
        return dbMode;
    }

    public boolean isKeepDbOpen() {
        return keepDbOpen;
    }

    public String getJdbcUrl() {
        StringBuilder builder = new StringBuilder("jdbc:h2:");

        switch (dbMode) {
            case IN_MEMORY:
                builder.append("mem:").append(dbName).append(";");

                if (keepDbOpen) {
                    builder.append("DB_CLOSE_DELAY=-1;");
                }

                if (!StringUtils.isEmpty(defaultSchema)) {
                    builder.append("SET SCHEMA ").append(defaultSchema);
                }
                break;

            case FILE:
                builder.append("file:").append(SYSTEM_TEMP_DIR).append(dbName).append(";");
                break;

            default:
                return "";
        }

        return builder.toString();
    }

    public Optional<Connection> build() {
        String jdbcUrl = getJdbcUrl();
        LOGGER.info("JDBC URL: {}", jdbcUrl);

        if (StringUtils.isEmpty(jdbcUrl)) {
            return Optional.absent();
        }

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            return Optional.of(connection);
        } catch (SQLException e) {
            LOGGER.error("Error creating JDBC connection. URL: {} User: {}", jdbcUrl, user, e);
        }
        return Optional.absent();
    }
}
