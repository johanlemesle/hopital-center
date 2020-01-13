package app.database_manager;

/**
 * Date
 */
public class Date {

    private Byte jour;
    private Integer annee;
    private Mois mois;

    public Date(Byte jour, Integer annee, Mois mois) {
        this.jour = jour;
        this.annee = annee;
        this.mois = mois;
    }

    public Byte getJour() {
        return jour;
    }

    public void setJour(Byte jour) {
        this.jour = jour;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Mois getMois() {
        return mois;
    }

    public void setMois(Mois mois) {
        this.mois = mois;
    }
}

enum Mois {
    Janvier, Fevrier, Mars, Avril, Mai, Juin, Juiller, Aout, Septembre, Octobre, Novembre, Decembre;
}
