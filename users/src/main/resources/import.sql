INSERT INTO users (username, password, enabled, name, lastname, email) VALUES('txema','$2a$10$mdH4h15k/QHrZZBS7J6j/u17eBQ5/vF3bq6c05bkTvQhGvphGUZFm',true, 'Txema', 'Lanchazo', 'txema@gmail.com');
INSERT INTO users (username, password, enabled, name, lastname, email) VALUES('admin','$2a$10$gVikUgQBK2j7Vyd9YahPle3NJ3l7uCH.efJur9qV3jAftGAfzGvYO',true, 'Pedro', 'Lanzas', 'pedro@gmail.com');

INSERT INTO roles (name) VALUES('ROLE_USER');
INSERT INTO roles (name) VALUES('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);