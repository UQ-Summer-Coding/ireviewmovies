-- SET MODIFIED FIELD AUTOMATICALLY ON UPDATE -
 ALTER TABLE `movie_review`
             CHANGE modified
                    modified TIMESTAMP NOT NULL
                                DEFAULT CURRENT_TIMESTAMP
                                ON UPDATE CURRENT_TIMESTAMP;

-- ADD NEW COLUMN TO STORE MOVIE POST PATH --
ALTER TABLE `movie_review`
ADD COLUMN `tmdb_movie_poster_path` varchar(255) NOT NULL
AFTER `tmdb_movie_id`;