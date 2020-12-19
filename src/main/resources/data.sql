-- INSERT INTO users (username, firstname, lastname) VALUES
--  ('lala', 'lala', 'lala'),
-- ('lolo', 'lolo', 'lolo');

INSERT INTO task (id, description, completed)
VALUES (1, 'nothing', 1);

INSERT INTO room (room_name, description)
VALUES ('404', 'nice sits and projector available'), 
('505', 'projector available, but not much space'), 
('606', 'projector not available'),
('707', 'projector available, but not much space'),
('808', 'room is better for parties, not for presentations');

INSERT INTO users (username, password, displaying_name, role)
VALUES ('admin', '$2y$12$VKsi9myOuBidJ5GIEdeMye5IrGK8bOppl36VuqMPABtgazhwHrNCq', 'John Doe', 'ADMIN'),
('presenter', '$2y$12$RVH8J44y0U9xLnj9oAjbl.4QUIhw7S5KaOcyY.lcjp6bj0PU.nWAq', 'Jack Doe', 'PRESENTER'),
('listener', '$2y$12$IdIZVvaSkqj4jqfKGdkU4e5dW8asJDwRq6ENhvsvPZFQpFLhP3Usy', 'Jane Doe', 'LISTENER');

INSERT INTO presentation (title)
VALUES ('nice one'), ('intresting one'), ('awesome one');

INSERT INTO schedule (presentation_id, room_id, presentation_begin, presentation_end)
VALUES (1, 1, '2020-10-10 08:00:00', '2020-10-10 10:00:00');