package graphical_user_interface;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import app.Hopital;
import app.database_manager.Adresse;
import app.database_manager.Specialite;
import app.database_manager.Utils;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Patient;
import app.database_manager.entities.Service;

/**
 * Unit test for simple App.
 */

// ici on test les fonctions.
// 1. déclarer une fonction précédée par le keyword @Test
// 2. mettre les test dans cette fonction
// 3. cliquer sur 'run test' ou 'debug test' au dessus de la fonction.
// 'run test' au dessus de la classe va executer tous les test à l'intérieur

public class AppTest {
        /**
         * Rigorous Test.
         */
        @Test
        public void test1() {

        }

        @Test
        public void test2() {
                Hopital hopital = new Hopital();

                Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06",
                                new Adresse("12", "rue", "de la Bretonne", "78220", "Viroflay"));
                Patient redoine = new Patient("Lahdiri", "Redoine", "0708070982",
                                new Adresse("37", "quai", "de grenelle", "75015", "Juvisy sur Marne"));
                Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                                new Adresse("10", "Boulevard", "du Général Leclerc", "78140", "Vélizy Villacoublay"),
                                Specialite.generaliste);
                Patient walid = new Patient("jabari", "walid", "0767248491",
                                new Adresse("3", "rue", "Lazare Carnot", "78220", "Viroflay"));

                hopital.insert("patient", walid);
                hopital.insert("patient", johan);
                hopital.insert("patient", redoine);
                hopital.insert("docteur", poulain);
                hopital.insert("soin", poulain, redoine);

                ArrayList<Pair<String, Object>> resultat = Utils.get("*", hopital);

                System.out.println("done");
        }
}
