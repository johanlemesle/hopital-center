package app.database_manager;

public class Adresse {
    enum TypeVoie {
        rue, avenue, place, boulevard,
    }

    private String commune, nomVoie, numVoie, codePostal;
    private TypeVoie typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

}
