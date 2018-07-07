-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-test-seed.sql
-- Description: Setup admin user.
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- ********** Begin of Users setup **********

INSERT INTO movieapp.USERS (ID, ROLE_ID, UUID, EMAIL, PASSWORD, SALT, FIRST_NAME, LAST_NAME,
					MOBILE_PHONE, STATUS, FAILED_LOGIN_COUNT, LAST_PASSWORD_CHANGE_AT, CREATED_BY)
		VALUES 
		(nextval('movieapp.users_id_seq'), 1, '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'admin@movieapp.com', '18B6738D69C2AA04', '8k5y7cuJmdBaS8FrE/BIJyUCEZNtzEG1+rwXRUPcG1A=', 'System', 'Administrator',
		'(111) 111-1111', 'ACTIVE', 0, CURRENT_TIMESTAMP, CURRENT_USER);

		
-- ********** End of Users setup **********	