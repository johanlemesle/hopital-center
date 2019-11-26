package app.database_manager;

import java.util.ArrayList;

/**
 * Docteur
 */
public class Docteur extends Employe {
    public Docteur(String nom, String prenom, String tel, Adresse adresse, String specialite) {
        super(nom, prenom, tel, adresse);
        specialite = Utils.purge(specialite);

        this.specialite = Specialite.valueOf(specialite).toString();
    }

    enum Specialite {
        generaliste("Généraliste"), dentiste("Dentiste"), ophtalmologue("Ophtalmologue");

        private String str;

        Specialite(String s) {
            this.str = s;
        }

        @Override
        public String toString() {
            return str;
        }

    };

    String specialite;
    ArrayList<Patient> soins = new ArrayList<>();

    @Override
    public String toString() {
        return "Docteur(Specialité : " + this.specialite + ")";
    }
}