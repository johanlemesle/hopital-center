package app.database_manager.entities;

import java.util.ArrayList;

import app.database_manager.Adresse;

/**
 * Malade
 */
public class Patient extends Personne {
    public Patient(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    private String mutuelle;
    private ArrayList<Docteur> soinsRecus = new ArrayList<>();

    public void addSoinRecu(Docteur d) {
        soinsRecus.add(d);
    }

    public String getMutuelle() {
        return mutuelle;
    }

    public ArrayList<Docteur> getSoinsRecus() {
        return soinsRecus;
    }
}