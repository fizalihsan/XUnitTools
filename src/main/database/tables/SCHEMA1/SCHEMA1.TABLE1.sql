CREATE TABLE "SCHEMA1"."TABLE1" (
		"LINK_RSRC_ID" INTEGER NOT NULL, 
		"RSRC_ID" VARCHAR(30) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"LINK_URI_ADDR" VARCHAR(256) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"LABEL_KEY" VARCHAR(32) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"GROUP_NM" VARCHAR(64) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"CRT_TS" TIMESTAMP NOT NULL WITH DEFAULT, 
		"CRT_USR" VARCHAR(18) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"MDY_TS" TIMESTAMP NOT NULL WITH DEFAULT, 
		"MDY_USR" VARCHAR(18) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"MENU_FLAG" CHAR(1) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"BEHAVIOR_CD" CHAR(1) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"LEVEL_1_DESC" VARCHAR(32) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"LEVEL_2_DESC" VARCHAR(32) FOR SBCS DATA NOT NULL WITH DEFAULT, 
		"LEVEL_0_SORT_NBR" INTEGER NOT NULL WITH DEFAULT, 
		"LEVEL_1_SORT_NBR" INTEGER NOT NULL WITH DEFAULT, 
		"LEVEL_2_SORT_NBR" INTEGER NOT NULL WITH DEFAULT, 
		"INCLUSIVE_FLG" CHAR(1) FOR SBCS DATA NOT NULL WITH DEFAULT 'A'
	)
	PARTITION BY SIZE EVERY 4 G 
	AUDIT NONE
	DATA CAPTURE NONE 
	CCSID EBCDIC;

CREATE UNIQUE INDEX "SCHEMA1"."LINK_RSRC_ID_KEY"
	ON "SCHEMA1"."TABLE1"
	("LINK_RSRC_ID");

ALTER TABLE "SCHEMA1"."TABLE1" ADD CONSTRAINT "LINK_RSRC_ID_KEY" PRIMARY KEY
	("LINK_RSRC_ID");

ALTER TABLE "SCHEMA1"."TABLE1" ADD CONSTRAINT "RSRC_ID_KEY" FOREIGN KEY
	("RSRC_ID")
	REFERENCES "SCHEMA2"."TABLE2"
	("RSRC_ID")
	ON DELETE CASCADE;