package ca.usherbrooke.fgen.api.business;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;


public class AddMDep implements Serializable {
    @JsonbProperty("idCompte")
    private String idCompte;

    @JsonbProperty("montant")
    private String montant;

    // Constructeur
    public AddMDep(String idCompte, String montant) {
        this.idCompte = idCompte;
        this.montant = montant;
    }

    public AddMDep() {}

    public String getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(String idCompte) {
        this.idCompte = idCompte;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

}
