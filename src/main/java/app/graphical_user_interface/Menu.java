package app.graphical_user_interface;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Service;
import app.database_manager.Utils;
/**
 * menu
 */

public class Menu {
        public static void main(String[] args) {
                // Hopital h = new Hopital();

                // Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06",
                // new Adresse("12", "rue", "de la Bretonne", 78220, "Viroflay"));
                // Patient redoine = new Patient("Lahdiri", "Redoine", "0708070982",
                // new Adresse("37", "quai", "de grenelle", 75015, "Juvisy sur Marne"));
                // Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                // new Adresse("10", "Boulevard", "du Général Leclerc", 78140, "Vélizy
                // Villacoublay"),
                // "Généraliste");
                // Patient walid = new Patient("jabari", "walid", "0767248491",
                // new Adresse("3", "rue", "Lazare Carnot", 78220, "Viroflay"));
                // h.insert("patient", johan);
                // h.insert("patient", redoine);
                // h.insert("docteur", poulain);
                // h.insert("soin", poulain, redoine);
                // ArrayList<Pair<String, Object>> obj = Utils
                // .get("patients{nom&prenom}&docteurs{patientsSoignes{nom&prenom}}", h);
                List<Pair<Field, Object>> var = Utils.extractNestedFields(Service.class, 0, 5);
                System.out.println("done");
        }
}
