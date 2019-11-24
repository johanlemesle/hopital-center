package app;

public class Adresse {
    private int numVoie, codePostal;

    enum TypeVoie {
        rue, avenue, place, boulevard,
    }

    private String commune, nomVoie;
    private TypeVoie typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }
}
