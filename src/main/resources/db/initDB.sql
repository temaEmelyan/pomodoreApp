DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS pomos;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq
  START 100000;

CREATE TABLE users
(
  id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  email    VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON users (email);

CREATE TABLE pomos (
  id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  duration INTEGER   NOT NULL,
  finish   TIMESTAMP NOT NULL,
  user_id  INTEGER   NOT NULL,
  FOREIGN KEY (user_id) REFERENCES USERS (id)
  ON DELETE CASCADE
);
CREATE UNIQUE INDEX pomos_unique_user_datetime_idx
  ON pomos (user_id, finish)