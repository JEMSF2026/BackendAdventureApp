-- EquipmentState
INSERT INTO equipment_state (id, name) VALUES (1, 'Aktiv');
INSERT INTO equipment_state (id, name) VALUES (2, 'Reparation');
INSERT INTO equipment_state (id, name) VALUES (3, 'Out of Order');

--EmployeeRole
INSERT INTO employee_type (id, name) values (1,'ADMIN')
INSERT INTO employee_type (id, name) values (2, 'ACTIVITY')

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

-- Reset sequences so auto-generated IDs don't conflict with seed data
ALTER TABLE equipment_state ALTER COLUMN id RESTART WITH 4;
ALTER TABLE customer_type ALTER COLUMN id RESTART WITH 3;
ALTER TABLE employee ALTER COLUMN id RESTART WITH 3;
ALTER TABLE customer ALTER COLUMN id RESTART WITH 3;
ALTER TABLE activity ALTER COLUMN id RESTART WITH 5;
ALTER TABLE equipment ALTER COLUMN id RESTART WITH 13;

-- Reservation
INSERT INTO reservation (id, booking_number, date_of_reservation, price, customer_id)
VALUES (1, 'BNR-2026-001', '2026-03-01 10:30:00', 498.00, 1);

INSERT INTO reservation (id, booking_number, date_of_reservation, price, customer_id)
VALUES (2, 'BNR-2026-002', '2026-03-05 14:00:00', 598.00, 2);

ALTER TABLE reservation ALTER COLUMN id RESTART WITH 3;

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

