-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-setup-users.sql
-- Description: Create user specific tables and grant privileges
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- # Step 1 - USERS table

DROP TABLE IF EXISTS movieapp.USERS CASCADE;
CREATE TABLE IF NOT EXISTS movieapp.USERS (
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
    STATUS VARCHAR(30) NOT NULL,
    FAILED_LOGIN_COUNT SMALLINT,
    LAST_PASSWORD_CHANGE_AT TIMESTAMP,
    LAST_LOGIN_AT TIMESTAMP NULL,
    SUBSCRIPTIONS_CONSENT BOOLEAN NOT NULL DEFAULT TRUE,
    CREATED_BY VARCHAR(100) NOT NULL DEFAULT CURRENT_USER,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR(100) NULL,
    MODIFIED_AT TIMESTAMP NULL
);
COMMENT ON TABLE movieapp.USERS IS 'Table to capture users';
COMMENT ON COLUMN movieapp.USERS.ID IS 'Auto generated PK identifier';
COMMENT ON COLUMN movieapp.USERS.UUID IS 'Unique identifier used as reference by external systems';
COMMENT ON COLUMN movieapp.USERS.VERSION IS 'Versioning for optimistic locking';
COMMENT ON COLUMN movieapp.USERS.ROLE_ID IS 'Role that this user is mapped to';
COMMENT ON COLUMN movieapp.USERS.EMAIL IS 'Email address of the user';
COMMENT ON COLUMN movieapp.USERS.PASSWORD IS 'Encrypted password of the user';
COMMENT ON COLUMN movieapp.USERS.SALT IS 'Generated salt for strong encryption of password of user';
COMMENT ON COLUMN movieapp.USERS.FIRST_NAME IS 'First name of the user';
COMMENT ON COLUMN movieapp.USERS.LAST_NAME IS 'Last name of the user';
COMMENT ON COLUMN movieapp.USERS.MOBILE_PHONE IS 'Mobile phone coordinates of the user';
COMMENT ON COLUMN movieapp.USERS.STATUS IS 'Status of the user - INACTIVE, ACTIVE, LOCKED';
COMMENT ON COLUMN movieapp.USERS.FAILED_LOGIN_COUNT IS 'Count to keep track of failed login attempts by the user';
COMMENT ON COLUMN movieapp.USERS.LAST_PASSWORD_CHANGE_AT IS 'Point in time when the user modified the password - as per password policy';
COMMENT ON COLUMN movieapp.USERS.LAST_LOGIN_AT IS 'Point in time when the user log in';
COMMENT ON COLUMN movieapp.USERS.SUBSCRIPTIONS_CONSENT IS 'Subscriptions consent given by the user';
COMMENT ON COLUMN movieapp.USERS.CREATED_BY IS 'User who inserted this record';
COMMENT ON COLUMN movieapp.USERS.CREATED_AT IS 'Point in time when this record was inserted';
COMMENT ON COLUMN movieapp.USERS.MODIFIED_BY IS 'User who modified this record';
COMMENT ON COLUMN movieapp.USERS.MODIFIED_AT IS 'Point in time when this record was modified';

ALTER TABLE movieapp.USERS OWNER TO postgres;
ALTER TABLE movieapp.USERS ADD CONSTRAINT UK_USERS_EMAIL UNIQUE(EMAIL);
ALTER TABLE movieapp.USERS ADD CONSTRAINT FK_USERS_ROLE_ID FOREIGN KEY(ROLE_ID) REFERENCES movieapp.ROLES(ID);

-- # Step 2 - USER_AUTH_TOKENS table
DROP TABLE IF EXISTS movieapp.USER_AUTH_TOKENS;
CREATE TABLE movieapp.USER_AUTH_TOKENS(
	ID BIGSERIAL PRIMARY KEY,
	VERSION SERIAL NOT NULL,
	USER_ID INTEGER NOT NULL,
	ACCESS_TOKEN VARCHAR(500) NOT NULL,
	EXPIRES_AT TIMESTAMP NOT NULL,
	LOGIN_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	LOGOUT_AT TIMESTAMP,
	CREATED_BY VARCHAR(100) NOT NULL DEFAULT CURRENT_USER,
	CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	MODIFIED_BY VARCHAR(100),
	MODIFIED_AT TIMESTAMP
);
COMMENT ON TABLE movieapp.USER_AUTH_TOKENS IS 'Table to capture user authentication tokens';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.ID IS 'Auto generated PK identifier';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.VERSION IS 'Versioning for optimistic locking';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.USER_ID IS 'User whose authentication token is generated';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.ACCESS_TOKEN IS 'Generated authentication token (short-lived) for the logged-in user';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.EXPIRES_AT IS 'Expiration time of the generated authentication token';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.LOGIN_AT IS 'Login time of the logged-in user';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.LOGOUT_AT IS 'Logout time of the previously logged-in user';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.CREATED_BY IS 'User who inserted this record';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.CREATED_AT IS 'Point in time when this record was inserted';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.MODIFIED_BY IS 'User who modified this record';
COMMENT ON COLUMN movieapp.USER_AUTH_TOKENS.MODIFIED_AT IS 'Point in time when this record was modified';

ALTER TABLE movieapp.USER_AUTH_TOKENS OWNER TO postgres;
ALTER TABLE movieapp.USER_AUTH_TOKENS ADD CONSTRAINT UK_USER_AUTH_TOKENS_ACCESS_TOKEN UNIQUE(ACCESS_TOKEN);
ALTER TABLE movieapp.USER_AUTH_TOKENS ADD CONSTRAINT FK_USER_AUTH_TOKENS_USER_ID FOREIGN KEY(USER_ID) REFERENCES movieapp.USERS(ID);

-- # Step 3 - Commit Transaction
COMMIT;

-- ********** End of setup **********