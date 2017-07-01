CREATE SCHEMA test DEFAULT CHARACTER SET utf8;

CREATE TABLE test.users (
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  USER_NAME VARCHAR(25) NOT NULL,
  USER_AGE INT NOT NULL,
  IS_ADMIN BIT(1) NOT NULL DEFAULT false,
  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

