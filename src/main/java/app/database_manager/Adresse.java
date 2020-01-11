package app.database_manager;

public class Adresse {
    private final String numVoie, codePostal;

    private final String commune, nomVoie;
    private final String typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

    public Adresse(final String numVoie, final String codePostal, final String commune, final String nomVoie,
            final String typeVoie) {
        this.numVoie = numVoie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.nomVoie = nomVoie;
        this.typeVoie = typeVoie;
    }
}