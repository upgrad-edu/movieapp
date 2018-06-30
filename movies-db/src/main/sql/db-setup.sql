-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-setup.sql
-- Description: Setup database and users
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- # Step 1 - Drop the existing database and users
REASSIGN OWNED BY proman_dba TO postgres;
DROP OWNED BY proman_dba;

DROP SCHEMA IF EXISTS proman CASCADE;
DROP USER IF EXISTS proman_dba;
DROP USER IF EXISTS proman_dev;

-- # Step 2 - Create users

CREATE USER proman_dba WITH ENCRYPTED PASSWORD 'promandba@123';
CREATE USER proman_dev WITH ENCRYPTED PASSWORD 'promandev@123';

-- # Step 3 - Create database
CREATE SCHEMA proman AUTHORIZATION proman_dba;
GRANT ALL PRIVILEGES ON SCHEMA proman TO proman_dba;

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