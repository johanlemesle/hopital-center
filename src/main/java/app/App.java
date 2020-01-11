package app;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;
import org.jfree.data.PieDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

import app.database_manager.Adresse;
import app.database_manager.EntitiyID;
import app.database_manager.PieChartTest;
import app.database_manager.Utils;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Patient;
import app.database_manager.entities.Service;

/**
 * menu
 *
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

                Service urgence = new Service(new EntitiyID(2), "Urgences", 1, poulain);
                Service pediatrie = new Service(new EntitiyID(1), "Pediatrie", 1, poulain);
                Service asile = new Service(new EntitiyID(3), "Asile", 1, poulain);


                hopital.insert("patient", walid);
                hopital.insert("patient", johan);
                hopital.insert("patient", redoine);
                hopital.insert("docteur", poulain);
                hopital.insert("soin", poulain, redoine);
                hopital.insert("service", urgence);
                hopital.insert("service", pediatrie);
                hopital.insert("service", asile);


                ArrayList<Pair<String, Object>> resultat = Utils.get(
                                "patients{*}&services{*}&docteurs{*}&infirmiers{*}&hospitalisations{*}&chambres{*}&employes{*}",
                                hopital);

                // Récupérer juste le nom des services
                ArrayList<Pair<String, Object>> lol = Utils.get("services{nom}", hopital);
                ArrayList<Pair<String, Object>> lol2 = (ArrayList<Pair<String, Object>>) lol.get(0).getRight();
                ArrayList<Pair<String, Object>> servicesName = (ArrayList<Pair<String, Object>>) lol2.get(0).getRight();

                DefaultPieDataset dataset = new DefaultPieDataset();
                // incorporation des données
                for (int i = 0; i < servicesName.size(); i++) {
                        Random r = new Random();
                        double randomValue = 2 + (10 - 2) * r.nextDouble();
                        String tst = servicesName.get(i).getRight().toString();

                        dataset.setValue(tst, randomValue);
                }

                /*
                 * dataset.setValue("Pediatrie",new Double(20)); dataset.setValue("Urgences",new
                 * Double(20)); dataset.setValue("Maternite",new Double(100));
                 * dataset.setValue("Radiologie",new Double(10));
                 */

                // création de mon chart
                PieChartTest demo = new PieChartTest("JABARI LEMESLE LAHDIRI", dataset);
                demo.setSize(560, 360);
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);

                System.out.println("done");

        }
}
// hopital.insert("hospitalisation", new Hospitalisation(walid, new Service(),
// new , lit))
// Utils.get("hospitalisations{patient{}&service}", hopital);