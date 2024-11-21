package ca.usherbrooke.fgen.api.business;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;

public class Compte implements Serializable {
    @JsonbProperty("compteId")
    public String compteId;

    @JsonbProperty("nomCompte")
    public String nomCompte;

    @JsonbProperty("montant")
    public String montant;

    @JsonbProperty("montant_depart")
    public String montant_depart;

    public Compte(String compteId, String nomCompte, String montant, String montant_depart) {
        this.compteId = compteId;
        this.nomCompte = nomCompte;
        this.montant = montant;
        this.montant_depart = montant_depart;
    }

    public Compte() {}

    public String getNomCompte() {
        return nomCompte;
    }

    public String getMontant() {
        return montant;
    }

    public String getMontant_depart() {
        return montant_depart;
    }

    public void setNomCompte(String nomCompte) {
        this.nomCompte = nomCompte;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public void setMontant_depart(String montant_depart) {
        this.montant_depart = montant_depart;
    }

    public String getCompteId() {
        return compteId;
    }

    public void setCompteId(String compteId) {
        this.compteId = compteId;
    }
}


