package app.entities;

import app.enums.TypeVoie;

public class Adresse {
    private String numVoie, codePostal;

    private String commune, nomVoie;
    private TypeVoie typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

    public Adresse(String numVoie, String codePostal, String commune, String nomVoie, TypeVoie typeVoie) {
        this.numVoie = numVoie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.nomVoie = nomVoie;
        this.typeVoie = typeVoie;
    }

    

}
