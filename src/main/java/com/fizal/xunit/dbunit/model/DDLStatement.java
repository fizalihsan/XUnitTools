package com.fizal.xunit.dbunit.model;

import com.fizal.xunit.dbunit.util.StringUtil;
import com.google.common.base.Optional;

/**
 * Created by fmohamed on 9/16/2016.
 */
@Deprecated
public final class DDLStatement {
    private final String ddl;
    private DDLType type;
    private String tableName;
    private String schemaName;
    private String objectName;

    public DDLStatement(String ddl) {
        this.ddl = ddl;
        Optional<DDLType> ddlType = DDLType.getDDLType(ddl);
        if (!ddlType.isPresent()) {
            throw new RuntimeException("Unrecognized DDL type found: " + ddl);
        }
        this.type = ddlType.get();

    }

    public String getDdl() {
        return ddl;
    }

    public DDLType getType() {
        return type;
    }

    public void setType(DDLType type) {
        this.type = type;
    }

    public String getTableName() {
        if (this.tableName == null) {
            this.tableName = StringUtil.getTableName(this.ddl);
        }
        return tableName;
    }

    /*public String getSchemaName(){
        if(this.schemaName == null){
            String table = getTableName();

            int index = table.indexOf(".");
            if(index != -1){

            }
        }
    }*/


}
