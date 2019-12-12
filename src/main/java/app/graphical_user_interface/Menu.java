package app.graphical_user_interface;

import app.database_manager.Adresse;
import app.database_manager.Docteur;
import app.database_manager.Hopital;
import app.database_manager.Patient;

/**
 * menu
 */

public class Menu {
    public static void main(String[] args) {
        Hopital h = new Hopital();

        Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06",
                new Adresse("12", "rue", "de la Bretonne", 78220, "Viroflay"));
        Patient redoine = new Patient("Lahdiri", "Redoine", "0708070982",
                new Adresse("37", "quai", "de grenelle", 75015, "Juvisy sur Marne"));
        Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                new Adresse("10", "Boulevard", "du Général Leclerc", 78140, "Vélizy Villacoublay"), "Généraliste");
        h.insert("patient", johan);
        h.insert("patient", redoine);
        h.insert("docteur", poulain);
        h.insert("soin", poulain, redoine);
        Object obj = h.get("patients");
        System.out.println("done");
    }
}
