INSERT INTO bootdb.user_entity (id, email, password, username) VALUES ('0fef539d-69be-4013-9380-6a12c3534c67', 'lily@amos.de', 'nvajsdvh''', 'Lily');
INSERT INTO bootdb.user_entity (id, email, password, username) VALUES ('2375e026-d348-4fb6-b42b-891a76758d5d', 'ines@amos.de', 'abce''', 'Ines');
INSERT INTO bootdb.user_entity (id, email, password, username) VALUES ('65119d5f-039e-404e-973e-f12c35fe9fef', 'maxr@amos.de', '1545646''', 'Max R.');
INSERT INTO bootdb.user_entity (id, email, password, username) VALUES ('74c31a72-7c9e-4095-a1d6-ec208b57ff1a', 'alex@amos.de', '16545''', 'Alex');
INSERT INTO bootdb.user_entity (id, email, password, username) VALUES ('8299a81a-8162-4739-8b98-5bc41cf5f44c', 'andreas@amos.de', 'nvkadvh''', 'Andreas');
INSERT INTO bootdb.user_entity (id, email, password, username) VALUES ('e8406f08-a78d-4531-baa5-c00c70caabbe', 'tobias@amos.de', 'vnkajdvbk''', 'Tobias');
INSERT INTO bootdb.user_entity (id, email, password, username) VALUES ('eb1dd60e-62ba-4187-8471-ff547183495e', 'maxb@amos.de', '1654154''', 'Max B.');
INSERT INTO bootdb.product_area_entity (id, category, name) VALUES (1, 'Privat', 'Kredit');
INSERT INTO bootdb.product_area_entity (id, category, name) VALUES (2, 'Business', 'Kredit');
INSERT INTO bootdb.product_area_entity (id, category, name) VALUES (3, 'Privat', 'Kunde');
INSERT INTO bootdb.product_area_entity (id, category, name) VALUES (4, 'Business', 'Kunde');
INSERT INTO bootdb.product_area_entity (id, category, name) VALUES (5, 'Privat', 'Payment');
INSERT INTO bootdb.product_area_entity (id, category, name) VALUES (6, 'Business', 'Payment');
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (1, null, 'Frequenz', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (2, null, 'Produktabschlüsse', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (3, null, 'Durchschnittliche Losgröße', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (4, null, 'Kredivolumen', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (5, null, 'Marge', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (6, 'Aufwandsschätzung', 'Sachaufwand', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (7, 'Aufwandsschätzung', 'Personalaufwand', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (8, 'Aufwandsschätzung', 'IT-Aufwand', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (9, null, 'Overall Economic Rating', 0);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (10, 'Kundenstruktur', 'Wie viele unterschiedliche Produktschlüssel existieren für das Produkt Dispokredit?', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (11, 'Kundenstruktur', 'Welche Kunden (Einzelkunden/Gemeinschaftskunden) nehmen diese Produktvariante in Anspruch?', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (12, 'Kreditrating', 'Frage für Kreditrating', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (13, 'Kreditrating', 'Frage für Kreditrating', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (14, 'Zahlungsbedingungen', 'Frage für Zahlungsbedingungen', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (15, 'Zahlungsbedingungen', 'Frage für Zahlungsbedingungen', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (16, 'Sicherheiten', 'Frage für Sicherheiten', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (17, 'Sicherheiten', 'Frage für Sicherheiten', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (18, 'Regulatorik', 'Frage für Regulatorik', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (19, 'Regulatorik', 'Frage für Regulatorik', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (20, 'Dokumentation', 'Frage für Dokumentation', 1);
INSERT INTO bootdb.rating_entity (id, category, criterion, ratingarea) VALUES (21, 'Dokumentation', 'Frage für Dokumentation', 1);
INSERT INTO bootdb.project_entity (id, creator, name) VALUES (100, '0fef539d-69be-4013-9380-6a12c3534c67', 'DKB');
INSERT INTO bootdb.project_entity (id, creator, name) VALUES (200, '2375e026-d348-4fb6-b42b-891a76758d5d', 'Commerzbank');
INSERT INTO bootdb.project_entity (id, creator, name) VALUES (300, '65119d5f-039e-404e-973e-f12c35fe9fef', 'Deutsche Bank');
INSERT INTO bootdb.project_entity (id, creator, name) VALUES (400, '74c31a72-7c9e-4095-a1d6-ec208b57ff1a', 'Postbank');
INSERT INTO bootdb.project_entity (id, creator, name) VALUES (500, '8299a81a-8162-4739-8b98-5bc41cf5f44c', 'Bank 5');
INSERT INTO bootdb.project_entity (id, creator, name) VALUES (600, 'e8406f08-a78d-4531-baa5-c00c70caabbe', 'Bank 6');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (2, 100, '0fef539d-69be-4013-9380-6a12c3534c67');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (0, 100, '74c31a72-7c9e-4095-a1d6-ec208b57ff1a');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (1, 100, '8299a81a-8162-4739-8b98-5bc41cf5f44c');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (1, 200, '0fef539d-69be-4013-9380-6a12c3534c67');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (0, 200, '74c31a72-7c9e-4095-a1d6-ec208b57ff1a');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (0, 300, '0fef539d-69be-4013-9380-6a12c3534c67');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (2, 300, '2375e026-d348-4fb6-b42b-891a76758d5d');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (1, 300, '74c31a72-7c9e-4095-a1d6-ec208b57ff1a');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (2, 300, 'eb1dd60e-62ba-4187-8471-ff547183495e');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (0, 400, '8299a81a-8162-4739-8b98-5bc41cf5f44c');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (1, 400, 'eb1dd60e-62ba-4187-8471-ff547183495e');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (2, 500, '65119d5f-039e-404e-973e-f12c35fe9fef');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (1, 500, 'e8406f08-a78d-4531-baa5-c00c70caabbe');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (0, 600, '65119d5f-039e-404e-973e-f12c35fe9fef');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (1, 600, '74c31a72-7c9e-4095-a1d6-ec208b57ff1a');
INSERT INTO bootdb.project_user_entity (role, project, user) VALUES (2, 600, 'e8406f08-a78d-4531-baa5-c00c70caabbe');
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (100, 'some comment', 'Sichteinlagen', null, 1, 100);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (101, 'some comment', 'Termingeld, Festgeld', 100, 1, 100);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (102, null, 'Spareinlagen', 100, 1, 100);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (103, 'some comment', 'Sparbriefe', null, 2, 200);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (104, null, 'Pfandbriefe', null, 1, 300);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (105, 'some comment', 'Zertifikate', null, 1, 300);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (106, 'some comment', 'Bausparverträge', null, 2, 400);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (107, null, 'Swaps', null, 2, 400);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (108, 'some comment', 'Optionen', null, 1, 200);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (109, 'some comment', 'Versicherungen mit Anlagencharakter', null, 1, 400);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (110, null, 'DUMMY', null, 1, 100);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (111, null, 'DUMMY', null, 2, 100);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (112, null, 'DUMMY', null, 3, 200);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (113, null, 'DUMMY', null, 4, 200);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (114, null, 'DUMMY', null, 5, 200);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (115, null, 'DUMMY', null, 6, 300);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (116, null, 'DUMMY', null, 1, 400);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (117, null, 'DUMMY', null, 2, 400);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (118, null, 'DUMMY', null, 3, 500);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (119, null, 'DUMMY', null, 4, 500);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (120, null, 'DUMMY', null, 5, 500);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (121, null, 'DUMMY', null, 6, 500);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (122, null, 'DUMMY', null, 1, 600);
INSERT INTO bootdb.product_entity (id, comment, name, parent_product_id, productarea, project) VALUES (123, null, 'DUMMY', null, 2, 600);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 1', 'comment 1', 0, 100, 1);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 2', 'comment 2', 1, 100, 2);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 3', 'comment 3', 2, 100, 3);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 4', 'comment 4', 2, 100, 4);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 5', 'comment 5', 1, 100, 5);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 6', 'comment 6', 0, 100, 6);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 7', 'comment 7', 1, 101, 1);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 8', 'comment 8', 2, 101, 2);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 9', 'comment 9', 1, 101, 3);
INSERT INTO bootdb.product_rating_entity (answer, comment, score, product, rating) VALUES ('answer 10', 'comment 10', 2, 101, 4);
