CREATE TABLE `movie_review` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `tmdb_movie_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `review_text` text NOT NULL,
    `rating` float,
    `likes` int DEFAULT 0,
    `created` datetime NOT NULL,
    `modified` datetime NOT NULL,
    PRIMARY KEY(`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;