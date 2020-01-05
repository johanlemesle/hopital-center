package app.entities;

import java.util.ArrayList;
import app.enums.Specialite;;

/**
 * Docteur
 */
public class Docteur extends Employe {
    public Docteur(String nom, String prenom, String tel, Adresse adresse, Specialite specialite) {
        super(nom, prenom, tel, adresse);
        this.specialite = specialite;
    }

    private Specialite specialite;
    ArrayList<Patient> soins = new ArrayList<>();

    @Override
    public String toString() {
        return "Docteur(Specialité : " + this.specialite + ")";
    }
}