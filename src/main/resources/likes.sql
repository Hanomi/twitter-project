DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) NOT NULL,
  `user` varchar(38) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `likes_messages_id_fk` (`message_id`),
  CONSTRAINT `likes_messages_id_fk` FOREIGN KEY (`message_id`) REFERENCES `messages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;