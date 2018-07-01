-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-setup-boards.sql
-- Description: Create movieapp specific tables and grant privileges
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- # Step 1 - Movies table

DROP TABLE IF EXISTS movieapp.MOVIES CASCADE;
CREATE TABLE IF NOT EXISTS movieapp.MOVIES (
	ID SERIAL PRIMARY KEY,
    UUID VARCHAR(36) NOT NULL,
    VERSION SERIAL NOT NULL,
    TITLE VARCHAR(100) NOT NULL,
    STORYLINE VARCHAR(2000) NOT NULL,
    POSTER_URL VARCHAR(2000) NOT NULL,
    TRAILER_URL VARCHAR(2000) NOT NULL,
    OFFICIAL_WEBSITE_URL VARCHAR(2000),
    WIKI_URL VARCHAR(2000),
    RELEASE_AT TIMESTAMP NOT NULL,
    CENSOR_BOARD_RATING VARCHAR(3) NOT NULL,
    CRITICS_RATING NUMERIC(2,1) DEFAULT 0,
    USERS_RATING NUMERIC(2,1) DEFAULT 0,
    STATUS SMALLINT NOT NULL,
    CREATED_BY VARCHAR(100) NOT NULL ,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR(100) NULL,
    MODIFIED_AT TIMESTAMP NULL
);
COMMENT ON TABLE movieapp.MOVIES IS 'Table to capture MOVIES';
COMMENT ON COLUMN movieapp.MOVIES.ID IS 'Auto generated PK identifier';
COMMENT ON COLUMN movieapp.MOVIES.UUID IS 'Unique identifier used as reference by external systems';
COMMENT ON COLUMN movieapp.MOVIES.VERSION IS 'Versioning for optimistic locking';
COMMENT ON COLUMN movieapp.MOVIES.TITLE IS 'Title of the movie';
COMMENT ON COLUMN movieapp.MOVIES.STORYLINE IS 'Storyline of the movie';
COMMENT ON COLUMN movieapp.MOVIES.POSTER_URL IS 'Poster url of the movie';
COMMENT ON COLUMN movieapp.MOVIES.TRAILER_URL IS 'Poster url of the movie';
COMMENT ON COLUMN movieapp.MOVIES.OFFICIAL_WEBSITE_URL IS 'Official website url of the movie';
COMMENT ON COLUMN movieapp.MOVIES.WIKI_URL IS 'Wiki url of the movie';
COMMENT ON COLUMN movieapp.MOVIES.RELEASE_AT IS 'Point in time when this movie will be release';
COMMENT ON COLUMN movieapp.MOVIES.CENSOR_BOARD_RATING IS 'Rating of the movie issued by Censor Board - U, A, UA';
COMMENT ON COLUMN movieapp.MOVIES.CRITICS_RATING IS 'Average rating provided by the film critics for the movie';
COMMENT ON COLUMN movieapp.MOVIES.USERS_RATING IS 'Average rating provided by the users for the movie';
COMMENT ON COLUMN movieapp.MOVIES.STATUS IS 'Status of the movie - PUBLISHED(0), RELEASED (1), CLOSED(2)';
COMMENT ON COLUMN movieapp.MOVIES.CREATED_BY IS 'User who inserted this record';
COMMENT ON COLUMN movieapp.MOVIES.CREATED_AT IS 'Point in time when this record was inserted';
COMMENT ON COLUMN movieapp.MOVIES.MODIFIED_BY IS 'User who modified this record';
COMMENT ON COLUMN movieapp.MOVIES.MODIFIED_AT IS 'Point in time when this record was modified';

ALTER TABLE movieapp.MOVIES OWNER TO postgres;

