-- db init for test usage 

INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (1, 'giver@example.com', 'giver', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'GIVER');
INSERT INTO Giver (id, first_name, last_name, birth_date) VALUES
    (1, 'John', 'Doe', '1990-01-01');

INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (2, 'ngo@example.com', 'ngo', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'NGO');
INSERT INTO NGO (id, name, krs) VALUES
    (2, 'Helping Hands', '1234567890');

INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (3, 'official@example.com', 'official', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'OFFICIAL');
INSERT INTO Official (id, official_name, regon) VALUES
    (3, 'City Official', '0987654321');

INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (4, 'volunteer@example.com', 'volunteer', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'VOLUNTEER');
INSERT INTO Volunteer (id, ready_for_mark, birth_date, organization_id, available, first_name, last_name) VALUES
    (4, true, '1995-05-05', 2, true, 'Jane', 'Smith');

SELECT * FROM app_users;