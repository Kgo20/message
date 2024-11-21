package ca.usherbrooke.fgen.api.business;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;


public class CreateCompte implements Serializable {

    @JsonbProperty("nom")
    private String nom;

    @JsonbProperty("cip")
    private String cip;

    @JsonbProperty("montant_depart")
    private String montant_depart;

    @JsonbProperty("montant")
    private String montant;

    // Constructeur
    public CreateCompte(String nom, String cip, String montant_depart, String montant) {
        this.nom = nom;
        this.cip = cip;
        this.montant_depart = montant_depart;
        this.montant = nom;
    }

    public CreateCompte() {}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCip() { return cip; }

    public void setCip(String cip) { this.cip = cip; }

    public String getMontant_depart() {
        return montant_depart;
    }

    public void setMontant_depart(String montant_depart) {
        this.montant_depart = montant_depart;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

}
