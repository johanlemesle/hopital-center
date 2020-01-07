package app;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Adresse;
import app.database_manager.Utils;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Infirmier;
import app.database_manager.entities.Patient;

/**
 * menu
 */

public class TestForDataBase {
        public static void main(String[] args) {

                Hopital h = new Hopital();

                Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06",
                                new Adresse("12", "rue", "de la Bretonne", 78220, "Viroflay"));
                Patient redoine = new Patient("Lahdiri", "Redoine", "0708070982",
                                new Adresse("37", "quai", "de grenelle", 75015, "Juvisy sur Marne"));
                Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                                new Adresse("10", "Boulevard", "du Général Leclerc", 78140, "Vélizy Villacoublay"));
                Patient walid = new Patient("jabari", "walid", "0767248491",
                                new Adresse("3", "rue", "Lazare Carnot", 78220, "Viroflay"));
                h.insert("patient", johan);
                h.insert("patient", redoine);
                h.insert("patient", walid);
                h.insert("docteur", poulain);
                h.insert("soin", poulain, redoine);
                ArrayList<Pair<String, Object>> obj = Utils
                                .get("patients{nom&prenom}:adresse%carnot&docteurs{patientsSoignes{nom&prenom}}", h);

                System.out.println("done");
                Infirmier wa = new Infirmier("Jabari", "Walid", "10", null, null, null, 3.0);
                System.out.println(Utils.testConditions(wa, "prenom%lid"));
        }
}
