ALTER TABLE `movie_review`
ADD COLUMN `title` VARCHAR(255) NOT NULL
AFTER `user_id`;
