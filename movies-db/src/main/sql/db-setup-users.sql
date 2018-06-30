-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-setup-users.sql
-- Description: Create user specific tables and grant privileges
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- # Step 1 - USERS table

DROP TABLE IF EXISTS proman.USERS CASCADE;
CREATE TABLE IF NOT EXISTS proman.USERS (
	ID SERIAL PRIMARY KEY,
    UUID VARCHAR(36) NOT NULL,
    VERSION SERIAL NOT NULL,
    ROLE_ID SMALLINT,
    EMAIL VARCHAR(200) NOT NULL,
    PASSWORD VARCHAR(200) NOT NULL,
    SALT VARCHAR(200) NOT NULL,
    FIRST_NAME VARCHAR(50) NOT NULL,
    LAST_NAME VARCHAR(50) NOT NULL,
    MOBILE_PHONE VARCHAR(50) NULL,
    STATUS SMALLINT NOT NULL,
    FAILED_LOGIN_COUNT SMALLINT,
    LAST_PASSWORD_CHANGE_AT TIMESTAMP,
    LAST_LOGIN_AT TIMESTAMP NULL,
    CREATED_BY VARCHAR(100) NOT NULL ,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR(100) NULL,
    MODIFIED_AT TIMESTAMP NULL
);
COMMENT ON TABLE proman.USERS IS 'Table to capture users';
COMMENT ON COLUMN proman.USERS.ID IS 'Auto generated PK identifier';
COMMENT ON COLUMN proman.USERS.UUID IS 'Unique identifier used as reference by external systems';
COMMENT ON COLUMN proman.USERS.VERSION IS 'Versioning for optimistic locking';
COMMENT ON COLUMN proman.USERS.ROLE_ID IS 'Role that this user is mapped to';
COMMENT ON COLUMN proman.USERS.EMAIL IS 'Email address of the user';
COMMENT ON COLUMN proman.USERS.PASSWORD IS 'Encrypted password of the user';
COMMENT ON COLUMN proman.USERS.SALT IS 'Generated salt for strong encryption of password of user';
COMMENT ON COLUMN proman.USERS.FIRST_NAME IS 'First name of the user';
COMMENT ON COLUMN proman.USERS.LAST_NAME IS 'Last name of the user';
COMMENT ON COLUMN proman.USERS.MOBILE_PHONE IS 'Mobile phone coordinates of the user';
COMMENT ON COLUMN proman.USERS.STATUS IS 'Status of the user - INACTIVE(0), ACTIVE (1), LOCKED(2)';
COMMENT ON COLUMN proman.USERS.FAILED_LOGIN_COUNT IS 'Count to keep track of failed login attempts by the user';
COMMENT ON COLUMN proman.USERS.LAST_PASSWORD_CHANGE_AT IS 'Point in time when the user modified the password - as per password policy';
COMMENT ON COLUMN proman.USERS.LAST_LOGIN_AT IS 'Point in time when the user log in';
COMMENT ON COLUMN proman.USERS.CREATED_BY IS 'User who inserted this record';
COMMENT ON COLUMN proman.USERS.CREATED_AT IS 'Point in time when this record was inserted';
COMMENT ON COLUMN proman.USERS.MODIFIED_BY IS 'User who modified this record';
COMMENT ON COLUMN proman.USERS.MODIFIED_AT IS 'Point in time when this record was modified';

ALTER TABLE proman.USERS OWNER TO proman_dba;
ALTER TABLE proman.USERS ADD CONSTRAINT UK_USERS_EMAIL UNIQUE(EMAIL);
ALTER TABLE proman.USERS ADD CONSTRAINT FK_USERS_ROLE_ID FOREIGN KEY(ROLE_ID) REFERENCES proman.ROLES(ID);
GRANT ALL ON proman.USERS TO proman_dba;
GRANT SELECT,INSERT,UPDATE ON proman.USERS TO proman_dev;

-- # Step 2 - USER_AUTH_TOKENS table
DROP TABLE IF EXISTS proman.USER_AUTH_TOKENS;
CREATE TABLE proman.USER_AUTH_TOKENS(
	ID BIGSERIAL PRIMARY KEY,
	VERSION SERIAL NOT NULL,
	USER_ID INTEGER NOT NULL,
	ACCESS_TOKEN VARCHAR(500) NOT NULL,
	EXPIRES_AT TIMESTAMP NOT NULL,
	LOGIN_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	LOGOUT_AT TIMESTAMP,
	LOGOUT_ACTION SMALLINT,
	CREATED_BY VARCHAR(100) NOT NULL,
	CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	MODIFIED_BY VARCHAR(100),
	MODIFIED_AT TIMESTAMP
);
COMMENT ON TABLE proman.USER_AUTH_TOKENS IS 'Table to capture user authentication tokens';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.ID IS 'Auto generated PK identifier';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.VERSION IS 'Versioning for optimistic locking';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.USER_ID IS 'User whose authentication token is generated';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.ACCESS_TOKEN IS 'Generated authentication token (short-lived) for the logged-in user';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.EXPIRES_AT IS 'Expiration time of the generated authentication token';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.LOGIN_AT IS 'Login time of the logged-in user';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.LOGOUT_AT IS 'Logout time of the previously logged-in user';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.LOGOUT_ACTION IS 'Logout action of the logged-in user - USER(1), EXPIRED(2)';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.CREATED_BY IS 'User who inserted this record';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.CREATED_AT IS 'Point in time when this record was inserted';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.MODIFIED_BY IS 'User who modified this record';
COMMENT ON COLUMN proman.USER_AUTH_TOKENS.MODIFIED_AT IS 'Point in time when this record was modified';

ALTER TABLE proman.USER_AUTH_TOKENS OWNER TO proman_dba;
ALTER TABLE proman.USER_AUTH_TOKENS ADD CONSTRAINT UK_USER_AUTH_TOKENS_ACCESS_TOKEN UNIQUE(ACCESS_TOKEN);
ALTER TABLE proman.USER_AUTH_TOKENS ADD CONSTRAINT FK_USER_AUTH_TOKENS_USER_ID FOREIGN KEY(USER_ID) REFERENCES proman.USERS(ID);
GRANT ALL ON proman.USER_AUTH_TOKENS TO proman_dba;
GRANT SELECT,INSERT,UPDATE ON proman.USER_AUTH_TOKENS TO proman_dev;

-- # Step 3 - Commit Transaction
COMMIT;

-- ********** End of setup **********