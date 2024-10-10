package ca.usherbrooke.fgen.api.service;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5444/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void acheterAction(String nomAction, String nomSymbole, String idCompte, int nbAction, String prixPaye) {




        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            /*
            // Vérifier s'il y a déjà un investissement
            String verfComp = "SELECT COUNT(*) FROM compte AS com " +
                "LEFT JOIN invest AS inv ON com.id_compte = inv.id_compte " +
                "LEFT JOIN action AS act ON inv.id_action = act.id_action " +
                "WHERE act.symbole = " + nomSymbole;

            // Préparer la requête SQL
            PreparedStatement stmt = conn.prepareStatement(verfComp);
            //stmt1.setString(1, nomSymbole); //UTILISÉ AVANT
            // Exécuter la requête et récupérer les résultats
            ResultSet rs1 = stmt.executeQuery();

            // Parcourir les résultats
            int nbSymbol = rs1.getInt("count");
            */

            ///---^JUSTE DES NOTES^---///

            //----------Update invest-------------
            String updateInvest = "UPDATE invest " +
                    "SET prix_acquisition = inv.prix_acquisition + " + prixPaye + ", " +
                    "quantite_action = inv.quantite_action + " + nbAction +
                    "FROM invest AS inv " +
                    "INNER JOIN action AS act ON inv.Id_Action = act.Id_Action " +
                    "INNER JOIN compte AS com on com.Id_Compte = inv.Id_Compte " +
                    "WHERE inv.id_compte = 1 AND act.symbole = " + nomSymbole + " AND act.nom = nomAction " +
                    "AND montant - prixPaye >= 0;";

            // Préparer la requête SQL
            PreparedStatement stmtA1 = conn.prepareStatement(updateInvest);
            // Exécuter la requête pour changer les données
            stmtA1.executeQuery();

            //----------Update compte-------------
            String updateCompte = "UPDATE compte AS com " +
                    "SET montant = montant - " + prixPaye +
                    "FROM invest AS inv " +
                    "INNER JOIN action AS act ON inv.Id_Action = act.Id_Action " +
                    "WHERE com.Id_Compte = inv.Id_Compte AND inv.id_compte = " + idCompte +
                    " AND act.symbole = " + nomSymbole + " AND act.nom = " + nomAction +
                    " AND montant - " + prixPaye + " >= 0;";

            // Préparer la requête SQL
            PreparedStatement stmtA2 = conn.prepareStatement(updateCompte);
            // Exécuter la requête pour changer les données
            stmtA2.executeQuery();

            //----------Create action-------------
            String insertAction = "INSERT INTO action(symbole , nom) " +
                    "VALUES (" + nomSymbole + ", " + nomAction + ") " +
                    "ON CONFLICT (symbole, nom) DO NOTHING;";

            // Préparer la requête SQL
            PreparedStatement stmtA3 = conn.prepareStatement(insertAction);
            // Exécuter la requête pour changer les données
            stmtA3.executeQuery();

            //----------Create invest-------------
            String insertInvest = "INSERT INTO invest(prix_acquisition , quantite_action, Id_action, Id_Compte) " +
                    "VALUES (" + prixPaye + ", " + nbAction + ", " +
                    "(SELECT id_action FROM action WHERE symbole = " + nomSymbole + " AND nom = " + nomAction + "), " +
                    idCompte + ") ON CONFLICT (Id_action, Id_Compte) DO NOTHING;";

            // Préparer la requête SQL
            PreparedStatement stmtA4 = conn.prepareStatement(insertInvest);
            // Exécuter la requête pour changer les données
            stmtA4.executeQuery();


        }
        catch(SQLException e){
            e.printStackTrace();

        }
    }


    public static void vendreAction(String nomAction, String nomSymbole, String idCompte, int nbAction, String prixPaye){

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String updateInvest = "UPDATE compte AS com" +
                    "SET montant = montant + " + prixPaye +
                    "FROM invest AS inv" +
                    "INNER JOIN action AS act ON inv.Id_Action = act.Id_Action" +
                    "WHERE com.Id_Compte = inv.Id_Compte AND inv.id_compte = " + idCompte +
                    "AND act.symbole = " + nomSymbole + " AND act.nom = " + nomAction + " AND quantite_action - " + nbAction + " >= 0 ;";

            // Préparer la requête SQL
            PreparedStatement stmtV1 = conn.prepareStatement(updateInvest);
            // Exécuter la requête
            stmtV1.executeQuery();



            // Update de tout
            String updateCompte = "UPDATE invest" +
                    "SET prix_acquisition = inv.prix_acquisition - " + prixPaye + "," +
                    "quantite_action = inv.quantite_action - " + nbAction +
                    "FROM invest AS inv" +
                    "INNER JOIN action AS act ON inv.Id_Action = act.Id_Action" +
                    "INNER JOIN compte AS com on com.Id_Compte = inv.Id_Compte" +
                    "WHERE inv.id_compte = " + idCompte + " AND act.symbole = " + nomSymbole + "AND act.nom = " + nomAction +
                    "AND quantite_action - " + nbAction + " >= 0;";

            // Préparer la requête SQL
            PreparedStatement stmtV2 = conn.prepareStatement(updateCompte);
            // Exécuter la requête pour changer les données
            stmtV2.executeQuery();


            // Delete section dans investissement
            String deleteInvest = "DELETE FROM invest AS inv WHERE inv.quantite_action = 0;";

            // Préparer la requête SQL
            PreparedStatement stmtV3 = conn.prepareStatement(deleteInvest);
            // Exécuter la requête pour changer les données
            stmtV3.executeQuery();


        }
        catch(SQLException e){
            e.printStackTrace();

        }
    }
}
