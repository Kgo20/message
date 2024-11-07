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

