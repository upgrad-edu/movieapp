-- Copyright 2018-2019, https://beingtechie.io

-- Script: db-test-seed.sql
-- Description: Setup admin user.
-- Version: 1.0
-- Author: Thribhuvan Krishnamurthy

-- ********** Begin of USERS setup **********

INSERT INTO movieapp.USERS (ID, ROLE_ID, UUID, EMAIL, PASSWORD, SALT, FIRST_NAME, LAST_NAME,
					MOBILE_PHONE, STATUS, FAILED_LOGIN_COUNT, LAST_PASSWORD_CHANGE_AT, CREATED_BY)
		VALUES 
		(nextval('movieapp.users_id_seq'), 1, '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'admin@movieapp.com', '18B6738D69C2AA04', '8k5y7cuJmdBaS8FrE/BIJyUCEZNtzEG1+rwXRUPcG1A=', 'System', 'Administrator',
		'(111) 111-1111', 'ACTIVE', 0, CURRENT_TIMESTAMP, CURRENT_USER);

		
-- ********** End of USERS setup **********

-- ********** Begin of MOVIES setup **********

INSERT INTO movieapp.MOVIES (ID, UUID, TITLE, DURATION,
                    STORYLINE,
                    POSTER_URL,
                    TRAILER_URL,
                    WIKI_URL,
                    RELEASE_AT,
                    STATUS, CENSOR_BOARD_RATING, RATING, CREATED_BY)
		VALUES
		(nextval('movieapp.movies_id_seq'), '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Sanju', 162,
		'Coming from a family of cinematic legends, East Indian actor Sanjay Dutt reaches dizzying heights of success -- but also battles numerous addictions and other personal demons',
		'https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/Sanju_-_Theatrical_poster.jpg/220px-Sanju_-_Theatrical_poster.jpg',
		'https://www.youtube.com/watch?v=1J76wN0TPI4',
		'https://en.wikipedia.org/wiki/Sanju',
		 date '2018-06-29',
		'RELEASED', 'UA', 4.0, CURRENT_USER);

INSERT INTO movieapp.MOVIES (ID, UUID, TITLE, DURATION,
                    STORYLINE,
                    POSTER_URL,
                    TRAILER_URL,
                    WIKI_URL,
                    RELEASE_AT,
                    STATUS, CENSOR_BOARD_RATING, RATING, CREATED_BY)
		VALUES
		(nextval('movieapp.movies_id_seq'), '8d174a25-ca31-55a8-95b4-c06ffc9d5f8f', 'Incredibles 2', 118,
		'Helen is called on to lead a campaign to bring Supers back, while Bob navigates the day-to-day heroics of "normal" life at home with Violet, Dash, and baby Jack-Jack - whose superpowers are about to be discovered. Their mission is derailed, however, when a new villain emerges with a brilliant and dangerous plot that threatens everything. But the Parrs don''t shy away from a challenge, especially with Frozone by their side; that''s what makes this family so Incredible.',
		'https://upload.wikimedia.org/wikipedia/en/2/27/The_Incredibles_2.jpg',
		'https://www.youtube.com/watch?v=i5qOzqD9Rms',
		'https://en.wikipedia.org/wiki/Incredibles_2',
		 date '2018-06-22',
		'RELEASED', 'U', 4.0, CURRENT_USER);

-- ********** End of MOVIES setup **********

