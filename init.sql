
CREATE TYPE ENUM_TYPE_VOL AS ENUM ('Indéfini','Economique','Premium economique','Affaires','Premiere','Charter','Low cost','Cargo','Privatif','Autre');
CREATE TYPE ENUM_STATUT_VOL AS ENUM ('Indéfini','Prevu','Enregistrement ouvert','En cours d’embarquement','Decolle','En vol','Atterri','Retarde','Annule','Detourne','Reprogramme','Finalise','En attente','Non operationnel','Autre');
CREATE TYPE ENUM_STATUT_RESERVATION AS ENUM ('Indéfini','Confirmée','Annulée','En attente','Autre');
CREATE TYPE ENUM_TYPE_PAIEMENT AS ENUM ('Indéfini','Carte bancaire','Virement','Cheque','Especes','A credit','Bon cadeau','Autre');


CREATE TABLE CONSEILLERS (
  pk_id_conseiller SERIAL PRIMARY KEY,
  nom_conseiller varchar(50) NOT NULL,
  prenom_conseiller varchar(50) NOT NULL,
  email_conseiller varchar(50) NOT NULL,
  numero_telephone_conseiller varchar(50)
) ;

CREATE TABLE CLIENTS (
  pk_id_client SERIAL PRIMARY KEY,
  nom_client varchar(50) NOT NULL,
  prenom_client varchar(50) NOT NULL,
  email_client varchar(50) NOT NULL,
  numero_telephone_client varchar(50),
  adresse_client varChar(50)
) ;

CREATE TABLE VOLS (
  pk_id_vol SERIAL PRIMARY KEY,
  dh_depart timestamp(0),
  lieu_depart varchar(50),
  dh_arrivee timestamp(0),
  lieu_arrivee varchar(50),
  type_vol ENUM_TYPE_VOL,
  compagnie varchar(50)
);


CREATE TABLE RESERVATIONS (
  pk_id_reservation SERIAL PRIMARY KEY,
  id_client INTEGER REFERENCES CLIENTS,
  id_conseiller INTEGER REFERENCES CONSEILLERS,
  id_vol INTEGER REFERENCES VOLS,
  statut ENUM_STATUT_RESERVATION,
  date_reservation timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dh_paiement timestamp(0),
  type_paiement ENUM_TYPE_PAIEMENT,
  nom_reservateur varchar(50),
  prenom_reservateur varchar(50),
  email_reservateur varchar(50),
  numero_telephone_reservateur varchar(50)
) ;

-- Création des conseillers
-- Création des conseillers
INSERT INTO CONSEILLERS (nom_conseiller, prenom_conseiller, email_conseiller, numero_telephone_conseiller) VALUES
('Dupont', 'Jean', 'jean.dupont@agence.com', '0601010101'),
('Martin', 'Sophie', 'sophie.martin@agence.com', '0602020202'),
('Durand', 'Paul', 'paul.durand@agence.com', '0603030303'),
('Blanc', 'Julie', 'julie.blanc@agence.com', '0604040404'),
('Morel', 'Antoine', 'antoine.morel@agence.com', '0605050505');

