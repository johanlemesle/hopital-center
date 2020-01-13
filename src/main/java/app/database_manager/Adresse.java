package app.database_manager;

public class Adresse {
    private final String numVoie, codePostal;

    private final String commune, nomVoie;
    private final TypeVoie typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

    public Adresse(final String numVoie, final String codePostal, final String commune, final String nomVoie,
            final TypeVoie typeVoie) {
        this.numVoie = numVoie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.nomVoie = nomVoie;
        this.typeVoie = typeVoie;
    }
}
