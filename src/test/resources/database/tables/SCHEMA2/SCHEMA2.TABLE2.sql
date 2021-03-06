CREATE TABLE "SCHEMA2"."TABLE2" (
		"RSRC_ID" VARCHAR(30) FOR SBCS DATA NOT NULL, 
		"RSRC_INQUIRY_FG" CHAR(1) FOR SBCS DATA NOT NULL, 
		"RSRC_DELETE_FG" CHAR(1) FOR SBCS DATA NOT NULL, 
		"RSRC_BROWSE_FG" CHAR(1) FOR SBCS DATA NOT NULL, 
		"RSRC_UPDTE_FG" CHAR(1) FOR SBCS DATA NOT NULL, 
		"RSRC_INSERT_FG" CHAR(1) FOR SBCS DATA NOT NULL, 
		"RSRC_TYPE" CHAR(1) FOR SBCS DATA NOT NULL, 
		"RSRC_OPENCLOSE_FG" CHAR(1) FOR SBCS DATA NOT NULL, 
		"RSRC_DESC" VARCHAR(200) FOR SBCS DATA NOT NULL, 
		"RSRC_UPT_DTE" TIMESTAMP NOT NULL, 
		"RSRC_UPT_USR" VARCHAR(30) FOR SBCS DATA NOT NULL, 
		"RSRC_ACCESS_DTE" TIMESTAMP NOT NULL, 
		"RSRC_ACCESS_USR" VARCHAR(30) FOR SBCS DATA NOT NULL, 
		"RSRC_APPL_DATA" VARCHAR(80) FOR SBCS DATA NOT NULL
	)
	AUDIT NONE
	DATA CAPTURE NONE 
	CCSID EBCDIC;

CREATE UNIQUE INDEX "SCHEMA2"."RSRC_ID"
	ON "SCHEMA2"."TABLE2"
	("RSRC_ID");

ALTER TABLE "SCHEMA2"."TABLE2" ADD CONSTRAINT "RSRC_ID" PRIMARY KEY
	("RSRC_ID");