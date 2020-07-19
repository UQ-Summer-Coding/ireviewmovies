ALTER TABLE `user`
ADD COLUMN `profile_image` LONGTEXT DEFAULT NULL
AFTER `password`;
