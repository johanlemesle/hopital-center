package app.entities;

import app.enums.TypeVoie;

public class Adresse {
    private int numVoie, codePostal;

    private String commune, nomVoie;
    private TypeVoie typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

}
