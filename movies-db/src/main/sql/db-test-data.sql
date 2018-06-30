-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-test-seed.sql
-- Description: Setup admin user.
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- ********** Begin of Users setup **********

INSERT INTO proman.USERS (ID, ROLE_ID, UUID, EMAIL, PASSWORD, SALT, FIRST_NAME, LAST_NAME,
					MOBILE_PHONE, STATUS, FAILED_LOGIN_COUNT, LAST_LOGIN_AT, LAST_PASSWORD_CHANGE_AT, CREATED_BY)
		VALUES 
		(nextval('proman.users_id_seq'), 1, '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'admin@proman.io', 'DF8582C633D66A48', 'sHrhX9BtMOnUpSKFHz8mltet4gXGfGR33JdvH0fst5I=', 'Proman', 'Administrator',
		'(111) 111-1111', 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_USER);

		
-- ********** End of Users setup **********	