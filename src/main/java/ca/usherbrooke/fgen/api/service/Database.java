package ca.usherbrooke.fgen.api.service;

import ca.usherbrooke.fgen.api.business.Compte;
import ca.usherbrooke.fgen.api.business.Info;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5444/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";


    public static Compte getCompteId(String cip) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "select c.id_compte, c.nom, c.montant, c.montant_depart " +
                    "from compte as c where c.cip = ? ";


            // Préparer la requête SQL
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, cip);
            // Exécuter la requête et récupérer les résultats
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Compte compte = new Compte(
                        rs.getString("id_compte"),
                        rs.getString("nom"),
                        rs.getString("montant"),
                        rs.getString("montant_depart")
                );
                return compte;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Info> loadCompte(String cip) {
        List<Info> comptes = new ArrayList<>();

        String query = "select u.cip, u.prenom, u.nom, c.nom AS nomCompte, c.montant, c.montant_depart, i.prix_acquisition, i.quantite_action, a.symbole, a.nom AS nomAction " +
                "from usager as u " +
                "    inner join compte as c ON u.cip = c.cip " +
                "    inner join invest as i ON i.id_compte = c.id_compte " +
                "    inner join action as a on a.id_action = i.id_action Where c.cip = ?";
        System.out.println("----------------------------------------------ici0");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Préparer la requête SQL
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, cip);
            // Exécuter la requête et récupérer les résultats
            ResultSet rs = stmt.executeQuery();
            System.out.println("----------------------------------------------ici1");

            // Parcourir les résultats et les ajouter dans une liste de Map
            while (rs.next()) {
                Info compte = new Info(
                        rs.getString("cip"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("montant"),
                        rs.getString("montant_depart"),
                        rs.getString("prix_acquisition"),
                        rs.getString("quantite_action"),
                        rs.getString("symbole"),
                        rs.getString("nomAction")
                );
                System.out.println("----------------------------------------------ici2" + compte);

                comptes.add(compte); // Ajouter l'enregistrement à la liste
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retourner la liste des comptes en JSON
        return comptes;
    }

    public static void acheterAction(String nomAction, String nomSymbole, String idCompte, int nbAction, double prixPaye) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            //----------Create action-------------
            String insertAction = "INSERT INTO action(symbole, nom) VALUES (?, ?) ON CONFLICT (symbole) DO NOTHING;";
            PreparedStatement stmtA1 = conn.prepareStatement(insertAction);

            stmtA1.setString(1, nomSymbole);
            stmtA1.setString(2, nomAction);

            System.out.println(insertAction);
            // Exécuter la requête pour changer les données
            stmtA1.executeUpdate();  // Utilisez executeUpdate pour INSERT

            //----------Create invest-------------
            String insertInvest = "INSERT INTO invest(prix_acquisition, quantite_action, Id_action, Id_Compte) " +
                    "VALUES (?, ?, " +
                    "(SELECT Id_Action FROM action WHERE symbole = ?), ?) " + // Corrigé ici
                    "ON CONFLICT (Id_action, Id_Compte) DO NOTHING;";

            PreparedStatement stmtA2 = conn.prepareStatement(insertInvest);
            stmtA2.setDouble(1, prixPaye);
            stmtA2.setInt(2, nbAction);


            stmtA2.setString(3, nomSymbole);
            stmtA2.setInt(4, Integer.parseInt(idCompte)); // Remplacez le idCompte par le paramètre correspondant
            int rowsAffectedCreateInvest = stmtA2.executeUpdate();  // Utilisez executeUpdate pour INSERT
            System.out.println("111111111111111111111111111111111111" + rowsAffectedCreateInvest);
            if(rowsAffectedCreateInvest == 0) {
                //----------Update invest-------------
                String updateInvest = "UPDATE invest " +
                        "SET prix_acquisition = ((prix_acquisition * quantite_action) + ? * ?)/ (quantite_action + ?), " +
                        "quantite_action = quantite_action + ? " +
                        "WHERE Id_Compte = ? " +
                        "AND Id_Action = (SELECT Id_Action FROM action WHERE symbole = ?) " +
                        "AND (SELECT montant FROM compte WHERE Id_Compte = ?) - ? >= 0;";

                PreparedStatement stmtA3 = conn.prepareStatement(updateInvest);

                stmtA3.setDouble(1, prixPaye);
                stmtA3.setInt(2, nbAction);
                stmtA3.setInt(3, nbAction);
                stmtA3.setInt(4, nbAction);
                stmtA3.setInt(5, Integer.parseInt(idCompte));
                stmtA3.setString(6, nomSymbole);
                stmtA3.setInt(7, Integer.parseInt(idCompte));
                stmtA3.setDouble(8, prixPaye);
                stmtA3.executeUpdate();  // Utilisez executeUpdate pour UPDATE
            }

            double coutTotal = prixPaye * nbAction;

            //----------Update compte-------------
            String updateCompte = "UPDATE compte AS com " +
                    "SET montant = montant - ? " +
                    "WHERE Id_compte = ? " +
                    "AND montant - ? >= 0;";

            PreparedStatement stmtA4 = conn.prepareStatement(updateCompte);
            stmtA4.setDouble(1, coutTotal);
            stmtA4.setInt(2, Integer.parseInt(idCompte));
            stmtA4.setDouble(3, coutTotal);
            stmtA4.executeUpdate();  // Utilisez executeUpdate pour UPDATE
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void vendreAction(String nomSymbole, String idCompte, int nbAction, double prixPaye) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            //----------Update Compte-------------
            //String updateCompte = "UPDATE compte SET montant = montant + ? " +
            //        "WHERE Id_compte = ?";
            //        "AND (SELECT prix_acquisition FROM invest WHERE Id_Action = " +
            //        "(SELECT Id_Action FROM action WHERE symbole = ?)) - ? >= 0 " +
            //        "AND (SELECT quantite_action FROM invest WHERE Id_Action = " +
            //        "(SELECT Id_Action FROM action WHERE symbole = ?)) - ? >= 0;";

            //PreparedStatement stmtV1 = conn.prepareStatement(updateCompte);
            //stmtV1.setDouble(1, prixPaye);
            //stmtV1.setInt(2, Integer.parseInt(idCompte));
            //stmtV1.setString(3, nomSymbole);
            //stmtV1.setDouble(4, prixPaye);
            //stmtV1.setString(5, nomSymbole);
            //stmtV1.setInt(6, nbAction);
            //stmtV1.executeUpdate();

            String updateCompteV2 = "UPDATE compte SET montant = montant + (? * ?) " +
                    "WHERE Id_compte = ?";

            PreparedStatement stmtV10 = conn.prepareStatement(updateCompteV2);
            stmtV10.setDouble(1, prixPaye);
            stmtV10.setInt(2, nbAction);
            stmtV10.setInt(3, Integer.parseInt(idCompte));
            stmtV10.executeUpdate();

            //----------Update invest-------------
            String updateInvest = "UPDATE invest SET " +
                    "quantite_action = quantite_action - ? " +
                    "WHERE Id_Compte = ? " +
                    "AND Id_Action = (SELECT Id_Action FROM action WHERE symbole = ?) " +
                    "AND quantite_action - ? >= 0;";
            System.out.println(updateInvest);
            PreparedStatement stmtV2 = conn.prepareStatement(updateInvest);
            stmtV2.setInt(1, nbAction);
            stmtV2.setInt(2, Integer.parseInt(idCompte));
            stmtV2.setString(3, nomSymbole);
            stmtV2.setInt(4, nbAction);
            stmtV2.executeUpdate();

            //----------Delete from invest-------------
            String deleteInvest = "DELETE FROM invest WHERE quantite_action = 0;";

            PreparedStatement stmtV3 = conn.prepareStatement(deleteInvest);
            stmtV3.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ajoutMontantDepart(String idCompte, double montant) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            //----------Update invest-------------
            String addMontant = "UPDATE compte\n" +
                    "SET montant = montant + ?,  montant_depart = montant_depart + ?" +
                    "WHERE Id_Compte = ?" +
                    "AND montant + ? >= 0 AND montant_depart + ? >= 0;";

            // Préparer la requête SQL
            PreparedStatement stmtM = conn.prepareStatement(addMontant);
            stmtM.setDouble(1, montant);
            stmtM.setDouble(2, montant);
            stmtM.setInt(3, Integer.parseInt(idCompte));
            stmtM.setDouble(4, montant);
            stmtM.setDouble(5, montant);
            // Exécuter la requête
            stmtM.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();

        }
    }

    public static void creeUsager(String cip, String prenom, String nom, String courriel) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String queryUsagerExist = "SELECT COUNT(*) FROM usager WHERE cip = ?;";
            PreparedStatement stmtque = conn.prepareStatement(queryUsagerExist);
            stmtque.setString(1, cip);

            ResultSet rs2 = stmtque.executeQuery();
            int countUsager = 0;

            if (rs2.next()) {
                countUsager = rs2.getInt(1);  // Récupère la première colonne du résultat
            }

            if (countUsager == 0) {
                //----------Ajout usager-------------
                String creeUsager = "INSERT INTO usager(cip, prenom, nom, courriel) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON CONFLICT (cip) DO NOTHING;";

                // Préparer la requête SQL
                PreparedStatement stmtU = conn.prepareStatement(creeUsager);
                stmtU.setString(1, cip);
                stmtU.setString(2, prenom);
                stmtU.setString(3, nom);
                stmtU.setString(4, courriel);
                // Exécuter la requête
                stmtU.executeUpdate();
            }

            String queryCompteExist = "SELECT COUNT(*) FROM compte WHERE cip = ? AND nom = 'Porfolio';";
            PreparedStatement stmtqce = conn.prepareStatement(queryCompteExist);
            stmtqce.setString(1, cip);

            ResultSet rs = stmtqce.executeQuery();
            int count = 0;

            if (rs.next()) {
                count = rs.getInt(1);  // Récupère la première colonne du résultat
            }

            if (count == 0) {

                String creeCompte = "INSERT INTO compte( nom, montant, montant_depart, cip) " +
                        "VALUES (?, ?, ?, ?);";

                // Préparer la requête SQL
                PreparedStatement stmtC = conn.prepareStatement(creeCompte);
                stmtC.setString(1, "Porfolio");
                stmtC.setDouble(2, 10000);
                stmtC.setDouble(3, 10000);
                stmtC.setString(4, cip);
                stmtC.executeUpdate();
            }

        }
        catch(SQLException e){

            e.printStackTrace();
        }
    }

    public static void creeCompte(String nom, String cip, double montant_depart, double montant) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            //----------Ajout compte-------------
            String creeCompte = "INSERT INTO compte( nom, montant, montant_depart, cip) " +
                    "VALUES (?, ?, ?, ?);";

            // Préparer la requête SQL
            PreparedStatement stmtC = conn.prepareStatement(creeCompte);
            stmtC.setString(1, nom);
            stmtC.setDouble(2, montant);
            stmtC.setDouble(3, montant_depart);
            stmtC.setString(4, cip);

            // Exécuter la requête
            stmtC.executeQuery();
        }
        catch(SQLException e){

            e.printStackTrace();
        }
    }
}