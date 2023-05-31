INSERT INTO CLASS (id, name)
VALUES
  (1, 'MATH'),
  (2, 'IT');

INSERT INTO student (id, first_name, last_name, class_id)
VALUES
  (1, 'John', 'Doe', 1),
  (2, 'Jane', 'Smith', 1),
  (3, 'Mike', 'Johnson', 2);


INSERT INTO teacher (id, first_name, last_name, class_id)
VALUES
  (1, 'teacher', 'one', 1),
  (2, 'teacher1', 'two', 2);

  INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
