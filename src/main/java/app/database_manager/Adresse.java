package app.database_manager;

import java.io.Serializable;

public class Adresse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1258730039783652713L;

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
