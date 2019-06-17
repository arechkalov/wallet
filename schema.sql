DROP TABLE IF EXISTS `balance`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `currency`;

CREATE TABLE `currency`
(
  `currency_id`   bigint(100) NOT NULL,
  `currency_name` varchar(3)  NOT NULL,
  PRIMARY KEY (`currency_id`),
  UNIQUE KEY `currency_name_UNIQUE` (`currency_name`)
);

CREATE TABLE `user`
(
  `user_id` bigint(100) NOT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `balance`
(
  `balance_id`  bigint(100)   NOT NULL,
  `amount`      decimal(4, 0) NOT NULL,
  `currency_id` bigint(100)   NOT NULL,
  `user_id`     bigint(100) NOT NULL,
  `opt_lock`    int(100),
  PRIMARY KEY (balance_id, user_id, currency_id),
  KEY `fk_uk` (`user_id`),
  CONSTRAINT `fk_balance_currency` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`currency_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

-- test data
INSERT INTO `currency`
VALUES (0, 'EUR'),
       (1, 'USD'),
       (2, 'GBP');

INSERT INTO `user`
VALUES (1),
       (2),
       (3);

INSERT INTO `balance`
VALUES (1, 200, 0, 1, 1),
       (2, 300, 1, 2, 1),
       (3, 10,  2, 3, 1),
       (4, 10,  2, 1, 1);