-- ********** Begin of MOVIE_GENRES setup **********
INSERT INTO movieapp.MOVIE_GENRES (ID, MOVIE_ID, GENRE_ID, CREATED_BY)
            VALUES
		    (nextval('movieapp.movie_genres_id_seq'),
            (select ID from movieapp.MOVIES where UUID = '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f'),
            (select ID from movieapp.GENRES where UUID = '1d174a25-ba31-45a8-85b4-b06ffc9d5f8f'), CURRENT_USER),
            (nextval('movieapp.movie_genres_id_seq'),
            (select ID from movieapp.MOVIES where UUID = '8d174a25-ca31-55a8-95b4-c06ffc9d5f8f'),
            (select ID from movieapp.GENRES where UUID = '4d174a25-ba31-45a8-85b4-b06ffc9d5f8f'), CURRENT_USER),
            (nextval('movieapp.movie_genres_id_seq'),
            (select ID from movieapp.MOVIES where UUID = '8d174a25-ca31-55a8-95b4-c06ffc9d5f8f'),
            (select ID from movieapp.GENRES where UUID = 'bb174a25-ba31-45a8-85b4-b06ffc9d5f8f'), CURRENT_USER),
            (nextval('movieapp.movie_genres_id_seq'),
            (select ID from movieapp.MOVIES where UUID = '8d174a25-ca31-55a8-95b4-c06ffc9d5f8f'),
            (select ID from movieapp.GENRES where UUID = '5d174a25-ba31-45a8-85b4-b06ffc9d5f8f'), CURRENT_USER);

-- ********** End of MOVIE_GENRES setup **********

-- ********** Begin of ARTISTS setup **********

INSERT INTO movieapp.ARTISTS (ID, UUID, FIRST_NAME, LAST_NAME, TYPE,
                    PROFILE_DESCRIPTION,
                    PROFILE_PICTURE_URL,
                    WIKI_URL, CREATED_BY)
		VALUES
		(nextval('movieapp.artists_id_seq'), '7c174b25-bb31-46a8-87b4-c06ffc9d5f8f', 'Ranbir', 'Kapoor', 'ACTOR',
		'Ranbir Kapoor (born 28 September 1982) is an Indian actor and film producer. He is one of the highest-paid actors of Hindi cinema and has featured in Forbes India''s Celebrity 100 list since 2012. Kapoor is the recipient of several awards, including five Filmfare Awards',
		'https://upload.wikimedia.org/wikipedia/commons/7/72/Ranbir_Kapoor_promoting_Bombay_Velvet.jpg',
		'https://en.wikipedia.org/wiki/Ranbir_Kapoor', CURRENT_USER);

INSERT INTO movieapp.ARTISTS (ID, UUID, FIRST_NAME, LAST_NAME, TYPE,
                    PROFILE_DESCRIPTION,
                    PROFILE_PICTURE_URL,
                    WIKI_URL, CREATED_BY)
		VALUES
		(nextval('movieapp.artists_id_seq'), '8c174b25-bb31-56a8-88b4-d06ffc9d5f89', 'Manisha', 'Koirala', 'ACTOR',
		'Manisha Koirala (born 16 August 1970) is a Nepalese actress who mainly appears in Hindi films, though she has worked in several South Indian and Nepali films. Noted for her acting prowess, Koirala is the recipient of several accolades, including four Filmfare Awards—and is one of India''s most well-known actresses',
		'https://upload.wikimedia.org/wikipedia/commons/2/20/Manisha_Koirala_graces_her_film_Dear_Maya%E2%80%99s_media_meet_%2805%29.jpg',
		'https://en.wikipedia.org/wiki/Manisha_Koirala', CURRENT_USER);

INSERT INTO movieapp.ARTISTS (ID, UUID, FIRST_NAME, LAST_NAME, TYPE,
                    PROFILE_DESCRIPTION,
                    PROFILE_PICTURE_URL,
                    WIKI_URL, CREATED_BY)
		VALUES
		(nextval('movieapp.artists_id_seq'), '9c174b25-cb31-66a8-98b4-d06ffc9d5f9f', 'Rajkumar', 'Hirani', 'DIRECTOR',
		'Rajkumar Hirani (born 20 November 1962) is an Indian film director and editor. He is widely regarded as one of the most successful and critically acclaimed filmmakers of the Hindi film industry. Hirani is known for directing the Hindi films Munna Bhai M.B.B.S (2003), Lage Raho Munnabhai (2006), 3 Idiots (2009), PK (2014) and Sanju (2018). All of his films have been huge commercial and critical successes.[2] Most have won several awards, including the national awards, and have often been regarded by the media and audiences as some of the most path-breaking films in the history of Indian cinema.[3][4] He has won 15 Filmfare Awards',
		'https://upload.wikimedia.org/wikipedia/commons/4/44/Rajkumar_Hirani_2014.jpg',
		'https://en.wikipedia.org/wiki/Rajkumar_Hirani', CURRENT_USER);

