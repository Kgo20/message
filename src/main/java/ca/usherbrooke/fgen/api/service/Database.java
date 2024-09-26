package ca.usherbrooke.fgen.api.service;

import ca.usherbrooke.fgen.api.business.Info;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5444/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";



    public static List<Info> loadCompte(String cip) {
        List<Info> comptes = new ArrayList<>();
        String query = "select u.cip, prenom, u.nom, c.nom, montant, montant_depart, prix_acquisition, quantie_action, a.diminutif, a.nom as nomAction " +
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
                        rs.getString("quantie_action"),
                        rs.getString("diminutif"),
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
}
