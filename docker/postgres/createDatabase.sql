CREATE TABLE usager(
                       cip CHAR(8) ,
                       prenom VARCHAR(50)  NOT NULL,
                       nom VARCHAR(50)  NOT NULL,
                       courriel VARCHAR(50) ,
                       mot_passe VARCHAR(50)  NOT NULL,
                       date_creation TIMESTAMP,
                       PRIMARY KEY(cip)
);

CREATE TABLE log(
                    Id_Log SERIAL,
                    cip_usager CHAR(8)  NOT NULL,
                    description VARCHAR(500)  NOT NULL,
                    date_creation TIMESTAMP NOT NULL,
                    PRIMARY KEY(Id_Log)
);

CREATE TABLE compte(
                       Id_Compte SERIAL,
                       nom VARCHAR(100)  NOT NULL,
                       montant NUMERIC(12, 2),
                       montant_depart NUMERIC(12, 2),
                       date_creation TIMESTAMP NOT NULL,
                       cip CHAR(8)  NOT NULL,
                       PRIMARY KEY(Id_Compte),
                       FOREIGN KEY(cip) REFERENCES usager(cip)
);

CREATE TABLE action(
                       Id_Action SERIAL,
                       diminutif VARCHAR(100)  NOT NULL,
                       nom VARCHAR(200)  NOT NULL,
                       date_creation TIMESTAMP NOT NULL,
                       PRIMARY KEY(Id_Action)
);

CREATE TABLE invest(
                       Id_Investissement SERIAL,
                       prix_acquisition NUMERIC(12, 2) NOT NULL,
                       quantie_action INTEGER NOT NULL,
                       date_creation TIMESTAMP NOT NULL,
                       Id_Action INTEGER NOT NULL,
                       Id_Compte INTEGER NOT NULL,
                       PRIMARY KEY(Id_Investissement),
                       FOREIGN KEY(Id_Action) REFERENCES action(Id_Action),
                       FOREIGN KEY(Id_Compte) REFERENCES compte(Id_Compte)
);




INSERT INTO  usager(cip, prenom, nom, courriel, mot_passe, date_creation) VALUES ('roya1414','Alexandre','Roy','a@a.com','projet','2024-09-25 19:10:25-07');
INSERT INTO  log(cip_usager, description, date_creation) VALUES ('roya1414','A achete une action','2024-09-25 19:10:25-07');
INSERT INTO  compte(nom, montant, montant_depart, date_creation, cip) VALUES ('creativipi', 10000, 8000, '2024-09-25 19:10:25-07', 'roya1414');
INSERT INTO  action(diminutif, nom, date_creation) VALUES ('aapl', 'Apple', '2024-09-25 19:10:25-07');
INSERT INTO  action(diminutif, nom, date_creation) VALUES ('qbr', 'Québecor', '2024-09-25 19:10:25-07');
INSERT INTO  action(diminutif, nom, date_creation) VALUES ('can', 'Canan', '2024-09-25 19:10:25-07');


INSERT INTO  invest(prix_acquisition, quantie_action, date_creation, Id_Action, Id_Compte) VALUES (1800.57, 31, '2024-09-25 19:10:25-07', 1, 1);

INSERT INTO  invest(prix_acquisition, quantie_action, date_creation, Id_Action, Id_Compte) VALUES (45.57, 12, '2024-09-25 19:10:25-07', 2, 1);
INSERT INTO  invest(prix_acquisition, quantie_action, date_creation, Id_Action, Id_Compte) VALUES (666.57, 77, '2024-09-25 19:10:25-07', 3, 1);
