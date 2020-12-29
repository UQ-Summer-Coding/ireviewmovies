INSERT INTO `user`(id, email, full_name, `password`) VALUES(1, 'chol2@example.com', 'Chol Nhial', '$2a$10$RMF1BAKXr6HlzkjrC0L2fuwiTDfYA/L0TN/JZxSxNAuc0jJrwxW3C');
INSERT INTO movie_review (id, tmdb_movie_id, tmdb_movie_poster_path, user_id, title, review_text, rating, likes, created)
VALUES(1, 558, '/olxpyq9kJAZ2NU1siLshhhXEPR7.jpg', (SELECT id FROM `user` WHERE email='chol2@example.com'), 'Spider-Man 2', 'a good movie', 4.5, 0, NOW());
INSERT INTO user_movie_review_bookmark(id, movie_review_id, user_id, created) VALUES(1, 1, 1, NOW());