-- Création des clients
INSERT INTO CLIENTS (nom_client, prenom_client, email_client, numero_telephone_client, adresse_client) VALUES
('Dubois', 'Alice', 'alice.dubois@exemple.com', '0610101010', 'Paris'),
('Lemoine', 'Bernard', 'bernard.lemoine@exemple.com', '0620202020', 'Lyon'),
('Petit', 'Claire', 'claire.petit@exemple.com', '0630303030', 'Marseille'),
('Roux', 'David', 'david.roux@exemple.com', '0640404040', 'Toulouse'),
('Moreau', 'Emma', 'emma.moreau@exemple.com', '0650505050', 'Nice'),
('Giraud', 'Lucas', 'lucas.giraud@exemple.com', '0660606060', 'Bordeaux'),
('Perrin', 'Sophie', 'sophie.perrin@exemple.com', '0670707070', 'Nantes'),
('Fernandez', 'Hugo', 'hugo.fernandez@exemple.com', '0680808080', 'Strasbourg'),
('Chevalier', 'Marie', 'marie.chevalier@exemple.com', '0690909090', 'Rennes'),
('Dupuy', 'Thomas', 'thomas.dupuy@exemple.com', '0611111111', 'Lille'),
('Vincent', 'Louise', 'louise.vincent@exemple.com', '0621212121', 'Dijon'),
('Bertrand', 'Hélène', 'helene.bertrand@exemple.com', '0631313131', 'Angers'),
('Simon', 'Patrick', 'patrick.simon@exemple.com', '0641414141', 'Grenoble'),
('Lambert', 'Isabelle', 'isabelle.lambert@exemple.com', '0651515151', 'Clermont-Ferrand'),
('Leroy', 'Philippe', 'philippe.leroy@exemple.com', '0661616161', 'Limoges'),
('Girard', 'Nathalie', 'nathalie.girard@exemple.com', '0671717171', 'Tours'),
('Colin', 'Cédric', 'cedric.colin@exemple.com', '0681818181', 'Le Havre'),
('Benoit', 'Laetitia', 'laetitia.benoit@exemple.com', '0691919191', 'Reims'),
('Robin', 'Chloé', 'chloe.robin@exemple.com', '0612121212', 'Orléans'),
('Marchand', 'Julien', 'julien.marchand@exemple.com', '0622222222', 'Metz'),
('Meyer', 'Camille', 'camille.meyer@exemple.com', '0622323232', 'Nancy'),
('Noel', 'Adrien', 'adrien.noel@exemple.com', '0622424242', 'Perpignan'),
('Renaud', 'Lucie', 'lucie.renaud@exemple.com', '0622525252', 'Caen'),
('Charpentier', 'Sylvain', 'sylvain.charpentier@exemple.com', '0622626262', 'Avignon'),
('Leclerc', 'Anna', 'anna.leclerc@exemple.com', '0622727272', 'Bayonne'),
('Garcia', 'Pauline', 'pauline.garcia@exemple.com', '0622828282', 'Poitiers'),
('Martinez', 'Olivier', 'olivier.martinez@exemple.com', '0622929292', 'Mulhouse'),
('Lopez', 'Manon', 'manon.lopez@exemple.com', '0623030303', 'Brest');

