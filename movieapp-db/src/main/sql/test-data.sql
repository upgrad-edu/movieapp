--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: users; Type: TABLE DATA; Schema: movieapp; Owner: postgres
--

INSERT INTO movieapp.users (id, uuid, role_id, email, password, salt, first_name, last_name, mobile_phone, status, failed_login_count, last_password_change_at, last_login_at, subscriptions_consent)
        VALUES
        (1, '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 1, 'admin@movieapp.com', '18B6738D69C2AA04', '8k5y7cuJmdBaS8FrE/BIJyUCEZNtzEG1+rwXRUPcG1A=', 'System', 'Administrator', '(111) 111-1111', 'ACTIVE', 0, '2018-08-17 20:26:03.804275', NULL, true),
        (2, '6c84c29a-3a79-4a60-adad-d1cf9fb44575', 2, 'srishti@upgrad.com', 'CC4490907682D0FF', '04tfJRn9Ic097tSmvU2z7SxMM3jX2NDI/4DSfH0d2Hc=', 'Srishti', 'Gupta', '(111) 111-1111', 'ACTIVE', 0, NULL, '2018-08-27 15:56:42.678324', true);
SELECT pg_catalog.setval('movieapp.users_id_seq', 2, true);

--
-- Data for Name: artists; Type: TABLE DATA; Schema: movieapp; Owner: postgres
--

INSERT INTO movieapp.artists (id, uuid, first_name, last_name, type, profile_description, profile_picture_url, wiki_url, active)
            VALUES
            (1, '7c174b25-bb31-46a8-87b4-c06ffc9d5f8f', 'Ranbir', 'Kapoor', 'ACTOR', 'Ranbir Kapoor (born 28 September 1982) is an Indian actor and film producer. He is one of the highest-paid actors of Hindi cinema and has featured in Forbes India''s Celebrity 100 list since 2012. Kapoor is the recipient of several awards, including five Filmfare Awards', 'https://upload.wikimedia.org/wikipedia/commons/7/72/Ranbir_Kapoor_promoting_Bombay_Velvet.jpg', 'https://en.wikipedia.org/wiki/Ranbir_Kapoor', true),
            (2, '8c174b25-bb31-56a8-88b4-d06ffc9d5f89', 'Manisha', 'Koirala', 'ACTOR', 'Manisha Koirala (born 16 August 1970) is a Nepalese actress who mainly appears in Hindi films, though she has worked in several South Indian and Nepali films. Noted for her acting prowess, Koirala is the recipient of several accolades, including four Filmfare Awards—and is one of India''s most well-known actresses', 'https://upload.wikimedia.org/wikipedia/commons/2/20/Manisha_Koirala_graces_her_film_Dear_Maya%E2%80%99s_media_meet_%2805%29.jpg', 'https://en.wikipedia.org/wiki/Manisha_Koirala', true),
            (3, '9c174b25-cb31-66a8-98b4-d06ffc9d5f9f', 'Rajkumar', 'Hirani', 'DIRECTOR', 'Rajkumar Hirani (born 20 November 1962) is an Indian film director and editor. He is widely regarded as one of the most successful and critically acclaimed filmmakers of the Hindi film industry. Hirani is known for directing the Hindi films Munna Bhai M.B.B.S (2003), Lage Raho Munnabhai (2006), 3 Idiots (2009), PK (2014) and Sanju (2018). All of his films have been huge commercial and critical successes.[2] Most have won several awards, including the national awards, and have often been regarded by the media and audiences as some of the most path-breaking films in the history of Indian cinema.[3][4] He has won 15 Filmfare Awards', 'https://upload.wikimedia.org/wikipedia/commons/4/44/Rajkumar_Hirani_2014.jpg', 'https://en.wikipedia.org/wiki/Rajkumar_Hirani', true),
            (4, 'f114b346-a237-11e8-9077-720006ceb890', 'Marlon', 'Brando', 'ACTOR', 'Marlon Brando Jr. was an American actor and film director. He is credited with bringing realism to film acting and helping to popularize the Stanislavski system of acting having studied with Stella Adler in the 1940s. Regarded for his cultural influence on 20th century film, Brandos Academy Award-winning performances include that of Terry Malloy in On the Waterfront (1954) and Don Vito Corleone in The Godfather (1972). Brando was an activist for many causes, notably the civil rights movement and various Native American movements.', 'https://upload.wikimedia.org/wikipedia/commons/e/e5/Marlon_Brando_%28cropped%29.jpg', 'https://en.wikipedia.org/wiki/Marlon_Brando', true),
            (5, '24614e76-a238-11e8-9077-720006ceb890', 'Leonardo', 'DiCaprio', 'ACTOR', 'Leonardo Wilhelm DiCaprio is an American actor and film producer. DiCaprio began his career by appearing in television commercials in the late 1980s. He next had recurring roles in various television series, such as the soap opera Santa Barbara and the sitcom Growing Pains. DiCaprios portrayals of Howard Hughes in The Aviator (2004) and Hugh Glass in The Revenant won him the Golden Globe Award for Best Actor – Motion Picture Drama. His performance as Jordan Belfort in The Wolf of Wall Street won him the Golden Globe award for Best Actor – Motion Picture Musical or Comedy. He also won the Academy Award for Best Actor and BAFTA Award for his performance in The Revenant. DiCaprio is the founder of his own production company, Appian Way Productions.', 'https://upload.wikimedia.org/wikipedia/commons/3/3f/Leonardo_DiCaprio_visited_Goddard_Saturday_to_discuss_Earth_science_with_Piers_Sellers_%2826105091624%29_cropped.jpg', 'https://en.wikipedia.org/wiki/Leonardo_DiCaprio', true),
            (6, '24615498-a238-11e8-9077-720006ceb890', 'Joseph', 'Gordon-Levitt', 'ACTOR', 'Joseph Leonard Gordon-Levitt is an American actor, filmmaker, singer, and entrepreneur. As a child, Gordon-Levitt appeared in many films and TV series. He took a break from acting to study at Columbia University, but dropped out in 2004 to pursue acting again. He has since starred in  films like (500) Days of Summer, Inception, The Dark Knight Rises, G.I. Joe: The Rise of Cobra and others. For his leading performances in (500) Days of Summer and 50/50, he was nominated for the Golden Globe Award for Best Actor – Motion Picture Musical or Comedy.', 'https://upload.wikimedia.org/wikipedia/commons/7/7d/Joseph_Gordon-Levitt_2013.jpg', 'https://en.wikipedia.org/wiki/Joseph_Gordon-Levitt', true),
            (7, '2461589e-a238-11e8-9077-720006ceb890', 'Matthew', 'McConaughey', 'ACTOR', 'Matthew David McConaughey is an American actor, producer, model, writer and director. McConaughey achieved ample success in 2013 and 2014. In 2013, McConaughey portrayed Ron Woodroof, a cowboy diagnosed with AIDS in the biographical film Dallas Buyers Club, which earned him the Academy Award, Critics Choice Movie Award, Golden Globe Award, and Screen Actors Guild Award, all for Best Actor, among other awards and nominations. In 2014, he starred as Rust Cohle in the first season of HBOs crime drama anthology series True Detective, for which he won the Critics Choice Television Award and TCA Award, and was nominated for the Primetime Emmy Award, Golden Globe Award, and Screen Actors Guild Award.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Matthew_McConaughey_-_Goldene_Kamera_2014_-_Berlin.jpg/1024px-Matthew_McConaughey_-_Goldene_Kamera_2014_-_Berlin.jpg', 'https://en.wikipedia.org/wiki/Matthew_McConaughey', true),
            (8, '24615c0e-a238-11e8-9077-720006ceb890', 'Anne', 'Hathaway', 'ACTOR', 'Anne Jacqueline Hathaway is an American actress and singer. One of the worlds highest-paid actresses in 2015, she has received multiple awards, including an Academy Award, a Golden Globe, a British Academy Film Award, and an Emmy. Her films have earned $6.4 billion worldwide, and she appeared in the Forbes Celebrity 100 in 2009.', 'https://upload.wikimedia.org/wikipedia/commons/b/bd/Anne_Hathaway_in_2017.png', 'https://en.wikipedia.org/wiki/Anne_Hathaway', true),
            (9, '24615f4c-a238-11e8-9077-720006ceb890', 'Rajkummar', 'Rao', 'ACTOR', 'Rajkummar Rao, also known as Rajkumar Yadav, is an Indian actor. He has established a career in Hindi cinema and is the recipient of several awards, including a National Film Award, three Filmfare Awards, and an Asia Pacific Screen Award. He is cited in the media as one of the most talented actors of his generation.', 'https://en.wikipedia.org/wiki/Rajkummar_Rao#/media/File:Rajkummar_Rao_World_Premiere_Newton_Zoopalast_Berlinale_2017_02.jpg', 'https://en.wikipedia.org/wiki/Rajkummar_Rao', true),
            (10, '246162a8-a238-11e8-9077-720006ceb890', 'Kay Kay', 'Menon', 'ACTOR', 'Kay Kay Menon is an Indian film, stage and television actor who works predominantly in Hindi cinema, and also in Gujarati, Tamil and Telugu cinema. He has also won the award for best actor for the film Shoonya from Festival of Arab and Asian cinema', 'https://upload.wikimedia.org/wikipedia/commons/a/ac/Kay_Kay_Menon_at_libas_store.jpg', 'https://en.wikipedia.org/wiki/Kay_Kay_Menon', true),
            (11, '246165d2-a238-11e8-9077-720006ceb890', 'Anthony', 'LaPaglia', 'ACTOR', 'Anthony M. LaPaglia is an Australian actor. He played the role of Joe in the coming of age comedy Empire Records and John in the film Autumn In New York, as well as FBI agent Jack Malone on the American TV series Without a Trace, for which he won a Golden Globe Award for Best Actor – Television Series Drama. He also appeared in 8 episodes of Frasier as Daphne Moons alcoholic brother Simon.', 'https://upload.wikimedia.org/wikipedia/commons/7/7c/Anthony_LaPaglia_and_Gia_Carides_at_the_Man_of_Steel_premiere_in_Sydney_%289123807673%29.jpg', 'https://en.wikipedia.org/wiki/Anthony_LaPaglia', true),
            (12, '2461973c-a238-11e8-9077-720006ceb890', 'Tom', 'Hardy', 'ACTOR', 'Edward Thomas Hardy is an English actor, producer, and former model. Hardy made his debut in the Ridley Scott film Black Hawk Down, and has since had notable roles in films such as Star Trek: Nemesis, RocknRolla, Bronson, Warrior, Tinker Tailor Soldier Spy, Lawless, Locke, The Drop, and The Revenant, for which he received a nomination for the Academy Award for Best Supporting Actor. In 2015, Hardy portrayed Mad Max Rockatansky in Mad Max: Fury Road and both Kray twins in Legend. He has also appeared in three Christopher Nolan films: Inception, as Bane in The Dark Knight Rises, and Dunkirk', 'https://upload.wikimedia.org/wikipedia/commons/3/30/Tom_Hardy_Locke_Premiere.jpg', 'https://en.wikipedia.org/wiki/Tom_Hardy', true),
            (13, '359f7e8a-a23b-11e8-9077-720006ceb890', 'Al', 'Pacino', 'ACTOR', 'Alfredo James Pacino is an American actor and filmmaker. Pacino has had a career spanning over five decades, during which time he has received numerous accolades and honors both competitive and honorary, among them an Academy Award, two Tony Awards, two Primetime Emmy Awards, a British Academy Film Award, four Golden Globe Awards, the Lifetime Achievement Award from the American Film Institute, the Golden Globe Cecil B. DeMille Award, and the National Medal of Arts. He is also one of few performers to have won a competitive Oscar, an Emmy, and a Tony Award for acting, dubbed the Triple Crown of Acting.', 'https://upload.wikimedia.org/wikipedia/commons/9/98/Al_Pacino.jpg', 'https://en.wikipedia.org/wiki/Al_Pacino', true),
            (14, '5485e5b4-a23b-11e8-9077-720006ceb890', 'Christian', 'Bale', 'ACTOR', 'Christian Charles Philip Bale is an English actor and producer. He has starred both in blockbuster films and smaller projects from independent producers and art houses. Born in Haverfordwest, Wales, to English parents, he first caught the public eye at the age of 13, when he was cast in the starring role of Steven Spielbergs Empire of the Sun. After a string of semi-successful feature films, he portrayed Wall Street banker and serial killer Patrick Bateman in American Psycho to widespread critical acclaim. His reputation for going great lengths to portray characters in films was first noted in the psychological thriller The Machinist, where he lost 28.5 kg to play the main lead. Within six months he gained 45 kg to star as Batman in Christopher Nolans Batman Begins', 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Christian_Bale_2014_%28cropped%29.jpg/1024px-Christian_Bale_2014_%28cropped%29.jpg', 'https://en.wikipedia.org/wiki/Christian_Bale', true),
            (15, '5485eb18-a23b-11e8-9077-720006ceb890', 'Heath', 'Ledger', 'ACTOR', 'Heath Andrew Ledger was an Australian actor and director. After performing roles in several Australian television and film productions during the 1990s, Ledger left for the United States in 1998 to further develop his film career. His work comprised nineteen films, including Brokeback Mountain and The Dark Knight. Ledger received numerous posthumous accolades for his critically acclaimed performance in the film The Dark Knight, including the Academy Award for Best Supporting Actor and Best Actor International Award at the 2008 Australian Film Institute Awards', 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Heath_Ledger_%28Berlin_Film_Festival_2006%29_revised.jpg/1024px-Heath_Ledger_%28Berlin_Film_Festival_2006%29_revised.jpg', 'https://en.wikipedia.org/wiki/Heath_Ledger', true),
            (16, '3097b8f4-a294-11e8-9a3a-720006ceb890', 'Peter', 'Berg', 'DIRECTOR', 'Peter Berg (born March 11, 1964) is an American director, producer, writer, and actor. In television, Berg developed Friday Night Lights (2006–2011), adapted from his film, earning two Primetime Emmy Award nominations. As an actor, he is best known for his role as Dr. Billy Kronk on the CBS medical drama Chicago Hope (1995–1999).', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Peter_Berg_by_Gage_Skidmore.jpg/440px-Peter_Berg_by_Gage_Skidmore.jpg', 'https://en.wikipedia.org/wiki/Peter_Berg', true),
            (17, '9df46816-a294-11e8-9a3a-720006ceb890', 'Mark', 'Wahlberg', 'PRODUCER', 'PMark Robert Michael Wahlberg (born June 5, 1971) is an American actor, producer, businessman, former model, rapper and songwriter. He was known by his stage name Marky Mark in his early career as frontman for the group Marky Mark and the Funky Bunch, releasing the albums Music for the People and You Gotta Believe.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Mark_Wahlberg_2017.jpg/440px-Mark_Wahlberg_2017.jpg', 'https://en.wikipedia.org/wiki/Mark_Wahlberg', true),
            (19, '1dd86f90-a296-11e8-9a3a-720006ceb890', 'John', 'Malkovich', 'ACTOR', 'John Gavin Malkovich (born December 9, 1953) is an American actor, director, producer and fashion designer. He has appeared in more than 70 films. For his roles in Places in the Heart and In the Line of Fire, he received Academy Award nominations. He appeared in such films as Empire of the Sun, The Killing Fields, Con Air, The Sheltering Sky, Of Mice and Men, Rounders, Knockaround Guys, Being John Malkovich, Shadow of the Vampire, Burn After Reading, Red, Red 2, Mulholland Falls, Dangerous Liaisons, and Warm Bodies, as well as producing films such as Ghost World, Juno, and The Perks of Being a Wallflower.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/John_Malkovich_at_a_screening_of_%22Casanova_Variations%22_in_January_2015.jpg/440px-John_Malkovich_at_a_screening_of_%22Casanova_Variations%22_in_January_2015.jpg', 'https://en.wikipedia.org/wiki/John_Malkovich', true),
            (20, 'c860e78a-a29b-11e8-9a3a-720006ceb890', 'Robert', 'De Niro', 'ACTOR', 'Robert Anthony De Niro Jr. (/də ˈnɪəroʊ/; born August 17, 1943) is an American actor, producer, and director. De Niro was cast as the young Vito Corleone in the 1974 film The Godfather Part II, for which he won the Academy Award for Best Supporting Actor. His longtime collaboration with director Martin Scorsese earned him the Academy Award for Best Actor for his portrayal of Jake LaMotta in the 1980 film Raging Bull. He received the AFI Life Achievement Award in 2003, the Golden Globe Cecil B. DeMille Award in 2010, and the Presidential Medal of Freedom from President Barack Obama in 2016.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/Robert_De_Niro_KVIFF_portrait.jpg/440px-Robert_De_Niro_KVIFF_portrait.jpg', 'https://en.wikipedia.org/wiki/Robert_De_Niro', true),
            (21, '19a4b6b2-a29c-11e8-9a3a-720006ceb890', 'Joe', 'Pesci', 'ACTOR', 'Joseph Frank Pesci (born February 9, 1943) is an American actor, comedian and singer. Known for portraying tough, volatile characters, in a variety of genres, he is best known for his role as Vincent Gambini in My Cousin Vinny (1992), Harry Lime in Home Alone and Home Alone 2: Lost in New York, as Leo Getz in the Lethal Weapon franchise, and for co-starring with Robert De Niro in the Martin Scorsese films Raging Bull (1980), Goodfellas (1990), and Casino (1995).', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/JoePesci-2009.jpg/440px-JoePesci-2009.jpg', 'https://en.wikipedia.org/wiki/Joe_Pesci', true);
SELECT pg_catalog.setval('movieapp.artists_id_seq', 21, true);


--
-- Data for Name: movies; Type: TABLE DATA; Schema: movieapp; Owner: postgres
--

INSERT INTO movieapp.movies (id, uuid, title, duration, storyline, poster_url, trailer_url, wiki_url, release_at, censor_board_rating, rating, status)
        VALUES
        (1, '7d174a25-ba31-45a8-85b4-b06ffc9d5f8f', 'Sanju', 162, 'Coming from a family of cinematic legends, East Indian actor Sanjay Dutt reaches dizzying heights of success -- but also battles numerous addictions and other personal demons', 'https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/Sanju_-_Theatrical_poster.jpg/220px-Sanju_-_Theatrical_poster.jpg', 'https://www.youtube.com/watch?v=1J76wN0TPI4', 'https://en.wikipedia.org/wiki/Sanju', '2018-06-29 00:00:00', 'UA', 4.0, 'RELEASED'),
        (3, '009ae262-a234-11e8-b475-720006ceb890', 'The Godfather', 177, 'A chilling portrait of the Corleone familys rise and near fall from power in America along with balancing the story of the Sicilian clans ugly crime business in which they are engaged.', 'https://upload.wikimedia.org/wikipedia/en/1/1c/Godfather_ver1.jpg', 'https://www.youtube.com/watch/?v=sY1S34973zA', 'https://en.wikipedia.org/wiki/The_Godfather', '1972-03-15 00:00:00', 'A', 9.2, 'RELEASED'),
        (4, '00ae33e8-a235-11e8-9077-720006ceb890', 'The Revenant', 156, 'A frontiersman on a fur trading expedition in the 1820s fights for survival after being mauled by a bear and left for dead by members of his own hunting team.', 'https://upload.wikimedia.org/wikipedia/en/b/b6/The_Revenant_2015_film_poster.jpg', 'https://www.youtube.com/watch?v=LoebZZ8K5N0', 'https://en.wikipedia.org/wiki/The_Revenant_(2015_film)', '2015-12-16 00:00:00', 'UA', 8.0, 'RELEASED'),
        (5, '066e720c-a235-11e8-9077-720006ceb890', 'Annabelle: Creation', 109, '12 years after the tragic death of their little girl, a dollmaker and his wife welcome a nun and several girls from a shuttered orphanage into their home, where they soon become the target of the dollmakers possessed creation, Annabelle.', 'https://upload.wikimedia.org/wikipedia/en/0/08/Annabelle_Creation.jpg', 'https://www.youtube.com/watch?v=KisPhy7T__Q', 'https://en.wikipedia.org/wiki/Annabelle:_Creation', '2017-08-11 00:00:00', 'A', 6.6, 'RELEASED'),
        (6, '0c364cd2-a235-11e8-9077-720006ceb890', 'Shahid', 129, 'Shahid Azmi becomes an unlikely champion of human rights, particularly for Indias Muslim minority.', 'https://upload.wikimedia.org/wikipedia/en/c/cd/Shahid_Poster_%282013%29.jpg', 'https://www.youtube.com/watch?v=XiQXmIn7qbI', 'https://en.wikipedia.org/wiki/Shahid_(film)', '2013-10-18 00:00:00', 'U', 8.6, 'RELEASED'),
        (8, '52974be0-a235-11e8-9077-720006ceb890', 'Inception', 148, 'A thief, who steals corporate secrets through the use of dream-sharing technology, is given the inverse task of planting an idea into the mind of a CEO.', 'https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg', 'https://www.youtube.com/watch?v=8hP9D6kZseM', 'https://en.wikipedia.org/wiki/Inception', '2010-07-16 00:00:00', 'UA', 8.8, 'RELEASED'),
        (7, '52974690-a235-11e8-9077-720006ceb890', 'The Dark Knight', 152, 'When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham. The Dark Knight must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'https://upload.wikimedia.org/wikipedia/en/8/8a/Dark_Knight.jpg', 'https://www.youtube.com/watch?v=EXeTwQWrcwY', 'https://en.wikipedia.org/wiki/The_Dark_Knight_(film)', '2008-07-18 00:00:00', 'A', 9.0, 'RELEASED'),
        (9, '52975022-a235-11e8-9077-720006ceb890', 'Interstellar', 169, 'A team of explorers travel beyond this galaxy through a newly discovered wormhole to discover whether mankind has a future among the stars.', 'https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg', 'https://www.youtube.com/watch?v=2LqzF5WauAw', 'https://en.wikipedia.org/wiki/Interstellar_(film)', '2014-11-07 00:00:00', 'UA', 8.6, 'RELEASED'),
        (12, '8d71c3b8-a293-11e8-9a3a-720006ceb890', 'Mile 22', 95, 'Mile 22 is a 2018 American action thriller film directed by Peter Berg and written by Lea Carpenter, from a story by Carpenter and Graham Roland. The film stars Mark Wahlberg, John Malkovich, Lauren Cohan, Iko Uwais, Ronda Rousey, and follows an elite CIA task force, composed of paramilitary officers from Ground Branch of Special Activities Division, that has to escort a high-priority asset 22 miles to an extraction point while being hunted by terrorists.', 'https://upload.wikimedia.org/wikipedia/en/4/41/Mile_22.png', 'https://www.youtube.com/watch?v=eJU6S5KOsNI', 'https://en.wikipedia.org/wiki/Mile_22', '2018-09-19 00:00:00', 'UA', 6.1, 'PUBLISHED'),
        (13, '65444278-a29b-11e8-9a3a-720006ceb890', 'The Irishman', 100, 'The Irishman is an upcoming American biographical crime film directed by Martin Scorsese and written by Steven Zaillian, based on the book I Heard You Paint Houses by Charles Brandt. The film stars Robert De Niro as Frank Sheeran, a labor union leader and alleged hitman for the Bufalino crime family, and Al Pacino as Jimmy Hoffa. Joe Pesci, Anna Paquin, Bobby Cannavale, Harvey Keitel, and Ray Romano also star.', 'https://m.media-amazon.com/images/M/MV5BZjcyMmYwZTUtMzVjZi00YjI5LWEwNDktNDQ1ZGY2M2FlYTBiXkEyXkFqcGdeQXVyMzE3OTk0NjQ@._V1_UY268_CR6,0,182,268_AL__QL50.jpg', 'https://www.youtube.com/watch?v=uu1KNOUvzPQ', 'https://en.wikipedia.org/wiki/The_Irishman_(2019_film)', '2019-01-01 00:00:00', 'UA', 8.0, 'PUBLISHED'),
        (14, '19f35c78-a2a0-11e8-9a3a-720006ceb890', 'Bad Boys For Life', 100, 'In June 2008, Bay stated that he may direct Bad Boys III, but that the greatest obstacle to the potential sequel would be the cost, as he and Will Smith demand some of the highest salaries in the film industry. By August 2009, Columbia Pictures had hired Peter Craig to write the script for Bad Boys III. In February 2011, Martin Lawrence reiterated that the film was in development.', 'https://upload.wikimedia.org/wikipedia/en/e/e3/Bad_Boys_Limited_Edition_Cover.jpg', 'https://www.youtube.com/watch?v=e-alJMlZUH4', 'https://en.wikipedia.org/wiki/Bad_Boys_(franchise)', '2019-01-01 00:00:00', 'UA', 8.0, 'PUBLISHED'),
        (15, '643d8d80-a2a0-11e8-9a3a-720006ceb890', 'Mowgli', 100, 'Mowgli is an upcoming adventure fantasy film directed by Andy Serkis and written by Callie Kloves, based on The Jungle Book by Rudyard Kipling. The film stars Rohan Chand, Matthew Rhys and Freida Pinto, along with voice and motion capture performances from Christian Bale, Cate Blanchett, Benedict Cumberbatch, Naomie Harris and Serkis.', 'https://upload.wikimedia.org/wikipedia/en/e/ec/Mowgli_teaser_poster.jpg', 'https://www.youtube.com/watch?v=9wY_vb4pkLs', 'https://en.wikipedia.org/wiki/Mowgli_(film)', '2019-01-01 00:00:00', 'UA', 8.0, 'PUBLISHED'),
        (16, 'd088f038-a2a0-11e8-9a3a-720006ceb890', 'Hellboy', 100, 'Hellboy is an upcoming American supernatural superhero film directed by Neil Marshall, based on the Dark Horse Comics character Hellboy. The film serves as a reboot to the Hellboy film series and stars David Harbour, Milla Jovovich, Ian McShane, Sasha Lane, Brian Gleeson, Daniel Dae Kim, and Sophie Okonedo. The film is scheduled to be released on January 11, 2019.', 'https://upload.wikimedia.org/wikipedia/en/7/72/Hellboy_Rise_of_the_Blood_Queen_Cannes_Artwork.jpg', 'https://www.youtube.com/watch?v=Ob9J3kCELXE', 'https://en.wikipedia.org/wiki/Hellboy_(2019_film)', '2019-01-01 00:00:00', 'UA', 8.0, 'PUBLISHED'),
        (17, '16ddc57c-a2a1-11e8-9a3a-720006ceb890', 'Ad Astra', 100, 'Ad Astra is an upcoming American epic science fiction thriller film directed by James Gray, and written by Gray and Ethan Gross. The film will star Brad Pitt, Tommy Lee Jones, Ruth Negga, Donald Sutherland and Jamie Kennedy.', 'https://static1.squarespace.com/static/57004e4a20c647bc9420760b/t/58345640bebafbb1e66b78ce/1479824966458/AD+Astra+poster.jpg?format=500w', 'https://www.youtube.com/watch?v=NTDgOOA2f-A', 'https://en.wikipedia.org/wiki/Ad_Astra_(film)', '2019-01-01 00:00:00', 'UA', 8.0, 'PUBLISHED'),
        (18, '5d49d32e-a2a2-11e8-9a3a-720006ceb890', 'The Nun', 96, 'The Nun is an upcoming American gothic supernatural horror film directed by Corin Hardy. The screenplay by Gary Dauberman is from a story by James Wan and Dauberman. It is a spin-off of 2016s The Conjuring 2 and the fifth installment in The Conjuring series, and stars Demián Bichir, Taissa Farmiga and Jonas Bloquet.', 'https://upload.wikimedia.org/wikipedia/en/3/34/TheNunPoster.jpg', 'https://www.youtube.com/watch?v=pzD9zGcUNrw', 'https://en.wikipedia.org/wiki/The_Nun_(2018_film)', '2018-09-07 00:00:00', 'UA', 8.0, 'PUBLISHED'),
        (19, '7281988a-a2a2-11e8-9a3a-720006ceb890', 'A Star IS Born', 135, 'A Star Is Born is an upcoming American musical romantic drama film produced and directed by Bradley Cooper, in his directorial debut. Cooper also wrote the screenplay with Will Fetters and Eric Roth. A remake of the 1937 film of the same name, it stars Cooper, Lady Gaga, Andrew Dice Clay, Dave Chappelle, and Sam Elliott, and follows a hard-drinking country musician (Cooper) who discovers and falls in love with a young singer (Gaga).', 'https://upload.wikimedia.org/wikipedia/en/3/39/A_Star_is_Born.png', 'https://www.youtube.com/watch?v=nSbzyEJ8X9E', 'https://en.wikipedia.org/wiki/A_Star_Is_Born_(2018_film)', '2018-10-05 00:00:00', 'UA', 8.0, 'PUBLISHED'),
        (20, '7e967dac-a2a2-11e8-9a3a-720006ceb890', 'Paltan', 150, 'Paltan is an upcoming Indian war film directed and produced by J.P. Dutta, based on 1967 Nathu La and Cho La clashes along the Sikkim border after 1962 Sino-Indian War. It stars an ensemble cast with Jackie Shroff, Arjun Rampal, Sonu Sood, Harshvardhan Rane, Esha Gupta, Sonal Chauhan and many more. It is set to be released on 7 September 2018.', 'https://upload.wikimedia.org/wikipedia/en/6/64/Paltan_2018.jpg', 'https://www.youtube.com/watch?v=kk6btnMEKTQ', 'https://en.wikipedia.org/wiki/Paltan_(film)', '2018-09-07 00:00:00', 'UA', 8.0, 'PUBLISHED');
SELECT pg_catalog.setval('movieapp.movies_id_seq', 20, true);

--
-- Data for Name: theatres; Type: TABLE DATA; Schema: movieapp; Owner: postgres
--

INSERT INTO movieapp.theatres (id, uuid, version, name, postal_address, city_code, active)
        VALUES
        (1, '7c174b25-bb31-46a8-87b4-c06ffc9d5f8f', 1, 'PVR Cinemas', 'Worli, Mumbai', 'MUM', true),
        (2, '8c174b25-bb31-56a8-88b4-d06ffc9d5f89', 2, 'Inox Cinemas', 'Mulund, Mumbai', 'MUM', true),
        (3, '9c174b25-cb31-66a8-98b4-d06ffc9d5f9f', 3, 'PVR Cinemas', 'Koramangala, Bengaluru', 'BLR', true),
        (4, '9d174b25-cb31-66a8-98b4-d06ffc9d5f9f', 4, 'Inox Cinemas', 'ORR, Bengaluru', 'BLR', true),
        (5, '9e174b25-cb31-66a8-98b4-d06ffc9d5f9f', 5, 'Inox Cinemas', 'Connaught Place, New Delhi', 'DEL', true),
        (6, '9f174b25-cb31-66a8-98b4-d06ffc9d5f9f', 6, 'Maxx Cinemas', 'Chandni Chowk, New Delhi', 'DEL', true);
SELECT pg_catalog.setval('movieapp.theatres_id_seq', 6, true);

--
-- Data for Name: shows; Type: TABLE DATA; Schema: movieapp; Owner: postgres
--

INSERT INTO movieapp.shows (id, uuid, movie_id, theatre_id, start_time, end_time, lang, unit_price, total_seats, available_seats, active)
        VALUES
        (1, '64a087d1-3232-4e17-b715-5d4e94f7f536', 4, 1, '2018-08-29 12:00:00', '2018-08-14 14:51:00', 'MARATHI', 900.00, 150, 150, true),
        (2, '1e623f93-3095-4ea4-97e3-5ff5c2a7cad0', 4, 3, '2018-09-20 12:00:00', '2018-08-14 14:51:00', 'ENGLISH', 900.00, 150, 150, true),
        (3, '3ce0cc8b-2a77-4125-b521-418a82428a77', 4, 2, '2018-08-30 12:00:00', '2018-08-14 14:51:00', 'ENGLISH', 1000.00, 200, 200, true),
        (4, '9f7be9a1-30ad-4c93-b5ca-615606bc9690', 4, 5, '2018-09-01 12:00:00', '2018-08-14 14:51:00', 'HINDI', 400.00, 500, 500, true),
        (5, '462b90b8-2a9c-47ec-9a03-e492c201c828', 4, 4, '2018-09-05 12:00:00', '2018-08-14 14:51:00', 'HINDI', 600.00, 350, 350, true);
SELECT pg_catalog.setval('movieapp.shows_id_seq', 5, true);

--
-- Data for Name: movie_artists; Type: TABLE DATA; Schema: movieapp; Owner: postgres
--

INSERT INTO movieapp.movie_artists (id, movie_id, artist_id)
        VALUES
        (1, 1, 1),
        (2, 1, 2),
        (3, 1, 3),
        (4, 3, 4),
        (5, 3, 13),
        (6, 4, 5),
        (7, 4, 12),
        (8, 5, 11),
        (9, 6, 9),
        (10, 6, 10),
        (11, 7, 14),
        (12, 7, 15),
        (13, 8, 5),
        (14, 8, 6),
        (15, 9, 7),
        (16, 9, 8),
        (17, 12, 16),
        (18, 12, 17),
        (19, 12, 19);
SELECT pg_catalog.setval('movieapp.movie_artists_id_seq', 19, true);

--
-- Data for Name: movie_genres; Type: TABLE DATA; Schema: movieapp; Owner: postgres
--

INSERT INTO movieapp.movie_genres (id, movie_id, genre_id)
        VALUES
        (1, 1, 1),
        (5, 3, 1),
        (6, 3, 5),
        (7, 4, 4),
        (8, 4, 11),
        (9, 4, 1),
        (10, 5, 3),
        (11, 5, 10),
        (12, 6, 1),
        (13, 6, 5),
        (14, 6, 8),
        (15, 7, 1),
        (16, 7, 4),
        (17, 7, 5),
        (18, 8, 4),
        (19, 8, 11),
        (20, 8, 13),
        (21, 9, 1),
        (22, 9, 11),
        (23, 9, 13),
        (24, 12, 4),
        (25, 12, 6);
SELECT pg_catalog.setval('movieapp.movie_genres_id_seq', 25, true);

--
-- PostgreSQL database dump complete
--
