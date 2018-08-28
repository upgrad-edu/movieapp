-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-setup.sql
-- Description: Setup database and users
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- # Step 1 - Drop the existing database and users

DROP SCHEMA IF EXISTS movieapp CASCADE;
CREATE SCHEMA movieapp AUTHORIZATION postgres;
GRANT ALL PRIVILEGES ON SCHEMA movieapp TO postgres;
COMMIT;

-- ********** End of setup **********