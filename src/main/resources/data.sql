INSERT INTO room (room_name, description)
VALUES ('404', 'nice sits and projector available'), 
('505', 'projector available, but not much space'), 
('606', 'projector not available'),
('707', 'projector available, but not much space'),
('808', 'room is better for parties, not for presentations');

INSERT INTO users (username, password, displaying_name, role)
VALUES ('admin', '$2y$12$VKsi9myOuBidJ5GIEdeMye5IrGK8bOppl36VuqMPABtgazhwHrNCq', 'John Doe', 'ADMIN'),
('presenter', '$2y$12$RVH8J44y0U9xLnj9oAjbl.4QUIhw7S5KaOcyY.lcjp6bj0PU.nWAq', 'Jack Doe', 'PRESENTER'),
('presenter2', '$2y$12$W/wwHa9gHBq3K3bigpf/9e2qCXip4cL7zAZVL/R7x2E9AArkefvFm', 'Justin Doe', 'PRESENTER'),
('presenter3', '$2y$12$Ns3Z2gvMlazySBgsjQ91.u7jSdi0J.9WzPZcuPhVC7F.5JUWMbezy', 'Joseph Doe', 'PRESENTER'),
('listener', '$2y$12$IdIZVvaSkqj4jqfKGdkU4e5dW8asJDwRq6ENhvsvPZFQpFLhP3Usy', 'Jane Doe', 'LISTENER');

INSERT INTO presentation (title, creator_username)
VALUES ('nice one', 'presenter'), 
('intresting one', 'presenter2'),
('awesome one','presenter3');

INSERT INTO user_presentation (user_id, presentation_id)
VALUES ('presenter', 2),
('presenter', 3),
('presenter2', 3);

INSERT INTO schedule (presentation_id, room_id, presentation_begin, presentation_end)
VALUES (1, 1, '2020-10-10 08:00:00', '2020-10-10 10:00:00'),
(2, 2, '2020-11-10 08:00:00', '2020-11-10 10:00:00'),
(3, 3, '2020-12-10 08:00:00', '2020-12-10 10:00:00');
