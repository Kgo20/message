package ca.usherbrooke.fgen.api.business;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;

public class Buy implements Serializable {

    @JsonbProperty("nom")
    private String nom;

    @JsonbProperty("symbole")
    private String symbole;

    @JsonbProperty("nbAction")
    private String nbAction;

    @JsonbProperty("prixCourant")
    private String prixCourant;

    @JsonbProperty("compte")
    private String compte;

    // Constructeur
    public Buy(String nom, String symbole, String nbAction, String prixCourant, String compte) {
        this.nom = nom;
        this.symbole = symbole;
        this.nbAction = nbAction;
        this.prixCourant = prixCourant;
        this.compte = compte;
    }

    public Buy() {}


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSymbole() {
        return symbole;
    }

    public String getNbAction() {
        return nbAction;
    }

    public String getPrixCourant() {
        return prixCourant;
    }

    public String getCompte() {
        return compte;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public void setNbAction(String nbAction) {
        this.nbAction = nbAction;
    }

    public void setPrixCourant(String prixCourant) {
        this.prixCourant = prixCourant;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }
}



