package app.database_manager.entities;

import app.database_manager.Adresse;
import app.database_manager.Specialite;;

/**
 * Docteur
 */
public class Docteur extends Employe {
    public Docteur(String nom, String prenom, String tel, Adresse adresse, Specialite specialite) {
        super(nom, prenom, tel, adresse);
        this.specialite = specialite;
    }

    private Specialite specialite;

    // #NUMERO, SPECIALITE
}