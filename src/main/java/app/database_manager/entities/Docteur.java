package app.database_manager.entities;

import java.util.ArrayList;

import app.database_manager.Adresse;
import app.database_manager.Specialite;;

/**
 * Docteur
 */
public class Docteur extends Employe {
    public Docteur(String nom, String prenom, String tel, Adresse adresse, String specialite) {
        super(nom, prenom, tel, adresse);
        this.specialite = specialite;
    }

    private String specialite;
    private ArrayList<Patient> soins = new ArrayList<>();

    public void addSoin(Patient p) {
        this.soins.add(p);
    }

    @Override
    public String toString() {
        return "Docteur(Specialité : " + this.specialite + ")";
    }
}