-- ********** End of ARTISTS setup **********

-- ********** Begin of MOVIE_ARTISTS setup **********
INSERT INTO movieapp.MOVIE_ARTISTS (ID, MOVIE_ID, ARTIST_ID, CREATED_BY)
            VALUES
		    (nextval('movieapp.movie_artists_id_seq'),
		    (select ID from movieapp.MOVIES where UUID = '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f'),
		    (select ID from movieapp.ARTISTS where UUID = '7c174b25-bb31-46a8-87b4-c06ffc9d5f8f'), CURRENT_USER),
		    (nextval('movieapp.movie_artists_id_seq'),
            (select ID from movieapp.MOVIES where UUID = '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f'),
            (select ID from movieapp.ARTISTS where UUID = '8c174b25-bb31-56a8-88b4-d06ffc9d5f89'), CURRENT_USER),
            (nextval('movieapp.movie_artists_id_seq'),
            (select ID from movieapp.MOVIES where UUID = '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f'),
            (select ID from movieapp.ARTISTS where UUID = '9c174b25-cb31-66a8-98b4-d06ffc9d5f9f'), CURRENT_USER);

-- ********** End of MOVIE_ARTISTS setup **********

-- ********** Begin of THEATRES setup **********

INSERT INTO movieapp.THEATRES (ID, UUID, NAME, POSTAL_ADDRESS, CITY_CODE, CREATED_BY)
		VALUES
		(nextval('movieapp.theatres_id_seq'), '7c174b25-bb31-46a8-87b4-c06ffc9d5f8f', 'PVR Cinemas', 'Worli, Mumbai', 'MUM', CURRENT_USER);

INSERT INTO movieapp.THEATRES (ID, UUID, NAME, POSTAL_ADDRESS, CITY_CODE, CREATED_BY)
		VALUES
		(nextval('movieapp.theatres_id_seq'), '8c174b25-bb31-56a8-88b4-d06ffc9d5f89', 'Inox Cinemas', 'Mulund, Mumbai', 'MUM', CURRENT_USER);

INSERT INTO movieapp.THEATRES (ID, UUID, NAME, POSTAL_ADDRESS, CITY_CODE, CREATED_BY)
		VALUES
		(nextval('movieapp.theatres_id_seq'), '9c174b25-cb31-66a8-98b4-d06ffc9d5f9f', 'PVR Cinemas', 'Koramangala, Bengaluru', 'BLR', CURRENT_USER);

INSERT INTO movieapp.THEATRES (ID, UUID, NAME, POSTAL_ADDRESS, CITY_CODE, CREATED_BY)
		VALUES
		(nextval('movieapp.theatres_id_seq'), '9d174b25-cb31-66a8-98b4-d06ffc9d5f9f', 'Inox Cinemas', 'ORR, Bengaluru', 'BLR', CURRENT_USER);

INSERT INTO movieapp.THEATRES (ID, UUID, NAME, POSTAL_ADDRESS, CITY_CODE, CREATED_BY)
		VALUES
		(nextval('movieapp.theatres_id_seq'), '9e174b25-cb31-66a8-98b4-d06ffc9d5f9f', 'Inox Cinemas', 'Connaught Place, New Delhi', 'DEL', CURRENT_USER);

INSERT INTO movieapp.THEATRES (ID, UUID, NAME, POSTAL_ADDRESS, CITY_CODE, CREATED_BY)
		VALUES
		(nextval('movieapp.theatres_id_seq'), '9f174b25-cb31-66a8-98b4-d06ffc9d5f9f', 'Maxx Cinemas', 'Chandni Chowk, New Delhi', 'DEL', CURRENT_USER);

-- ********** End of THEATRES setup **********