package ca.usherbrooke.fgen.api.business;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

public class Sell implements Serializable {

    @JsonbProperty("symbole")
    private String symbole;

    @JsonbProperty("compte")
    private String compte;

    @JsonbProperty("nbAVendre")
    private String nbAVendre;

    @JsonbProperty("prixVente")
    private String prixVente;

    @JsonbProperty("cip")
    private String cip;

    // Constructeur par défaut
    public Sell() {
        // Laissez ce constructeur vide pour la désérialisation
    }

    // Constructeur avec paramètres
    public Sell(String symbole, String compte, String cip, String prixVente, String nbAVendre) {
        this.symbole = symbole;
        this.compte = compte;
        this.prixVente= prixVente;
        this.nbAVendre = nbAVendre;
        this.cip = cip;
    }



    public String getSymbole() {
        return symbole;
    }

    public String getCompte() {
        return compte;
    }

    public String getCip() {
        return cip;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getNbAVendre() {
        return nbAVendre;
    }

    public String getPrixVente() {
        return prixVente;
    }

    public void setNbAVendre(String nbAVendre) {
        this.nbAVendre = nbAVendre;
    }

    public void setPrixVente(String prixVente) {
        this.prixVente = prixVente;
    }
}

