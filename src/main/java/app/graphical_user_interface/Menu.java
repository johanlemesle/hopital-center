package app.graphical_user_interface;

import app.database_manager.Adresse;
import app.database_manager.Hopital;
import app.database_manager.Patient;
import app.database_manager.Utils;

import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

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

        try {
            Object ret4 = Utils.get("patients{adresse&nom&prenom}", h);
            System.out.println("lol");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
