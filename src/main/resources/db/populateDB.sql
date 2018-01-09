DELETE FROM pomos;
DELETE FROM users;
ALTER SEQUENCE global_seq
RESTART WITH 100000;

INSERT INTO users (email, password) VALUES
  ('user1@yandex.ru', 'password1'),
  ('user2@gmail.com', 'password1');

INSERT INTO POMOS (DURATION, FINISH, user_id) VALUES
  ('600', '2015-05-30 9:25:00', 100000),
  ('600', '2015-05-30 10:55:00', 100000),
  ('600', '2015-05-30 11:25:00', 100000),
  ('600', '2015-05-30 12:55:00', 100000),
  ('600', '2015-05-30 09:00:00', 100001),
  ('600', '2015-05-30 10:55:00', 100001),
  ('600', '2015-05-30 12:25:00', 100001),
  ('600', '2015-05-30 13:25:00', 100001),
  ('600', '2015-05-30 14:25:00', 100001);
