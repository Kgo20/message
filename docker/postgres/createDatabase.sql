CREATE TABLE usager(
                       cip CHAR(8) ,
                       prenom VARCHAR(50)  NOT NULL,
                       nom VARCHAR(50)  NOT NULL,
                       courriel VARCHAR(50) ,
                       mot_passe VARCHAR(50)  NOT NULL,
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
                       UNIQUE (symbole, nom)
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




INSERT INTO  usager(cip, prenom, nom, courriel, mot_passe, date_creation) VALUES ('roya1414','Alexandre','Roy','a@a.com','projet','2024-09-25 19:10:25-07');
INSERT INTO  log(cip_usager, description, date_creation) VALUES ('roya1414','A achete une action','2024-09-25 19:10:25-07');
INSERT INTO  compte(nom, montant, montant_depart, date_creation, cip) VALUES ('creativipi', 10000, 8000, '2024-09-25 19:10:25-07', 'roya1414');
INSERT INTO  action(symbole, nom, date_creation) VALUES ('aapl', 'Apple', '2024-09-25 19:10:25-07');
INSERT INTO  invest(prix_acquisition, quantite_action, date_creation, Id_Action, Id_Compte) VALUES (1800.57, 31, '2024-09-25 19:10:25-07', 1, 1);


\d action --Je ne sais pas ce que ça fait


-------Fonction pour acheter------------
---Update---***L'ordre des requests est vraiment important, sinon, il faut mettre un if dans la fonction***

---Changement des stats dans invest si elle existe et que l'on a assez d'argent---
UPDATE invest
SET prix_acquisition = inv.prix_acquisition + 2000,
    quantite_action = inv.quantite_action + 100
FROM invest AS inv
         INNER JOIN action AS act ON inv.Id_Action = act.Id_Action
         INNER JOIN compte AS com on com.Id_Compte = inv.Id_Compte
WHERE inv.id_compte = 1 AND act.symbole = 'aapl'AND act.nom = 'Apple'
  AND montant - 2000 >= 0;
---Changement des stats dans compte si elle existe et que l'on a assez d'argent---
UPDATE compte AS com
SET montant = montant - 2000
FROM invest AS inv
    INNER JOIN action AS act ON inv.Id_Action = act.Id_Action
WHERE com.Id_Compte = inv.Id_Compte AND inv.id_compte = 1
  AND act.symbole = 'aapl' AND act.nom = 'Apple' AND montant - 2000 >= 0;

---Créer l'action si elle n'existe pas---
INSERT INTO action(symbole , nom)
VALUES ('MC', 'McDonald''s')
ON CONFLICT (symbole, nom) DO NOTHING;

---Créer l'investissment s'il n'existe pas---
INSERT INTO invest(prix_acquisition , quantite_action, Id_action, Id_Compte)
VALUES (12, 10, 2, 1) ON CONFLICT (Id_action, Id_Compte) DO NOTHING;





--Select everything
SELECT * FROM compte AS com
    JOIN invest AS inv ON com.id_compte = inv.id_compte
    JOIN action AS act ON inv.id_action = act.id_action;


-------Fonction pour vendre------------

---Changement des stats dans compte si elle existe et que l'on a assez d'actions---
UPDATE compte AS com
SET montant = montant + 2000
FROM invest AS inv
         INNER JOIN action AS act ON inv.Id_Action = act.Id_Action
WHERE com.Id_Compte = inv.Id_Compte AND inv.id_compte = 1
  AND act.symbole = 'aapl' AND act.nom = 'Apple' AND quantite_action - 100 >= 0;
---Changement des stats dans invest si elle existe et que l'on a assez d'argent---
UPDATE invest
SET prix_acquisition = inv.prix_acquisition - 2000,
    quantite_action = inv.quantite_action - 100
FROM invest AS inv
         INNER JOIN action AS act ON inv.Id_Action = act.Id_Action
         INNER JOIN compte AS com on com.Id_Compte = inv.Id_Compte
WHERE inv.id_compte = 1 AND act.symbole = 'aapl'AND act.nom = 'Apple'
  AND quantite_action - 100 >= 0;

DELETE FROM invest AS inv WHERE inv.quantite_action = 0;

--Select everything
SELECT * FROM compte AS com
                  JOIN invest AS inv ON com.id_compte = inv.id_compte
                  JOIN action AS act ON inv.id_action = act.id_action;

---------------------------------------------------------------------------------------

--Pour ajouter une contrainte
ALTER TABLE invest
    ADD CONSTRAINT unique_action_compte UNIQUE (Id_action, Id_Compte);


--Add action if it doesn't exist already------------
INSERT INTO action(symbole , nom) VALUES ('MC', 'McDonald''s') ON CONFLICT (symbole, nom) DO NOTHING;
SELECT * From action; --Obvious
SELECT * From invest; --Obvious

--Might get removed
SELECT COUNT(*) FROM compte AS com
    LEFT JOIN invest AS inv ON com.id_compte = inv.id_compte
    LEFT JOIN action AS act ON inv.id_action = act.id_action
         WHERE act.symbole = 'aapl';

--Strange attempt with if
--Look at https://www.postgresqltutorial.com/postgresql-plpgsql/plpgsql-if-else-statements/
DECLARE @Result int;
set @Result = SELECT COUNT(*) FROM compte AS com
    LEFT JOIN invest AS inv ON com.id_compte = inv.id_compte
    LEFT JOIN action AS act ON inv.id_action = act.id_action
    WHERE act.symbole = 'aapl';
/*
IF ( = 0)
BEGIN
-- do one thing
-- do another thing
-- do a third thing
END
    ELSE
BEGIN
-- do the other thing(s)
END
END IF;

 */
