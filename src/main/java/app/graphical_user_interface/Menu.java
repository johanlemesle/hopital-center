package app.graphical_user_interface;

import app.database_manager.Adresse;
import app.database_manager.Hopital;
import app.database_manager.Patient;
import app.database_manager.Utils;

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
        h.addPatient(johan);
        h.addPatient(redoine);
        Object ret2 = Utils.get("patients.soinsRecus&nom&prenom&adresse.nomVoie&numVoie&nom&prenom&specialite", h);
        Object ret3 = Utils.get("patients{soinsRecus{nom&prenom&specialite}&nom&prenom&adresse{nomVoie&numVoie}}", h);

        System.out.println("lol");
    }

}
