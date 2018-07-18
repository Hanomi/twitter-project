DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(250) NOT NULL,
  `date` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `retweet` int(11) DEFAULT NULL,
  `thread` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `lft` int(11) DEFAULT NULL,
  `rgt` int(11) DEFAULT NULL,
  `lvl` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `messages_users_id_fk` (`user_id`),
  CONSTRAINT `messages_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;