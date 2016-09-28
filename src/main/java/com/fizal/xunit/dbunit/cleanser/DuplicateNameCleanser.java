package com.fizal.xunit.dbunit.cleanser;

import com.fizal.xunit.dbunit.model.DDLType;

import java.util.Date;
import java.util.EnumSet;
import java.util.Optional;

import static com.fizal.xunit.dbunit.model.DDLType.*;
import static com.fizal.xunit.dbunit.util.StringUtil.getIndexOrConstraintName;
import static java.util.EnumSet.of;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Apparently DB2 allows same index and constraint names across tables, however in H2 it should be
 * unique across the database.
 * <p/>
 * This implementation is to append table name in front of index and constraint names.
 * <p/>
 * Created by fmohamed on 9/16/2016.
 */
public class DuplicateNameCleanser implements DDLCleanser {

    private static final EnumSet<DDLType> OBJECT_TYPES = of(INDEX, UNIQUE_INDEX, CONSTRAINT);

    @Override
    public String cleanse(String ddl) {

        Optional<DDLType> type = DDLType.getDDLType(ddl);
        DDLType ddlType = type.get();

        if (type.isPresent() && OBJECT_TYPES.contains(ddlType)) {
            String indexOrConstraintName = getIndexOrConstraintName(ddl);

            if (!isEmpty(indexOrConstraintName)) {
                String nameWithoutQuotes = indexOrConstraintName.replaceAll("\"", "");
                String newName = "\"" + nameWithoutQuotes + "_" + new Date().getTime() + "\"";

                String prefix = ddlType.getCommandPrefix();
                ddl = ddl.replaceAll(prefix + " " + indexOrConstraintName, prefix + " " + newName);
            }
        }

        return ddl;
    }
}