-- Création des vols
INSERT INTO VOLS (dh_depart, lieu_depart, dh_arrivee, lieu_arrivee, type_vol, compagnie) VALUES
('2025-01-15 10:00:00', 'Paris', '2025-01-15 14:00:00', 'Palma de Majorque', 'Economique', 'Air France'),
('2025-01-16 14:00:00', 'Lyon', '2025-01-16 17:00:00', 'Santorini', 'Premium economique', 'Air France'),
('2025-01-17 18:00:00', 'Marseille', '2025-01-17 21:30:00', 'Marrakech', 'Affaires', 'Air France'),
('2025-01-18 08:00:00', 'Toulouse', '2025-01-18 12:30:00', 'Tenerife', 'Economique', 'EasyJet'),
('2025-01-19 09:00:00', 'Nice', '2025-01-19 13:30:00', 'Lisbonne', 'Premiere', 'Air France'),
('2025-01-20 11:00:00', 'Bordeaux', '2025-01-20 15:00:00', 'Barcelone', 'Low cost', 'Ryanair'),
('2025-01-21 15:00:00', 'Strasbourg', '2025-01-21 19:30:00', 'Rome', 'Economique', 'Air France'),
('2025-01-22 07:00:00', 'Lille', '2025-01-22 10:30:00', 'Porto', 'Charter', 'Transavia'),
('2025-01-23 12:00:00', 'Paris', '2025-01-23 16:00:00', 'Malaga', 'Affaires', 'Air France'),
('2025-01-24 16:00:00', 'Nantes', '2025-01-24 19:30:00', 'Ibiza', 'Economique', 'EasyJet'),
('2025-01-25 08:00:00', 'Dijon', '2025-01-25 12:00:00', 'Athènes', 'Economique', 'Air France'),
('2025-02-11 09:00:00', 'Angers', '2025-02-11 12:30:00', 'Berlin', 'Economique', 'Lufthansa'),
('2025-01-26 14:30:00', 'Grenoble', '2025-01-26 18:30:00', 'Cagliari (Sardaigne)', 'Premium economique', 'Air France'),
('2025-01-27 09:00:00', 'Clermont-Ferrand', '2025-01-27 13:30:00', 'Palermo (Sicile)', 'Affaires', 'Air France'),
('2025-01-28 11:00:00', 'Limoges', '2025-01-28 15:30:00', 'Tunis', 'Low cost', 'Ryanair'),
('2025-01-29 07:00:00', 'Tours', '2025-01-29 11:30:00', 'Santorini', 'Economique', 'Air France'),
('2025-01-30 10:00:00', 'Le Havre', '2025-01-30 14:30:00', 'Marrakech', 'Charter', 'Transavia'),
('2025-01-31 15:30:00', 'Reims', '2025-01-31 20:00:00', 'Cancún', 'Affaires', 'Air France'),
('2025-02-01 09:30:00', 'Orléans', '2025-02-01 13:00:00', 'Lisbonne', 'Economique', 'EasyJet'),
('2025-02-02 12:00:00', 'Metz', '2025-02-02 16:00:00', 'Barcelone', 'Premium economique', 'Air France'),
('2025-02-03 13:00:00', 'Nancy', '2025-02-03 17:30:00', 'Malé (Maldives)', 'Affaires', 'Air France'),
('2025-02-04 16:00:00', 'Perpignan', '2025-02-04 20:00:00', 'Djerba', 'Economique', 'Air France'), 
('2025-02-05 12:00:00', 'Caen', '2025-02-05 16:30:00', 'Mykonos', 'Premium economique', 'Air France'),
('2025-02-06 17:00:00', 'Avignon', '2025-02-06 21:00:00', 'Malaga', 'Low cost', 'Ryanair'),
('2025-02-07 14:30:00', 'Biarritz', '2025-02-07 19:30:00', 'Athènes', 'Charter', 'Transavia'), 
('2025-02-08 15:30:00', 'Poitiers', '2025-02-08 19:30:00', 'Ibiza', 'Affaires', 'Air France'),
('2025-02-09 16:00:00', 'Mulhouse', '2025-02-09 20:00:00', 'Porto', 'Economique', 'EasyJet'), 
('2025-02-10 13:30:00', 'Brest', '2025-02-10 18:00:00', 'Cagliari (Sardaigne)', 'Affaires', 'Air France');

