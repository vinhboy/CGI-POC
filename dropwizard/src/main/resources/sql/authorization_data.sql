-- API Roles
INSERT INTO role (id, role_name) VALUES (1, 'Guest');
INSERT INTO role (id, role_name) VALUES (2, 'Customer');
INSERT INTO role (id, role_name) VALUES (3, 'Administrator');

-- API Permissions
INSERT INTO permission (id, permission_name) VALUES (1, 'create');
INSERT INTO permission (id, permission_name) VALUES (2, 'read');
INSERT INTO permission (id, permission_name) VALUES (3, 'update');
INSERT INTO permission (id, permission_name) VALUES (4, 'delete');

-- API Role to Permissions
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 3);
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 3);
INSERT INTO role_permission (role_id, permission_id) VALUES (3, 4);
