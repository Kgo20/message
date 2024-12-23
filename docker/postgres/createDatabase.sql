CREATE TABLE usager(
                       cip CHAR(8) ,
                       prenom VARCHAR(50)  NOT NULL,
                       nom VARCHAR(50)  NOT NULL,
                       courriel VARCHAR(50) ,
                       date_creation TIMESTAMP DEFAULT NOW(),
                       PRIMARY KEY(cip)
);

CREATE TABLE log(
                    Id_Log SERIAL,
                    cip_usager CHAR(8)  NOT NULL,
                    description VARCHAR(500)  NOT NULL,
                    date_creation TIMESTAMP DEFAULT NOW(),
                    PRIMARY KEY(Id_Log)
);

CREATE TABLE compte(
                       Id_Compte SERIAL,
                       nom VARCHAR(100)  NOT NULL,
                       montant NUMERIC(12, 2),
                       montant_depart NUMERIC(12, 2),
                       date_creation TIMESTAMP DEFAULT NOW(),
                       cip CHAR(8)  NOT NULL,
                       PRIMARY KEY(Id_Compte),
                       FOREIGN KEY(cip) REFERENCES usager(cip)
);

CREATE TABLE action(
                       Id_Action SERIAL,
                       symbole VARCHAR(100)  NOT NULL,
                       nom VARCHAR(200)  NOT NULL,
                       date_creation TIMESTAMP DEFAULT NOW(),
                       PRIMARY KEY(Id_Action),
                       UNIQUE (symbole)
                   --Empêche d'avoir un object avec le même nom ET symbole à la fois
);

CREATE TABLE invest(
                       Id_Investissement SERIAL,
                       prix_acquisition NUMERIC(12, 2) NOT NULL,
                       quantite_action INTEGER NOT NULL,
                       date_creation TIMESTAMP DEFAULT NOW(),
                       Id_Action INTEGER NOT NULL,
                       Id_Compte INTEGER NOT NULL,
                       PRIMARY KEY(Id_Investissement),
                       FOREIGN KEY(Id_Action) REFERENCES action(Id_Action),
                       FOREIGN KEY(Id_Compte) REFERENCES compte(Id_Compte),
                       UNIQUE (Id_Compte, Id_Action)
    --Empêche d'avoir un object avec le même nom ET symbole à la fois
);




INSERT INTO  usager(cip, prenom, nom, courriel, date_creation) VALUES ('roya1414','Alexandre','Roy','a@a.com','2024-09-25 19:10:25-07');
INSERT INTO  log(cip_usager, description, date_creation) VALUES ('roya1414','A achete une action','2024-09-25 19:10:25-07');
INSERT INTO  compte(nom, montant, montant_depart, date_creation, cip) VALUES ('creativipi', 10000, 8000, '2024-09-25 19:10:25-07', 'roya1414');
INSERT INTO  action(symbole, nom, date_creation) VALUES ('aapl', 'Apple', '2024-09-25 19:10:25-07');
INSERT INTO  invest(prix_acquisition, quantite_action, date_creation, Id_Action, Id_Compte) VALUES (1800.57, 31, '2024-09-25 19:10:25-07', 1, 1);
