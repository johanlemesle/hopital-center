package app.graphical_user_interface;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import app.database_manager.Adresse;
import app.database_manager.Docteur;
import app.database_manager.Hopital;
import app.database_manager.Patient;
import app.database_manager.Utils;

/**
 * menu
 */
enum MyEnum {
    lol, xd, mdr;

}

public class Menu {

    public static void main(String[] args) {
        Hopital h = new Hopital();
        Patient p = new Patient("nom", "prenom", "tel", new Adresse());
        h.addPatient(new Patient("Othman", "day", "09 89E3 ", new Adresse()));
        h.addPatient(new Patient("Otahan", "day", "09 89E3 ", new Adresse()));
        h.addPatient(new Patient("Otmsdfan", "day", "09 89E3 ", new Adresse()));
        h.addPatient(new Patient("Othmagfdn", "day", "09 89E3 ", new Adresse()));
        h.addPatient(new Patient("Othmazefdzan", "day", "09 89E3 ", new Adresse()));
        h.addPatient(p);
        Object o = 1;
        Object ret = Utils.get("patient", h, Utils.makeHash(p));
        System.out.println("lol");
    }

}
