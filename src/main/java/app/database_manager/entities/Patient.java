package app.database_manager.entities;

import java.util.ArrayList;

import app.database_manager.Adresse;
import app.database_manager.Personne;

/**
 * Malade
 */
public class Patient extends Personne {
    public Patient(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    private String mutuelle;

    public String getMutuelle() {
        return mutuelle;
    }
}