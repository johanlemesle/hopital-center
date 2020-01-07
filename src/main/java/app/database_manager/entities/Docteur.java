package app.database_manager.entities;

import java.util.ArrayList;

import app.database_manager.Adresse;
import app.database_manager.Specialite;;

/**
 * Docteur
 */
public class Docteur extends Employe {
    public Docteur(final String nom, final String prenom, final String tel, final Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    private Specialite specialite;
    ArrayList<Patient> patientsSoignes = new ArrayList<>();
    public void addSoin(final Patient p){
        patientsSoignes.add(p);
    }
    
    @Override
    public String toString() {
        return "Docteur(Specialité : " + this.specialite + ")";
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public ArrayList<Patient> getPatientsSoignes() {
        return patientsSoignes;
    }

    public void setSoins(ArrayList<Patient> soins) {
        this.patientsSoignes = soins;
    }
}