-- # Step 2 - ARTISTS table
DROP TABLE IF EXISTS movieapp.ARTISTS CASCADE;
CREATE TABLE movieapp.ARTISTS(
	ID SERIAL PRIMARY KEY,
	UUID VARCHAR(36) NOT NULL,
	VERSION SERIAL NOT NULL,
    FIRST_NAME VARCHAR(50) NOT NULL,
    LAST_NAME VARCHAR(50) NOT NULL,
    PROFILE_DESCRIPTION VARCHAR(500),
    PROFILE_PICTURE_URL VARCHAR(2000) NOT NULL,
    WIKI_URL VARCHAR(2000),
	CREATED_BY VARCHAR(100) NOT NULL,
	CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	MODIFIED_BY VARCHAR(100),
	MODIFIED_AT TIMESTAMP
);
COMMENT ON TABLE movieapp.ARTISTS IS 'Table to capture Artists';
COMMENT ON COLUMN movieapp.ARTISTS.ID IS 'Auto generated PK identifier';
COMMENT ON COLUMN movieapp.ARTISTS.UUID IS 'Unique identifier used as reference by external systems';
COMMENT ON COLUMN movieapp.ARTISTS.VERSION IS 'Versioning for optimistic locking';
COMMENT ON COLUMN movieapp.ARTISTS.FIRST_NAME IS 'First name of an artist';
COMMENT ON COLUMN movieapp.ARTISTS.LAST_NAME IS 'Last name of an artist';
COMMENT ON COLUMN movieapp.ARTISTS.PROFILE_DESCRIPTION IS 'Profile description of an artist';
COMMENT ON COLUMN movieapp.ARTISTS.PROFILE_PICTURE_URL IS 'Profile picture url of an artist';
COMMENT ON COLUMN movieapp.ARTISTS.WIKI_URL IS 'Wiki url of an artist';
COMMENT ON COLUMN movieapp.ARTISTS.CREATED_BY IS 'User who inserted this record';
COMMENT ON COLUMN movieapp.ARTISTS.CREATED_AT IS 'Point in time when this record was inserted';
COMMENT ON COLUMN movieapp.ARTISTS.MODIFIED_BY IS 'User who modified this record';
COMMENT ON COLUMN movieapp.ARTISTS.MODIFIED_AT IS 'Point in time when this record was modified';

ALTER TABLE movieapp.ARTISTS OWNER TO postgres;

-- # Step 3 - PROJECT_MEMBERS table
DROP TABLE IF EXISTS movieapp.MOVIE_ARTISTS CASCADE;
CREATE TABLE IF NOT EXISTS movieapp.MOVIE_ARTISTS (
   ID SERIAL PRIMARY KEY,
   MOVIE_ID INTEGER NOT NULL,
   ARTIST_ID INTEGER NOT NULL,
   CREATED_BY VARCHAR(100) NOT NULL,
   CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE movieapp.MOVIE_ARTISTS IS 'Table to map movie with artists';
COMMENT ON COLUMN movieapp.MOVIE_ARTISTS.ID IS 'Auto generated PK identifier';
COMMENT ON COLUMN movieapp.MOVIE_ARTISTS.MOVIE_ID IS 'Movie that has these artists';
COMMENT ON COLUMN movieapp.MOVIE_ARTISTS.ARTIST_ID IS 'Artists who will be mapped to the Movie';
COMMENT ON COLUMN movieapp.MOVIE_ARTISTS.CREATED_BY IS 'User who inserted this record';
COMMENT ON COLUMN movieapp.MOVIE_ARTISTS.CREATED_AT IS 'Point in time when this record was inserted';

ALTER TABLE movieapp.MOVIE_ARTISTS OWNER TO postgres;
ALTER TABLE movieapp.MOVIE_ARTISTS ADD CONSTRAINT FK_MOVIE_ARTISTS_MOVIE_ID FOREIGN KEY(MOVIE_ID) REFERENCES movieapp.MOVIES(ID);
ALTER TABLE movieapp.MOVIE_ARTISTS ADD CONSTRAINT FK_MOVIE_ARTISTS_ARTIST_ID FOREIGN KEY(ARTIST_ID) REFERENCES movieapp.ARTISTS(ID);

-- # Step 3 - Commit Transaction
COMMIT;

-- ********** End of setup **********