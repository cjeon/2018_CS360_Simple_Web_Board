INSERT INTO `user` (`id`, `created_at`, `is_admin`, `password`, `updated_at`, `user_id`) VALUES (1,'2018-11-06 00:00:00',b'1','admin','2018-11-06 00:00:00','admin');
INSERT INTO `user` (`id`, `created_at`, `is_admin`, `password`, `updated_at`, `user_id`) VALUES (2,'2018-11-06 00:00:00',b'0','user','2018-11-06 00:00:00','user');
INSERT INTO `post` (`id`, `created_at`, `text`, `title`, `updated_at`, `user_id`) VALUES (1, '2018-11-12 14:45:07', '1', '1', '2018-11-12 14:45:07', '1');
INSERT INTO `comment` (`id`, `created_at`, `text`, `updated_at`, `post_id`, `user_id`) VALUES (1, '2018-11-15 00:00:00', 'comment 1', '2018-11-15 00:00:00', 1, 1), (2, '2018-11-15 00:00:00', 'comment 2', '2018-11-15 00:00:00', 1, 2);
create view `user_id_pw` as select u.id, u.user_id, u.password, u.is_admin from user as u;
CREATE TRIGGER text_check BEFORE INSERT ON post FOR EACH ROW IF NEW.text = "" THEN SET NEW.text = "(; END IF;no content)"