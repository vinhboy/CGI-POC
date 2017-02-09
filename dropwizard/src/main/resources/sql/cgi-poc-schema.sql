--liquibase formatted SQL

--changeset alucas:1
-- COMMENT :Initializing DATABASE WITH a test USER.
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS users;
SET foreign_key_checks = 1;

CREATE TABLE users (
  id    BIGINT                   NOT NULL AUTO_INCREMENT,
  email VARCHAR(50)              NOT NULL,
  hash  VARCHAR(150)             NOT NULL,
  role  ENUM ('ADMIN', 'NORMAL') NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
)
  AUTO_INCREMENT = 1;

-- ROLLBACK DROP TABLE users;

--changeset valonsejdini:2
-- COMMENT :Creating asset TABLE.

CREATE TABLE assets (
  id          BIGINT        NOT NULL AUTO_INCREMENT,
  url         VARCHAR(1024) NOT NULL,
  description VARCHAR(2048)          DEFAULT NULL,
  user_id     BIGINT        NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id)
)
  AUTO_INCREMENT = 1;

-- ROLLBACK DROP TABLE assets;
