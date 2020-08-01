ALTER TABLE `movie` ADD COLUMN `description` TEXT NOT NULL AFTER `name`;
ALTER TABLE `movie` ADD COLUMN `year` bigint(20) AFTER `description`;
