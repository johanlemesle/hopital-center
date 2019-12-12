package app.database_manager;

import java.util.ArrayList;

/**
 * Docteur
 */
enum Specialite {
    generaliste("Généraliste"), dentiste("Dentiste"), ophtalmologue("Ophtalmologue");

    private String nom;

    Specialite(String s) {
        this.nom = s;
    }

    @Override
    public String toString() {
        return nom;
    }

};

public class Docteur extends Employe {
    public Docteur(String nom, String prenom, String tel, Adresse adresse, String specialite) {
        super(nom, prenom, tel, adresse);
        this.specialite = Specialite.valueOf(Utils.purge(specialite)).toString();
    }

    private String specialite;
    private ArrayList<Patient> patientsSoignes = new ArrayList<>();

    public void addPatientSoigne(Patient p) {
        patientsSoignes.add(p);
    }

    @Override
    public String toString() {
        return "Docteur(Specialité : " + this.specialite + ")";
    }
}