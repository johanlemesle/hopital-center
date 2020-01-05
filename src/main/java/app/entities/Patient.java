package app.entities;

import java.util.ArrayList;

/**
 * Malade
 */
public class Patient extends Personne {
    public Patient(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    private String mutuelle;
    private ArrayList<Docteur> soins = new ArrayList<>();

    public String getMutuelle() {
        return mutuelle;
    }

    public ArrayList<Docteur> getSoins() {
        return soins;
    }
}