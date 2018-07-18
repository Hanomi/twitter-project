DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nick` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `vk_id` int(11) DEFAULT NULL,
  `confirmation_token` varchar(36) DEFAULT NULL,
  `reset_token` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `users_id_index` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (1,'admin@gmail.com','$2a$11$z76gTidS0sj0cVBOJK5/XuAirD89DR2k4qA5CoNed.vASb2nYTCOC','Admin','',0,NULL,'05774c83-012a-4457-b697-673a3f949a61');
UNLOCK TABLES;