CREATE TABLE role (
  id            BIGINT      NOT NULL AUTO_INCREMENT,
  role_name     VARCHAR(30) NOT NULL,
  last_modified TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY role_name (role_name)
);

CREATE TABLE permission (
  id              BIGINT      NOT NULL AUTO_INCREMENT,
  permission_name VARCHAR(30) NOT NULL,
  last_modified   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY permission_name (permission_name)
);

CREATE TABLE role_permission (
  role_id       BIGINT    NOT NULL,
  permission_id BIGINT    NOT NULL,
  last_modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (role_id, permission_id),
  CONSTRAINT fk_rolepermission_1 FOREIGN KEY (role_id) REFERENCES role (id),
  CONSTRAINT fk_rolepermission_2 FOREIGN KEY (permission_id) REFERENCES permission (id)
);
