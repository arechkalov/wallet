DROP SCHEMA IF EXISTS bet_pawa;
CREATE SCHEMA IF NOT EXISTS bet_pawa;

USE `bet_pawa`;
DROP TABLE IF EXISTS `balance`;
DROP TABLE IF EXISTS `currency`;

CREATE TABLE `currency`
(
  `currency_id`   bigint(100) NOT NULL,
  `currency_value` varchar(3)  NOT NULL,
  PRIMARY KEY (`currency_id`),
  UNIQUE KEY `currency_name_UNIQUE` (`currency_value`)
);

CREATE TABLE `balance`
(
  `balance_id`  bigint(100)   NOT NULL,
  `amount`      decimal(4, 0) NOT NULL,
  `currency_id` bigint(100)   NOT NULL,
  `user_id`     bigint(100) NOT NULL,
  `opt_lock`    int(100) DEFAULT 0,
  PRIMARY KEY (balance_id, user_id, currency_id),
  CONSTRAINT `fk_balance_currency` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`currency_id`) ON UPDATE CASCADE
);

CREATE INDEX user_idx ON balance (user_id) USING BTREE;
CREATE INDEX cur_idx ON balance (currency_id) USING BTREE;

-- test data
INSERT INTO `currency`
VALUES (0, 'EUR'),
       (1, 'USD'),
       (2, 'GBP');
