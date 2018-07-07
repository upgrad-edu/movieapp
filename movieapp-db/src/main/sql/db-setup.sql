-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-setup.sql
-- Description: Setup database and users
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- # Step 1 - Drop the existing database and users

DROP SCHEMA IF EXISTS movieapp CASCADE;
CREATE SCHEMA movieapp AUTHORIZATION postgres;
GRANT ALL PRIVILEGES ON SCHEMA movieapp TO postgres;

--CREATE DATABASE proman
--       WITH OWNER = 'proman_dba'
--            ENCODING = 'UTF8'
--            TABLESPACE = pg_default
--            LC_COLLATE = 'en_IN'
--            LC_CTYPE = 'en_IN'
--       CONNECTION LIMIT = -1;

-- COMMENT ON DATABASE proman IS 'Database for Project Management Application';

-- # Step 4 - Grant access privileges

--GRANT ALL PRIVILEGES ON DATABASE proman TO proman_dba;
COMMIT;

--\connect proman;

-- ********** End of setup **********