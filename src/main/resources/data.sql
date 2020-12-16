-- INSERT INTO users (username, firstname, lastname) VALUES
--  ('lala', 'lala', 'lala'),
-- ('lolo', 'lolo', 'lolo');

INSERT INTO task (id, description, completed)
VALUES (1, 'nothing', 1);

INSERT INTO room (room_name)
VALUES ('404'), ('505'), ('606');

INSERT INTO users (username, password, displaying_name, role)
VALUES ('admin', 'admin', 'John Doe', 'ADMIN'),
('presenter', 'presenter', 'Jack Doe', 'PRESENTER'),
('listener', 'listener', 'Jane Doe', 'LISTENER');

INSERT INTO presentation (title)
VALUES ('nice one'), ('intresting one'), ('awesome one');

INSERT INTO schedule (presentation_id, room_id, presentation_begin, presentation_end)
VALUES (1, 1, '2020-10-10 08:00:00', '2020-10-10 10:00:00');