package app.database_manager;

import java.io.Serializable;

/**
 * Date
 */
public class Date implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3996112805605815166L;
    private Byte jour;
    private Integer annee;
    private Mois mois;

    public Date(Byte jour, Integer annee, Mois mois) {
        this.jour = jour;
        this.annee = annee;
        this.mois = mois;
    }

    @Override
    public String toString() {
        return jour + "/" + mois + "/" + annee;
    }
}

enum Mois {
    Janvier, Fevrier, Mars, Avril, Mai, Juin, Juiller, Aout, Septembre, Octobre, Novembre, Decembre;
}
