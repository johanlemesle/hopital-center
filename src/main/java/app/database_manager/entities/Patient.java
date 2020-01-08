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

    public void setMutuelle(String mutuelle) {
        this.mutuelle = mutuelle;
    }

    public void setSoinsRecus(ArrayList<Docteur> soinsRecus) {
        this.soinsRecus = soinsRecus;
    }
}