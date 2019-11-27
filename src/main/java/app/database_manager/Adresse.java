package app.database_manager;

public class Adresse {
    enum TypeVoie {
        rue, avenue, place, boulevard
    }

    private String commune, nomVoie, numVoie;
    private int codePostal;
    private String typeVoie;

    public Adresse(String numVoie, String typeVoie, String nomVoie, int codePostal, String commune) {
        this.numVoie = numVoie;
        this.typeVoie = typeVoie;
        this.nomVoie = nomVoie;
        this.codePostal = codePostal;
        this.commune = commune;
    }

    /**
     * @return the codePostal
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     * @return the commune
     */
    public String getCommune() {
        return commune;
    }

    /**
     * @return the nomVoie
     */
    public String getNomVoie() {
        return nomVoie;
    }

    /**
     * @return the numVoie
     */
    public String getNumVoie() {
        return numVoie;
    }

    /**
     * @return the typeVoie
     */
    public String getTypeVoie() {
        return typeVoie;
    }

    @Override
    public String toString() {
        return numVoie + ", " + typeVoie + " " + nomVoie + ", " + codePostal + " " + commune;
    }

}
