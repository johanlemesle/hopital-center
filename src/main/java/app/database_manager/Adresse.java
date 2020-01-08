package app.database_manager;

public class Adresse {
    private String numVoie;
    private int codePostal;

    private String commune, nomVoie;
    private String typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

    public Adresse(String numVoie, String nomVoie, String commune, int codePostal, String typeVoie) {
        this.numVoie = numVoie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.nomVoie = nomVoie;
        this.typeVoie = typeVoie;
    }

}
