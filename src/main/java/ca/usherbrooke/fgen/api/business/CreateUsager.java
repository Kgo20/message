package ca.usherbrooke.fgen.api.business;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;


public class CreateUsager implements Serializable {

    @JsonbProperty("cip")
    private String cip;

    @JsonbProperty("nom")
    private String prenom;

    @JsonbProperty("nom")
    private String nom;

    @JsonbProperty("courriel")
    private String courriel;

    @JsonbProperty("mot_passe")
    private String mot_passe;

    // Constructeur
    public CreateUsager(String cip, String prenom, String nom, String courriel, String mot_passe) {
        this.cip = cip;
        this.prenom = prenom;
        this.nom = nom;
        this.courriel = courriel;
        this.mot_passe = mot_passe;
    }

    public CreateUsager() {}

    public String getCip() { return cip; }

    public void setCip(String cip) { this.cip = cip; }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMot_passe() {
        return mot_passe;
    }

    public void setMot_passe(String mot_passe) {
        this.mot_passe = mot_passe;
    }

}


