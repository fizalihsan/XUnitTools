package com.fizal.xunit.dbunit.service;

import com.fizal.xunit.dbunit.exception.DDLExecutionException;
import com.fizal.xunit.dbunit.model.ConnectionConfig;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static com.fizal.xunit.dbunit.model.XUnitConstants.TEST_DB;

/**
 * Created by fmohamed on 9/13/2016.
 */
@Component
final class H2DatabaseService implements DatabaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(H2DatabaseService.class);
    private final ConnectionConfig connectionConfig;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Fatal error loading H2 JDBC driver", e);
        }
    }

    public H2DatabaseService() {
        this(new ConnectionConfig().dbName(TEST_DB));
    }

    public H2DatabaseService(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;

        //TODO why is this getting called twice for a test method?
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUser(connectionConfig.getUser());
        dataSource.setPassword(connectionConfig.getPassword());
        dataSource.setUrl(connectionConfig.getJdbcUrl());

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public final void execute(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            LOGGER.error("Error executing SQL : {}", sql, e);
            throw new DDLExecutionException("Error executing SQL : " + sql, e);
        }
    }

    @Override
    public final void createSchemas(String... schemas) {
        for (String schema : schemas) {
            execute("CREATE SCHEMA IF NOT EXISTS " + schema);
        }
    }

    @Override
    public final void dropAllDbOjects() {
        execute("DROP ALL objects DELETE files");
    }

    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    protected ConnectionConfig getConnectionConfig() {
        return connectionConfig;
    }
}
