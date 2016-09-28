package com.fizal.xunit.dbunit.service;

import com.fizal.xunit.dbunit.cleanser.AdminKeywordsDDLCleanser;
import com.fizal.xunit.dbunit.cleanser.ColumnDefDDLCleanser;
import com.fizal.xunit.dbunit.cleanser.DDLCleanser;
import com.fizal.xunit.dbunit.cleanser.DuplicateNameCleanser;
import com.fizal.xunit.dbunit.model.TestMethodConfig;
import com.fizal.xunit.dbunit.supplier.DDLSupplier;
import com.fizal.xunit.dbunit.supplier.TestDataSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.fizal.xunit.dbunit.util.StringUtil.splitCommands;
import static com.fizal.xunit.dbunit.util.TableDeployOrderSorter.sort;

/**
 * Created by fmohamed on 9/15/2016.
 */
@Component
public class DBUnitBootStrapService {

    @Autowired
    private DatabaseService dbService;
    @Autowired
    private DDLSupplier ddlSupplier;
    @Autowired
    private TestDataSupplier testDataSupplier;

    private static final DDLCleanser[] DDL_CLEANSERS = new DDLCleanser[]{
            new AdminKeywordsDDLCleanser(),
            new ColumnDefDDLCleanser(),
            new DuplicateNameCleanser()
    };

    public void bootstrap(final TestMethodConfig testMethodConfig) {
        //1. Drop all pre-existing objects
        dbService.dropAllDbOjects();

        //2. Create schemas
        Set<String> schemaNames = testMethodConfig.getSchemaNames();
        dbService.createSchemas(schemaNames.toArray(new String[schemaNames.size()]));

        for (String table : sort(testMethodConfig.getTableNames())) {
            //3. Get DDL for the table
            String ddlStatement = ddlSupplier.getDDLStatement(table);

            //4. Cleanse DDL
            List<String> ddlCommands = splitAndCleanse(ddlStatement);

            //5. Create table
            for (String ddlCommand : ddlCommands) {
                dbService.execute(ddlCommand);
            }

            //6. Load table with test data
            List<String> insertSqls = testDataSupplier.insertStatement(testMethodConfig, table);

            for (String sql : insertSqls) {
                dbService.execute(sql);
            }
        }
    }

    protected List<String> splitAndCleanse(String ddl) {
        List<String> commands = splitCommands(ddl);

        List<String> cleansedCommands = new ArrayList<>(commands.size());

        for (String command : commands) {
            for (DDLCleanser cleanser : DDL_CLEANSERS) {
                command = cleanser.cleanse(command);
            }
            cleansedCommands.add(command);
        }

        return cleansedCommands;
    }
}
