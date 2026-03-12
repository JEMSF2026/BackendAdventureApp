-- EquipmentState
INSERT INTO equipment_state (id, name) VALUES (1, 'Aktiv');
INSERT INTO equipment_state (id, name) VALUES (2, 'Reparation');
INSERT INTO equipment_state (id, name) VALUES (3, 'Out of Order');

-- CustomerType
INSERT INTO customer_type (id, name) VALUES (1, 'Privat');
INSERT INTO customer_type (id, name) VALUES (2, 'Erhverv');

-- Employee
INSERT INTO employee (id, employee_role, first_name, last_name, phone_number, email)
VALUES (1, 'ADMIN', 'Mads', 'Nielsen', '12345678', 'mads.nielsen@adventure.dk');

INSERT INTO employee (id, employee_role, first_name, last_name, phone_number, email)
VALUES (2, 'ACTIVITY', 'Sara', 'Hansen', '87654321', 'sara.hansen@adventure.dk');

-- Customer
INSERT INTO customer (id, first_name, last_name, company_name, cvr, email, phone_number, customer_type_id)
VALUES (1, 'Lars', 'Christensen', NULL, NULL, 'lars.christensen@gmail.com', '22334455', 1);

INSERT INTO customer (id, first_name, last_name, company_name, cvr, email, phone_number, customer_type_id)
VALUES (2, 'Karin', 'Madsen', 'Madsen A/S', 12345678, 'karin.madsen@madsenas.dk', '33445566', 2);

-- Activity
INSERT INTO activity (id, name, description, price, minimum_age, duration_minutes, max_participants)
VALUES (1, 'Paintball', 'Spændende paintball-kamp i vores skovbane med udstyr inkluderet.', 249.00, 12, 60, 20);

INSERT INTO activity (id, name, description, price, minimum_age, duration_minutes, max_participants)
VALUES (2, 'Go Kart', 'Racerkørsel på vores professionelle go-kart bane indendørs.', 199.00, 10, 30, 12);

INSERT INTO activity (id, name, description, price, minimum_age, duration_minutes, max_participants)
VALUES (3, 'Bueskydning', 'Lær bueskydningens kunst under vejledning af erfarne instruktører.', 179.00, 8, 45, 15);

INSERT INTO activity (id, name, description, price, minimum_age, duration_minutes, max_participants)
VALUES (4, 'Adventure Area', 'Klatring, zipline og forhindringsbane i vores udendørs adventure park.', 299.00, 10, 90, 25);

-- Equipment – Paintball (activity_id = 1)
INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (1, 'Paintball Gevær #1', 'Semi-automatisk paintball markør, kaliber .68', 1, 1);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (2, 'Paintball Maske #2', 'Beskyttelsesmaske med anti-dug linse – sendt til reparation', 2, 1);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (3, 'Paintball Vest #3', 'Beskyttelsesvest størrelse L – defekt spænde', 3, 1);

-- Equipment – Go Kart (activity_id = 2)
INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (4, 'Go Kart #1', 'Elektrisk go-kart, maks 80 kg fører', 1, 2);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (5, 'Go Kart #2', 'Benzindrevet go-kart – bremsekalibrering i gang', 2, 2);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (6, 'Racerhjelm #3', 'Fuld-face hjelm størrelse M – revne i skærm', 3, 2);

-- Equipment – Bueskydning (activity_id = 3)
INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (7, 'Recurve Bue #1', 'Recurve bue 30 lbs, højrehåndet', 1, 3);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (8, 'Armskinner #2', 'Læder armskinner til beskyttelse – strop i reparation', 2, 3);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (9, 'Compound Bue #3', 'Compound bue 50 lbs – kabelstræk defekt', 3, 3);

-- Equipment – Adventure Area (activity_id = 4)
INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (10, 'Klatresele #1', 'Fuldbody klatresele til voksne, CE-certificeret', 1, 4);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (11, 'Hjelm Adventure #2', 'Klatrehjelm størrelse S/M – spændejustering i reparation', 2, 4);

INSERT INTO equipment (id, name, description, equipment_state_id, activity_id)
VALUES (12, 'Zipline Karabinhage #3', 'Dobbelt-lås karabinhage – låsemekanisme ude af drift', 3, 4);

-- Reservation
INSERT INTO reservation (id, booking_number, date_of_reservation, price, customer_id)
VALUES (1, 'BNR-2026-001', '2026-03-01 10:30:00', 498.00, 1);

INSERT INTO reservation (id, booking_number, date_of_reservation, price, customer_id)
VALUES (2, 'BNR-2026-002', '2026-03-05 14:00:00', 598.00, 2);

-- Timeslots – marts 2026
-- Tilgængelige timeslots (ingen reservation)
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (1, '2026-03-03', '2026-03-03 09:00:00', '2026-03-03 10:00:00', 0, 1, NULL, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (2, '2026-03-03', '2026-03-03 11:00:00', '2026-03-03 11:30:00', 0, 2, NULL, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (3, '2026-03-07', '2026-03-07 10:00:00', '2026-03-07 10:45:00', 0, 3, NULL, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (4, '2026-03-10', '2026-03-10 13:00:00', '2026-03-10 14:30:00', 0, 4, NULL, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (5, '2026-03-14', '2026-03-14 09:00:00', '2026-03-14 10:00:00', 0, 1, NULL, 1);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (6, '2026-03-17', '2026-03-17 11:00:00', '2026-03-17 11:30:00', 0, 2, NULL, 1);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (7, '2026-03-21', '2026-03-21 14:00:00', '2026-03-21 14:45:00', 0, 3, NULL, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (8, '2026-03-28', '2026-03-28 10:00:00', '2026-03-28 11:30:00', 0, 4, NULL, 1);

-- Bookede timeslots (med reservation)
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (9, '2026-03-15', '2026-03-15 09:00:00', '2026-03-15 10:00:00', 4, 1, 1, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (10, '2026-03-20', '2026-03-20 13:00:00', '2026-03-20 14:30:00', 6, 4, 2, 1);