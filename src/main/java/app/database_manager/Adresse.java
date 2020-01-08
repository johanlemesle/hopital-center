package app.database_manager;

public class Adresse {
    private String numVoie, codePostal;

    private String commune, nomVoie;
    private String typeVoie;

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

    public Adresse(String numVoie, String codePostal, String commune, String nomVoie, String typeVoie) {
        this.numVoie = numVoie;
        this.codePostal = codePostal;
        this.commune = commune;
        this.nomVoie = nomVoie;
        this.typeVoie = typeVoie;
    }

    

}
