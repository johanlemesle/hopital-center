package app.database_manager.entities;

import app.database_manager.Adresse;

public class Employe extends Personne {
    public Employe(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }
}
