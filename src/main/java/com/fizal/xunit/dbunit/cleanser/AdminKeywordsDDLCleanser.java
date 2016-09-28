package com.fizal.xunit.dbunit.cleanser;

import com.fizal.xunit.dbunit.util.StringUtil;

/**
 * Typically a DDL for a table or an index contain storage administration related keywords
 * like AUDIT, CLUSTER, BUFFERPOOL, etc. as shown below:
 * <p/>
 * <pre>
 *     <code>
 *         CREATE TABLE "DBO"."AC18_AC19_CNV" (
 * ........
 * )
 * AUDIT NONE
 * DATA CAPTURE NONE
 * CCSID EBCDIC;
 *     </code>
 * </pre>
 * <p/>
 * <pre>
 *     <code>
 *         CREATE INDEX "DBO"."XPAC1819" ON "DBO"."AC18_AC19_CNV"
 * (....)
 * CLUSTER
 * USING STOGROUP SGCS0001
 * PRIQTY -1
 * SECQTY -1
 * PCTFREE 5
 * BUFFERPOOL BP3
 * PIECESIZE 2097152 K;
 *     </code>
 * </pre>
 * <p/>
 * This implementation removes those DB2-specific admin keywords.
 * <p/>
 * Created by fmohamed on 9/16/2016.
 */
public class AdminKeywordsDDLCleanser implements DDLCleanser {

    @Override
    public String cleanse(String ddl) {
        if (ddl.startsWith("ALTER TABLE")) {
            int index = ddl.lastIndexOf(")");
            if (index != -1) {
                return ddl.substring(0, index + 1);
            }
            return ddl;
        }

        return StringUtil.extractObjectDef(ddl);
    }

}
