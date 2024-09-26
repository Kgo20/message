package ca.usherbrooke.fgen.api.business;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;

public class Info implements Serializable {
    @JsonbProperty("cip")
    public String cip;

    @JsonbProperty("prenom")
    public String prenom;

    @JsonbProperty("nom")
    public String nom;

    @JsonbProperty("montant")
    public String montant;

    @JsonbProperty("montant_depart")
    public String montant_depart;

    @JsonbProperty("prix_acquisition")
    public String prix_acquisition;

    @JsonbProperty("quantie_action")
    public String quantie_action;

    @JsonbProperty("diminutif")
    public String diminutif;

    @JsonbProperty("nomAction")
    public String nomAction;

    // Constructeur
    public Info(String cip, String prenom, String nomUsager, String montant, String montant_depart,
                String prix_acquisition, String quantie_action, String diminutif, String nomAction) {
        this.cip = cip;
        this.prenom = prenom;
        this.nom = nomUsager;
        this.montant = montant;
        this.montant_depart = montant_depart;
        this.prix_acquisition = prix_acquisition;
        this.quantie_action = quantie_action;
        this.diminutif = diminutif;
        this.nomAction = nomAction;
    }
}