-- Création des réservations
INSERT INTO RESERVATIONS (id_client, id_conseiller, id_vol, statut, dh_paiement, type_paiement, nom_reservateur, prenom_reservateur, email_reservateur, numero_telephone_reservateur) VALUES
(1, 1, 1, 'Confirmée', '2025-01-14 09:00:00', 'Carte bancaire', 'Dubois', 'Alice', 'alice.dubois@exemple.com', '0610101010'),
(2, 2, 2, 'Confirmée', '2025-01-15 10:00:00', 'Virement', 'Lemoine', 'Bernard', 'bernard.lemoine@exemple.com', '0620202020'),
(3, 3, 3, 'Annulée', '2025-01-16 11:00:00', 'Virement', 'Petit', 'Claire', 'claire.petit@exemple.com', '0630303030'),
(4, 4, 4, 'Confirmée', '2025-01-17 10:30:00', 'Carte bancaire', 'Roux', 'David', 'david.roux@exemple.com', '0640404040'),
(5, 5, 5, 'En attente', '2025-01-18 12:00:00', 'Cheque', 'Moreau', 'Emma', 'emma.moreau@exemple.com', '0650505050'),
(6, 1, 6, 'Confirmée', '2025-01-19 13:00:00', 'Carte bancaire', 'Giraud', 'Lucas', 'lucas.girard@exemple.com', '0660606060'),
(7, 2, 7, 'Confirmée', '2025-01-20 14:30:00', 'Carte bancaire', 'Perrin', 'Sophie', 'sophie.perrin@exemple.com', '0670707070'),
(8, 3, 8, 'Annulée', '2025-01-21 09:30:00', 'Virement', 'Fernandez', 'Hugo', 'hugo.fernandez@exemple.com', '0680808080'),
(9, 4, 9, 'Confirmée', '2025-01-22 10:30:00', 'Carte bancaire', 'Chevalier', 'Marie', 'marie.chevalier@exemple.com', '0690909090'),
(10, 5, 10, 'En attente', '2025-01-23 15:00:00', 'Especes', 'Dupuy', 'Thomas', 'thomas.dupuy@exemple.com', '0611111111'),
(11, 1, 11, 'Confirmée', '2025-01-24 16:00:00', 'Carte bancaire', 'Vincent', 'Louise', 'louise.vincent@exemple.com', '0621212121'),
(12, 2, 12, 'Annulée', '2025-01-25 08:30:00', 'Virement', 'Bertrand', 'Hélène', 'helene.bertrand@exemple.com', '0631313131'),
(13, 3, 13, 'Confirmée', '2025-01-26 14:00:00', 'Carte bancaire', 'Simon', 'Patrick', 'patrick.simon@exemple.com', '0641414141'),
(14, 4, 14, 'En attente', '2025-01-27 09:30:00', 'Especes', 'Lambert', 'Isabelle', 'isabelle.lambert@exemple.com', '0651515151'),
(15, 5, 15, 'Confirmée', '2025-01-28 13:00:00', 'Carte bancaire', 'Leroy', 'Philippe', 'philippe.leroy@exemple.com', '0661616161'),
(16, 1, 16, 'Annulée', '2025-01-29 10:00:00', 'Virement', 'Girard', 'Nathalie', 'nathalie.girard@exemple.com', '0671717171'),
(17, 2, 17, 'Confirmée', '2025-01-30 11:30:00', 'Carte bancaire', 'Colin', 'Cédric', 'cedric.colin@exemple.com', '0681818181'),
(18, 3, 18, 'En attente', '2025-01-31 15:00:00', 'Especes', 'Benoit', 'Laetitia', 'laetitia.benoit@exemple.com', '0691919191'),
(19, 4, 19, 'Confirmée', '2025-02-01 12:30:00', 'Carte bancaire', 'Robin', 'Chloé', 'chloe.robin@exemple.com', '0612121212'),
(20, 5, 20, 'Annulée', '2025-02-02 13:30:00', 'Virement', 'Marchand', 'Julien', 'julien.marchand@exemple.com', '0622222222'),
(21, 1, 21, 'Confirmée', '2025-02-03 11:00:00', 'Carte bancaire', 'Meyer', 'Camille', 'camille.meyer@exemple.com', '0622323232'),
(22, 2, 22, 'En attente', '2025-02-04 14:00:00', 'Especes', 'Noel', 'Adrien', 'adrien.noel@exemple.com', '0622424242'),
(23, 3, 23, 'Confirmée', '2025-02-05 10:30:00', 'Cheque', 'Renaud', 'Lucie', 'lucie.renaud@exemple.com', '0622525252'),
(24, 4, 24, 'Annulée', '2025-02-06 16:00:00', 'Virement', 'Charpentier', 'Sylvain', 'sylvain.charpentier@exemple.com', '0622626262'),
(25, 5, 25, 'Confirmée', '2025-02-07 13:30:00', 'Carte bancaire', 'Leclerc', 'Anna', 'anna.leclerc@exemple.com', '0622727272'),
(26, 1, 26, 'En attente', '2025-02-08 15:00:00', 'Especes', 'Garcia', 'Pauline', 'pauline.garcia@exemple.com', '0622828282'),
(27, 2, 27, 'Confirmée', '2025-02-09 14:00:00', 'Carte bancaire', 'Martinez', 'Olivier', 'olivier.martinez@exemple.com', '0622929292'),
(28, 3, 28, 'Annulée', '2025-02-10 12:30:00', 'Virement', 'Lopez', 'Manon', 'manon.lopez@exemple.com', '0623030303');