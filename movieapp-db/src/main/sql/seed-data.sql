-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-seed.sql
-- Description: Setup seed data that is essential to bring up applications in running state.
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

INSERT INTO movieapp.GROUPS (ID, UUID, NAME, DESCRIPTION)
		VALUES 
		(1, 101, 'Manage Users', 'User Management'),
		(2, 102, 'Manage Movies', 'Movie Management'),
		(3, 103, 'Manage Reports', 'Report Management');
SELECT pg_catalog.setval('movieapp.groups_id_seq', 3, false);

INSERT INTO movieapp.PERMISSIONS (ID, UUID, NAME, DESCRIPTION, GROUP_ID)
		VALUES 
		(1, 101, 'Add/Modify/Remove Users', 'Add/Modify/Remove Users', 1),
		(2, 102, 'Add/Modify/Remove Movies', 'Add/Modify/Remove Movies', 2),
		(3, 103, 'Add/Modify/Remove Artists', 'Add/Modify/Remove Artists', 2),
		(4, 104, 'Add/Modify/Remove Theatres', 'Add/Modify/Remove Theatres', 2),
		(5, 105, 'Add/Modify/Remove Shows', 'Add/Modify/Remove Shows', 2),
		(6, 106, 'View Reports', 'View various reports', 3);
SELECT pg_catalog.setval('movieapp.permissions_id_seq', 6, false);


INSERT INTO movieapp.ROLES (ID, UUID, NAME, DESCRIPTION, ACTIVE)
		VALUES 
		(1, 101, 'Administrator', 'Administrator', true),
		(2, 102, 'Customer', 'Customer', true);
SELECT pg_catalog.setval('movieapp.roles_id_seq', 2, false);

INSERT INTO movieapp.ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID)
		VALUES 
		(1,1),
		(1,2),
		(1,3),
		(1,4),
		(1,5),
		(1,6);
SELECT pg_catalog.setval('movieapp.role_permissions_id_seq', 6, true);

-- ********** End of Roles and Permissions setup **********

-- ********** Begin of GENRES setup **********
INSERT INTO movieapp.GENRES (ID, UUID, GENRE, DESCRIPTION)
        VALUES
        (1, '1d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Drama', 'Drama'),
        (2, '2d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Romance', 'Romance'),
        (3, '3d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Horror', 'Horror'),
        (4, '4d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Action', 'Action'),
        (5, '5d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Crime', 'Crime'),
        (6, '6d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Thriller', 'Thriller'),
        (7, '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Political', 'Political'),
        (8, '8d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Social', 'Social'),
        (9, '9d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Fantasy', 'Fantasy'),
        (10, 'aa174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Suspense', 'Suspense'),
        (11, 'bb174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Adventure', 'Adventure'),
        (12, 'cc174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Comedy', 'Comedy'),
        (13, 'dd174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Scifi', 'Science Fiction'),
        (14, 'ee174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Historical', 'Historical');
SELECT pg_catalog.setval('movieapp.genres_id_seq', 14, true);

-- ********** End of GENRES setup **********

-- ********** Begin of CITIES setup **********
INSERT INTO movieapp.CITIES (ID, CODE, NAME)
            VALUES
		    (1, 'BLR', 'Bengaluru'),
		    (2, 'MUM', 'Mumbai'),
		    (3, 'HYD', 'Hyderabad'),
		    (4, 'DEL', 'New Delhi'),
		    (5, 'KOL', 'Kolkata'),
		    (6, 'CHN', 'Chennai'),
		    (7, 'AHM', 'Ahmedabad');
SELECT pg_catalog.setval('movieapp.cities_id_seq', 7, true);

-- ********** End of GENRES setup **********

-- ********** Begin of COUPONS setup **********
INSERT INTO movieapp.COUPONS (ID, CODE, DISCOUNT_PERCENTAGE, DESCRIPTION)
            VALUES
		    (1, 'OFFER10PC', 10, '10% discount on the booking'),
		    (2, 'OFFER25PC', 25, '25% discount on the booking'),
		    (3, 'OFFER50PC', 50, '50% discount on the booking');
SELECT pg_catalog.setval('movieapp.coupons_id_seq', 3, true);

-- ********** End of COUPONS setup **********