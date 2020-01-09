package app;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Adresse;
import app.database_manager.Utils;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Patient;

/**
 * menu
 */

public class App {

        public static void main(String[] args) {
                Hopital hopital = new Hopital();

                Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06",
                                new Adresse("12", "rue", "de la Bretonne", "78220", "Viroflay"));
                Patient redoine = new Patient("Lahdiri", "Redoine", "0708070982",
                                new Adresse("37", "quai", "de grenelle", "75015", "Juvisy sur Marne"));
                Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                                new Adresse("10", "Boulevard", "du Général Leclerc", "78140", "Vélizy Villacoublay"),
                                "Généraliste");
                Patient walid = new Patient("jabari", "walid", "0767248491",
                                new Adresse("3", "rue", "Lazare Carnot", "78220", "Viroflay"));
                hopital.insert("patient", walid);
                hopital.insert("patient", johan);
                hopital.insert("patient", redoine);
                hopital.insert("docteur", poulain);
                hopital.insert("soin", poulain, redoine);
                ArrayList<Pair<String, Object>> resultat = Utils.get("hospitalisations{patient{}&service}", hopital);
                System.out.println("done");

        }
}