-- Paintball – 3 timeslots pr. dag (activity_id = 1, duration 60 min)
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (11, '2026-03-19', '2026-03-19 09:00:00', '2026-03-19 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (12, '2026-03-19', '2026-03-19 11:00:00', '2026-03-19 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (13, '2026-03-19', '2026-03-19 13:00:00', '2026-03-19 14:00:00', 0, 1, NULL, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (14, '2026-03-23', '2026-03-23 09:00:00', '2026-03-23 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (15, '2026-03-23', '2026-03-23 11:00:00', '2026-03-23 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (16, '2026-03-23', '2026-03-23 13:00:00', '2026-03-23 14:00:00', 0, 1, NULL, 2);

INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (17, '2026-03-26', '2026-03-26 09:00:00', '2026-03-26 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (18, '2026-03-26', '2026-03-26 11:00:00', '2026-03-26 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (19, '2026-03-26', '2026-03-26 13:00:00', '2026-03-26 14:00:00', 0, 1, NULL, 2);

-- Paintball – 3 timeslots for alle dage i marts (activity_id = 1, duration 60 min)
-- Dage med eksisterende 09:00 slot (3, 14, 15) får kun 11:00 og 13:00 tilføjet

-- 1. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (20, '2026-03-01', '2026-03-01 09:00:00', '2026-03-01 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (21, '2026-03-01', '2026-03-01 11:00:00', '2026-03-01 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (22, '2026-03-01', '2026-03-01 13:00:00', '2026-03-01 14:00:00', 0, 1, NULL, 2);

-- 2. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (23, '2026-03-02', '2026-03-02 09:00:00', '2026-03-02 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (24, '2026-03-02', '2026-03-02 11:00:00', '2026-03-02 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (25, '2026-03-02', '2026-03-02 13:00:00', '2026-03-02 14:00:00', 0, 1, NULL, 2);

-- 3. marts (har allerede 09:00, tilføjer 11:00 og 13:00)
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (26, '2026-03-03', '2026-03-03 11:00:00', '2026-03-03 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (27, '2026-03-03', '2026-03-03 13:00:00', '2026-03-03 14:00:00', 0, 1, NULL, 2);

-- 4. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (28, '2026-03-04', '2026-03-04 09:00:00', '2026-03-04 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (29, '2026-03-04', '2026-03-04 11:00:00', '2026-03-04 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (30, '2026-03-04', '2026-03-04 13:00:00', '2026-03-04 14:00:00', 0, 1, NULL, 2);

-- 5. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (31, '2026-03-05', '2026-03-05 09:00:00', '2026-03-05 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (32, '2026-03-05', '2026-03-05 11:00:00', '2026-03-05 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (33, '2026-03-05', '2026-03-05 13:00:00', '2026-03-05 14:00:00', 0, 1, NULL, 2);

-- 6. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (34, '2026-03-06', '2026-03-06 09:00:00', '2026-03-06 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (35, '2026-03-06', '2026-03-06 11:00:00', '2026-03-06 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (36, '2026-03-06', '2026-03-06 13:00:00', '2026-03-06 14:00:00', 0, 1, NULL, 2);

-- 7. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (37, '2026-03-07', '2026-03-07 09:00:00', '2026-03-07 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (38, '2026-03-07', '2026-03-07 11:00:00', '2026-03-07 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (39, '2026-03-07', '2026-03-07 13:00:00', '2026-03-07 14:00:00', 0, 1, NULL, 2);

-- 8. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (40, '2026-03-08', '2026-03-08 09:00:00', '2026-03-08 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (41, '2026-03-08', '2026-03-08 11:00:00', '2026-03-08 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (42, '2026-03-08', '2026-03-08 13:00:00', '2026-03-08 14:00:00', 0, 1, NULL, 2);

-- 9. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (43, '2026-03-09', '2026-03-09 09:00:00', '2026-03-09 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (44, '2026-03-09', '2026-03-09 11:00:00', '2026-03-09 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (45, '2026-03-09', '2026-03-09 13:00:00', '2026-03-09 14:00:00', 0, 1, NULL, 2);

-- 10. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (46, '2026-03-10', '2026-03-10 09:00:00', '2026-03-10 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (47, '2026-03-10', '2026-03-10 11:00:00', '2026-03-10 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (48, '2026-03-10', '2026-03-10 13:00:00', '2026-03-10 14:00:00', 0, 1, NULL, 2);

-- 11. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (49, '2026-03-11', '2026-03-11 09:00:00', '2026-03-11 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (50, '2026-03-11', '2026-03-11 11:00:00', '2026-03-11 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (51, '2026-03-11', '2026-03-11 13:00:00', '2026-03-11 14:00:00', 0, 1, NULL, 2);

-- 12. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (52, '2026-03-12', '2026-03-12 09:00:00', '2026-03-12 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (53, '2026-03-12', '2026-03-12 11:00:00', '2026-03-12 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (54, '2026-03-12', '2026-03-12 13:00:00', '2026-03-12 14:00:00', 0, 1, NULL, 2);

-- 13. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (55, '2026-03-13', '2026-03-13 09:00:00', '2026-03-13 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (56, '2026-03-13', '2026-03-13 11:00:00', '2026-03-13 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (57, '2026-03-13', '2026-03-13 13:00:00', '2026-03-13 14:00:00', 0, 1, NULL, 2);

-- 14. marts (har allerede 09:00, tilføjer 11:00 og 13:00)
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (58, '2026-03-14', '2026-03-14 11:00:00', '2026-03-14 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (59, '2026-03-14', '2026-03-14 13:00:00', '2026-03-14 14:00:00', 0, 1, NULL, 2);

-- 15. marts (har allerede 09:00 booket, tilføjer 11:00 og 13:00)
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (60, '2026-03-15', '2026-03-15 11:00:00', '2026-03-15 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (61, '2026-03-15', '2026-03-15 13:00:00', '2026-03-15 14:00:00', 0, 1, NULL, 2);

-- 16. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (62, '2026-03-16', '2026-03-16 09:00:00', '2026-03-16 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (63, '2026-03-16', '2026-03-16 11:00:00', '2026-03-16 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (64, '2026-03-16', '2026-03-16 13:00:00', '2026-03-16 14:00:00', 0, 1, NULL, 2);

-- 17. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (65, '2026-03-17', '2026-03-17 09:00:00', '2026-03-17 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (66, '2026-03-17', '2026-03-17 11:00:00', '2026-03-17 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (67, '2026-03-17', '2026-03-17 13:00:00', '2026-03-17 14:00:00', 0, 1, NULL, 2);

-- 18. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (68, '2026-03-18', '2026-03-18 09:00:00', '2026-03-18 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (69, '2026-03-18', '2026-03-18 11:00:00', '2026-03-18 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (70, '2026-03-18', '2026-03-18 13:00:00', '2026-03-18 14:00:00', 0, 1, NULL, 2);

-- 20. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (71, '2026-03-20', '2026-03-20 09:00:00', '2026-03-20 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (72, '2026-03-20', '2026-03-20 11:00:00', '2026-03-20 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (73, '2026-03-20', '2026-03-20 13:00:00', '2026-03-20 14:00:00', 0, 1, NULL, 2);

-- 21. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (74, '2026-03-21', '2026-03-21 09:00:00', '2026-03-21 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (75, '2026-03-21', '2026-03-21 11:00:00', '2026-03-21 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (76, '2026-03-21', '2026-03-21 13:00:00', '2026-03-21 14:00:00', 0, 1, NULL, 2);

-- 22. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (77, '2026-03-22', '2026-03-22 09:00:00', '2026-03-22 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (78, '2026-03-22', '2026-03-22 11:00:00', '2026-03-22 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (79, '2026-03-22', '2026-03-22 13:00:00', '2026-03-22 14:00:00', 0, 1, NULL, 2);

-- 24. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (80, '2026-03-24', '2026-03-24 09:00:00', '2026-03-24 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (81, '2026-03-24', '2026-03-24 11:00:00', '2026-03-24 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (82, '2026-03-24', '2026-03-24 13:00:00', '2026-03-24 14:00:00', 0, 1, NULL, 2);

-- 25. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (83, '2026-03-25', '2026-03-25 09:00:00', '2026-03-25 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (84, '2026-03-25', '2026-03-25 11:00:00', '2026-03-25 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (85, '2026-03-25', '2026-03-25 13:00:00', '2026-03-25 14:00:00', 0, 1, NULL, 2);

-- 27. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (86, '2026-03-27', '2026-03-27 09:00:00', '2026-03-27 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (87, '2026-03-27', '2026-03-27 11:00:00', '2026-03-27 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (88, '2026-03-27', '2026-03-27 13:00:00', '2026-03-27 14:00:00', 0, 1, NULL, 2);

-- 28. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (89, '2026-03-28', '2026-03-28 09:00:00', '2026-03-28 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (90, '2026-03-28', '2026-03-28 11:00:00', '2026-03-28 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (91, '2026-03-28', '2026-03-28 13:00:00', '2026-03-28 14:00:00', 0, 1, NULL, 2);

-- 29. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (92, '2026-03-29', '2026-03-29 09:00:00', '2026-03-29 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (93, '2026-03-29', '2026-03-29 11:00:00', '2026-03-29 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (94, '2026-03-29', '2026-03-29 13:00:00', '2026-03-29 14:00:00', 0, 1, NULL, 2);

-- 30. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (95, '2026-03-30', '2026-03-30 09:00:00', '2026-03-30 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (96, '2026-03-30', '2026-03-30 11:00:00', '2026-03-30 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (97, '2026-03-30', '2026-03-30 13:00:00', '2026-03-30 14:00:00', 0, 1, NULL, 2);

-- 31. marts
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (98, '2026-03-31', '2026-03-31 09:00:00', '2026-03-31 10:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (99, '2026-03-31', '2026-03-31 11:00:00', '2026-03-31 12:00:00', 0, 1, NULL, 2);
INSERT INTO timeslot (id, day_of_activity, start_time, end_time, participants, activity_id, reservation_id, employee_id)
VALUES (100, '2026-03-31', '2026-03-31 13:00:00', '2026-03-31 14:00:00', 0, 1, NULL, 2);

ALTER TABLE timeslot ALTER COLUMN id RESTART WITH 101;