-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-seed.sql
-- Description: Setup seed data that is essential to bring up applications in running state.
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

INSERT INTO movieapp.GROUPS (ID, UUID, NAME, DESCRIPTION, CREATED_BY)
		VALUES 
		(1, 101, 'Manage Users', 'User Management', CURRENT_USER),
		(2, 102, 'Manage Movies', 'Movie Management', CURRENT_USER),
		(3, 103, 'Manage Reports', 'Report Management', CURRENT_USER);

INSERT INTO movieapp.PERMISSIONS (ID, UUID, NAME, DESCRIPTION, GROUP_ID, CREATED_BY)
		VALUES 
		(1, 101, 'Add/Modify/Remove Users', 'Add/Modify/Remove Users', 1, CURRENT_USER),
		(2, 102, 'Add/Modify/Remove Movies', 'Add/Modify/Remove Movies', 2, CURRENT_USER),
		(3, 103, 'Add/Modify/Remove Artists', 'Add/Modify/Remove Artists', 2, CURRENT_USER),
		(4, 104, 'Add/Modify/Remove Theatres', 'Add/Modify/Remove Theatres', 2, CURRENT_USER),
		(5, 105, 'Add/Modify/Remove Shows', 'Add/Modify/Remove Shows', 2, CURRENT_USER),
		(6, 106, 'View Reports', 'View various reports', 3, CURRENT_USER);

INSERT INTO movieapp.ROLES (ID, UUID, NAME, DESCRIPTION, ACTIVE, CREATED_BY)
		VALUES 
		(1, 101, 'Administrator', 'Administrator', true, CURRENT_USER),
		(2, 102, 'Customer', 'Customer', true, CURRENT_USER);

INSERT INTO movieapp.ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID, CREATED_BY)
		VALUES 
		(1,1, CURRENT_USER), (1,2, CURRENT_USER), (1,3, CURRENT_USER), (1,4, CURRENT_USER), (1,5, CURRENT_USER), (1,6, CURRENT_USER);

-- ********** End of Roles and Permissions setup **********

-- ********** Begin of GENRES setup **********
INSERT INTO movieapp.GENRES (ID, UUID, GENRE, DESCRIPTION, CREATED_BY)
            VALUES
		    (nextval('movieapp.genres_id_seq'), '1d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Drama', 'Drama', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '2d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Romance', 'Romance', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '3d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Horror', 'Horror', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '4d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Action', 'Action', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '5d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Animation', 'Animation', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '6d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Thriller', 'Thriller', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Political', 'Political', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '8d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Social', 'Social', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), '9d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Fantasy', 'Fantasy', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), 'aa174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Satire', 'Satire', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), 'bb174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Adventure', 'Adventure', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), 'cc174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Comedy', 'Comedy', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), 'dd174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Scifi', 'Science Fiction', CURRENT_USER),
		    (nextval('movieapp.genres_id_seq'), 'ee174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Historical', 'Historical', CURRENT_USER);

-- ********** End of GENRES setup **********