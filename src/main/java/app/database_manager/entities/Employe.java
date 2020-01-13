package app.database_manager.entities;

import app.database_manager.Adresse;
import app.database_manager.Personne;

public class Employe extends Personne {
    /**
     *
     */
    private static final long serialVersionUID = -3937883264867322387L;

    public Employe(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

}
