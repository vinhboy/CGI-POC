--liquibase formatted SQL

--changeset valonsejdini:1
-- COMMENT : Loading tables with test model.

/* insert ADMIN user with password adminpw */
INSERT INTO users (id, email, hash, role) VALUES (1, 'admin@cgi.com',
                                              '518bd5283161f69a6278981ad00f4b09a2603085f145426ba8800c:8bd85a69ed2cb94f4b9694d67e3009909467769c56094fc0fce5af',
                                              'ADMIN');
/* insert NORMAL user with password adminpw */
INSERT INTO users (id, email, hash, role) VALUES (2, 'normal1@cgi.com','5b4f8788e3d7abbe847db2ac651aec742ecd142b6692e850b9bab3:2a7046a7af334f4e26e033a4b178b4a39ff591a22e19307ff08259','NORMAL');
INSERT INTO users (id, email, hash, role) VALUES (3, 'normal2@cgi.com','5b4f8788e3d7abbe847db2ac651aec742ecd142b6692e850b9bab3:2a7046a7af334f4e26e033a4b178b4a39ff591a22e19307ff08259','NORMAL');

/* insert asset model */
INSERT INTO assets (id, url, description, user_id) VALUES (1, "https://github.com/", "Test asset1", 2);
INSERT INTO assets (id, url, description, user_id) VALUES (2, "https://google.com/", "Test asset2", 2);
INSERT INTO assets (id, url, description, user_id) VALUES (3, "https://linkedin.com/", "Test asset3", 2);
INSERT INTO assets (id, url, description, user_id) VALUES (4, "https://facebook.com/", "Test asset4", 3);

