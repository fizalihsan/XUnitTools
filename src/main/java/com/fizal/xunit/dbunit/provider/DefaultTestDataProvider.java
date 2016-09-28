package com.fizal.xunit.dbunit.provider;

import com.fizal.xunit.dbunit.exception.DDLNotFoundException;
import com.fizal.xunit.dbunit.exception.InvalidTestDataFileException;
import com.fizal.xunit.dbunit.model.TestMethodConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.fizal.xunit.dbunit.util.StringUtil.join;
import static java.io.File.separator;
import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by fmohamed on 9/13/2016.
 */
@Component
public class DefaultTestDataProvider implements TestDataProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDDLProvider.class);

    public List<String> insertStatement(TestMethodConfig testMethodConfig, String table) {
        try {
            File testDataFile = getTestDataFile(testMethodConfig, table);

            if (testDataFile != null) {
                String csv = readFileToString(testDataFile, defaultCharset());
                LOGGER.debug("Test Data for {} = \n\n{}\n\n", table, csv);

                return csvToInsertSql(csv, table);
            }
        } catch (Exception e) {
            LOGGER.error("Error reading DDL for table : {}", table);
            throw new DDLNotFoundException("Error reading DDL for table: " + table, e);
        }

        return Collections.emptyList();
    }

    protected File getTestDataFile(TestMethodConfig testMethodConfig, String table) {
        String testClassName = testMethodConfig.getTestClassName().replace(".", separator);
        String dataSetName = testMethodConfig.getDataSetName();

        if (isEmpty(dataSetName)) {
            dataSetName = testMethodConfig.getTestMethodName();
        }

        String csvFilePath = join("/testdata/", testClassName, separator, dataSetName, separator, table, ".csv");
        File csvFile = getFile(csvFilePath);

        if (csvFile == null) {
            //If no data file found under /<class>/<method> directory, then
            //check if a common data file exists for this table

            csvFilePath = join("/testdata/", table, ".csv");
            csvFile = getFile(csvFilePath);
        }


        //return null to allow creating an empty table without any data rows
        return csvFile;
    }

    protected File getFile(String filePath) {
        Resource resource = new ClassPathResource(filePath);

        if (resource.exists()) {
            try {
                return resource.getFile();
            } catch (IOException e) {
                throw new RuntimeException("Error retrieving test data file from classpath : " + filePath, e);
            }
        } else {
            LOGGER.info("Test data file '{}' not found in classpath.", filePath);
        }

        return null;
    }

    protected List<String> csvToInsertSql(String csv, String table) {
        String[] csvLines = csv.split("\n");

        if (csvLines.length < 2) {
            throw new InvalidTestDataFileException("Test data csv file should have at least a header line and a data line");
        }

        String columnNames = csvLines[0];
        List<String> sqls = new LinkedList<>();

        for (int i = 1; i < csvLines.length; i++) {
            if (csvLines[i].startsWith("--")) {
                //this is a comment line. Skip it.
                continue;
            }
            String sql = join("INSERT INTO ", table, " (", columnNames, ") VALUES(", csvLines[i], "); \n");
            sqls.add(sql);
        }
        return sqls;
    }
}
