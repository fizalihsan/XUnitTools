package com.fizal.xunit.dbunit.provider;

import com.fizal.xunit.dbunit.exception.DDLNotFoundException;
import com.fizal.xunit.dbunit.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.readFileToString;

/**
 * Created by fmohamed on 9/13/2016.
 */
@Component
public class DefaultDDLProvider implements DDLProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDDLProvider.class);

    public String getDDLStatement(String table) {
        String sql;
        try {
            sql = readFileToString(getDDLFile(table), Charset.defaultCharset());
            LOGGER.debug("DDL for {} = \n\n{}\n\n", table, sql);
        } catch (Exception e) {
            LOGGER.error("Error reading DDL for table : {}", table);
            throw new DDLNotFoundException("Error reading DDL for table: " + table, e);
        }
        return sql;
    }

    protected File getDDLFile(String table) {
        String ddlFilePath = StringUtil.join("/tables/", getSchema(table), separator, table, ".sql");
        Resource resource = new ClassPathResource(ddlFilePath);

        if (resource.exists()) {
            try {
                return resource.getFile();
            } catch (IOException e) {
                throw new RuntimeException("Error retrieving DDL file from classpath : " + ddlFilePath, e);
            }
        }

        throw new DDLNotFoundException("Unable to locate the DDL file in classpath: " + ddlFilePath);
    }

    protected String getSchema(String table) {
        return table.contains(".") ? table.substring(0, table.indexOf(".")) : "";
    }
}
