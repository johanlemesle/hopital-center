package app.database_manager.entities;

import java.util.HashMap;

import app.database_manager.Adresse;
import app.database_manager.Rotation;

public class Infirmier extends Employe {
    public Infirmier(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    private HashMap<Integer, Chambre> chambres;

    private Service service;
    private Rotation rotation;
    private int salaire;

    // #NUMERO, #CODE_SERVICE, ROTATION, SALAIRE
}
