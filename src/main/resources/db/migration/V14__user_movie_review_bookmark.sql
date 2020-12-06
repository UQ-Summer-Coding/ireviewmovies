CREATE TABLE `user_movie_review_bookmark` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `movie_review_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY(`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;