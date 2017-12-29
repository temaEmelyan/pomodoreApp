DROP TABLE pomos
IF EXISTS;
DROP TABLE users
IF EXISTS;
DROP SEQUENCE global_seq
IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ
  AS INTEGER
  START WITH 100000;

CREATE TABLE users
(
  id    INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  email VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON USERS (email);

CREATE TABLE pomos
(
  id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  start   TIMESTAMP NOT NULL,
  finish  TIMESTAMP NOT NULL,
  user_id INTEGER   NOT NULL,
  FOREIGN KEY (user_id) REFERENCES USERS (id)
    ON DELETE CASCADE
);
CREATE UNIQUE INDEX pomos_unique_user_datetime_idx
  ON pomos (user_id, start)