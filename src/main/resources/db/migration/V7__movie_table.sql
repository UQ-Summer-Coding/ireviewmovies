CREATE TABLE `movie` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `tmdb_movie_id` bigint(20) NOT NULL,
                                `tmdb_movie_poster_path` varchar(255) NOT NULL,
                                `average_rating` float,
                                `total_reviews` int DEFAULT 0,
                                `created` datetime NOT NULL,
                                `modified` TIMESTAMP NOT NULL
                                    DEFAULT CURRENT_TIMESTAMP
                                    ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY(`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;