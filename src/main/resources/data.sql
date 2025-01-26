-- db init for test usage 
-- haslo do wszystkich userow --> 'password'
INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (101, 'giver@example.com', 'giver', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'GIVER');
INSERT INTO Giver (id, first_name, last_name, birth_date) VALUES
    (101, 'John', 'Doe', '1990-01-01');

INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (102, 'ngo@example.com', 'ngo', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'NGO');
INSERT INTO NGO (id, name, krs) VALUES
    (102, 'Helping Hands', '1234567890');

INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (103, 'official@example.com', 'official', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'OFFICIAL');
INSERT INTO Official (id, official_name, regon) VALUES
    (103, 'City Official', '0987654321');

INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (104, 'szymskul@gmail.com', 'volunteer', '123-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'VOLUNTEER');
INSERT INTO Volunteer (id, ready_for_mark, birth_date, organization_id, available, first_name, last_name) VALUES
    (104, true, '1995-05-05', 102, true, 'Jane', 'Smith');

-- Dodatkowy wolantariusz, test raportowania
INSERT INTO app_users (id, email, username, phone, password, role) VALUES
    (105, 'mail2@gmail.com', 'volunteer2', '567-456-789', '$2a$10$3DEvp/rDvtdKXvSiJ7nwVOxOFZHOntXlk9C4fF6QrTJ4k5deUUaLK', 'VOLUNTEER');
INSERT INTO Volunteer (id, ready_for_mark, birth_date, organization_id, available, first_name, last_name) VALUES
    (105, true, '1990-02-03', 102, true, 'NotJane', 'NotSmith');

SELECT * FROM app_users;

--templates
INSERT INTO templates (type, language, title, body)
VALUES ('help1', 'pl', 'Twoje zgłoszenie pomocy zostało przyjęte!', 'Witaj {firstName} {lastName},
Dziękujemy za zgłoszenie pomocy!
Otrzymaliśmy Twoje zgłoszenie, a poniżej znajdują się szczegóły:
Opis: {description}
Unikalny kod zgłoszenia: {uniqueCode}
Status Twojego zgłoszenia: {status}
Pozdrawiamy, Zespół Pomocy Humanitarnej');

INSERT INTO templates (type, language, title, body)
VALUES ('help1', 'en', 'Your help request has been received!', 'Hello {firstName} {lastName},
Thank you for submitting your help request!
We have received your request, and below are the details:
Description: {description}
Unique request code: {uniqueCode}
Status of your request: {status}
Best regards,
The Humanitarian Aid Team');

INSERT INTO templates (type, language, title, body)
VALUES ('help2', 'pl', 'Twoje zgłoszenie pomocy zostało zaktualizowane!', 'Witaj {firstName} {lastName},
Twoje zgłoszenie zostało zaktualizowane poniżej znajdują się szczegóły:
Opis: {description}
Unikalny kod zgłoszenia: {uniqueCode}
Status Twojego zgłoszenia: {status}
Pozdrawiamy, Zespół Pomocy Humanitarnej');

INSERT INTO templates (type, language, title, body)
VALUES ('help2', 'en', 'Your help request has been updated!', 'Hello {firstName} {lastName},
Your help request has been updated. Below are the details:
Description: {description}
Unique request code: {uniqueCode}
Status of your request: {status}
Best regards,
The Humanitarian Aid Team');

INSERT INTO templates (type, language, title, body)
VALUES ('vol1', 'pl', 'Zaproszenie do dołączenia do akcji pomocowej', 'Witaj wolontariuszu!
Zapraszamy cię do dołączenia do akcji pomocowej.
W celu dołączenia do akcji kliknij w ten link - >  {eventLink}');
INSERT INTO templates (type, language, title, body)
VALUES ('vol2', 'en', 'Invitation to Join the Relief Effort', 'Hello Volunteer!
        We invite you to join the relief effort.
        To participate in the event, please click on this link -> {eventLink}');

INSERT INTO catastrophes (type, longitude, latitude, is_active, reported_date)
VALUES
    ('Pożar', 52.2297, 21.0122, TRUE, '2025-01-20 11:30:00');

INSERT INTO help_requests (first_name, last_name, email, email_language, description, status, unique_code, catastrophe_id, reported_date)
VALUES
('Jan', 'Kowalski', 'jan.kowalski@example.com', 'pl', 'Potrzebuję gaśnicy', 0, '11111111-1111-1111-1111-111111111111', 1, '2025-01-20 12:00:00');


INSERT INTO resources (amount, date_of_registration, resource_status, resource_type, destination, holder_id, id_resource, description)
VALUES (1, '20-01-2025', 0, 7, 1, 101, 1, 'Gaśnica');

INSERT INTO resources (amount, date_of_registration, resource_status, resource_type, destination, holder_id, id_resource, description)
VALUES (2, '20-01-2025', 0, 6, 1, 103, 2, 'Samochód');

INSERT INTO resources (amount, date_of_registration, resource_status, resource_type, destination, holder_id, id_resource, description)
VALUES (5, '25-01-2025', 0, 2, null, 102, 3, 'Apteczka pierwszej pomocy');
