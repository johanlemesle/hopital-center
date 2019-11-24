package app.database_manager;

import java.util.ArrayList;

/**
 * Docteur
 */
public class Docteur extends Employe {
    public Docteur(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    enum Specialite {

    };

    Specialite specialite;
    ArrayList<Patient> soins;
}