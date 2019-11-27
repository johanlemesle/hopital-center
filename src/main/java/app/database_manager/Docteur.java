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
        specialite = Utils.purge(specialite);

        this.specialite = Specialite.valueOf(specialite).toString();
    }

    String specialite;
    ArrayList<Patient> patientsSoignes = new ArrayList<>();

    @Override
    public String toString() {
        return "Docteur(Specialité : " + this.specialite + ")";
    }
}