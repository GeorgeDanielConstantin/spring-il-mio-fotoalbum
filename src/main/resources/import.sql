INSERT INTO roles (id, name) VALUES(1,'ADMIN');
INSERT INTO roles (id, name) VALUES(2,'USER');
INSERT INTO users (id, email, first_name, last_name, password) VALUES(1, 'admin@email.com', 'Daniel', 'Constantin', '{noop}admin');
INSERT INTO users (id, email, first_name, last_name, password) VALUES(2, 'user@email.com', 'George', 'Constantin', '{noop}user');
INSERT INTO users_roles (roles_id, user_id) VALUES(1, 1);
INSERT INTO users_roles (roles_id, user_id) VALUES(2, 2);

INSERT INTO `categories` (`name`) VALUES ('paesaggio')

INSERT INTO `photos` (`title`, `description`, `url`, `visibility`) VALUES ('Strapiombo', 'Foto fatta su uno strapiombo', 'https://picsum.photos/200', true)