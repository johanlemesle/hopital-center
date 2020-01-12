package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.tuple.Pair;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

import app.database_manager.Adresse;
import app.database_manager.PieChartTest;
import app.database_manager.Rotation;
import app.database_manager.Specialite;
import app.database_manager.Utils;
import app.database_manager.entities.Chambre;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Hospitalisation;
import app.database_manager.entities.Infirmier;
import app.database_manager.entities.Malade;
import app.database_manager.entities.Service;
import app.database_manager.entities.Soin;
import app.graphical_user_interface.Window;

/**
 * menu
 *
 */

public class App {

        public static final String PATH_TO_DB_FILE = "database.txt";
        public static final Hopital hopital;
        public static final Window window = new Window();
        static {
                Hopital tmp = null;
                try {
                        tmp = (Hopital) Utils.load(PATH_TO_DB_FILE);
                } catch (Exception e1) {
                        tmp = new Hopital();
                        e1.printStackTrace();
                }

                hopital = tmp;
                window.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                                confirmAndExit();
                        }
                });
        }

        public static void main(String[] args) {

                Malade johan = new Malade("Lemesle", "Johan", "06 06 06 06 06",
                                new Adresse("12", "rue", "de la Bretonne", "78220", "Viroflay"));
                Malade redoine = new Malade("Lahdiri", "Redoine", "0708070982",
                                new Adresse("37", "quai", "de grenelle", "75015", "Juvisy sur Marne"));

                Malade john = new Malade("Dark", "John", "0353426153",
                                new Adresse("26", "quai", "de grounelle", "75003", "Paris sur Orge"));

                Malade hermione = new Malade("Granger", "Hermione", "0023212322",
                                new Adresse("21", "allée", "de poudlard", "91200", "Marseille sur Orge"));

                Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                                new Adresse("10", "Boulevard", "du Général Leclerc", "78140", "Vélizy Villacoublay"),
                                Specialite.generaliste);
                Docteur bibiche = new Docteur("Bibiche", "Ichli", "0165345321",
                                new Adresse("9", "rue", "du Dassault Dassault", "78140", "Vélizy Villacoublay"),
                                Specialite.generaliste);
                Malade walid = new Malade("jabari", "walid", "0767248491",
                                new Adresse("3", "rue", "Lazare Carnot", "78220", "Viroflay"));

                Service urgence = new Service("URG", "Urgences", 'B', poulain);
                Service pediatrie = new Service("PED", "Pediatrie", 'B', poulain);
                Service asile = new Service("ASL", "Asile", 'B', poulain);
                Service radiologie = new Service("RAD", "Radiologie", 'A', bibiche);

                Infirmier marec = new Infirmier("Marec", "Affeli", "0670223410",
                                new Adresse("12", "avenue", "Lazare Carnot", "78220", "Viroflay"), asile, Rotation.Jour,
                                1520);
                Chambre room205 = new Chambre(205, asile, marec, (byte) 5);

                // On ne peut pas faire deux hospitalisation différente sur un même Malade car
                // même clé
                Hospitalisation greffe = new Hospitalisation(walid, urgence, room205, 2, null, null);
                Hospitalisation platre = new Hospitalisation(johan, urgence, room205, 1, null, null);
                Hospitalisation piqure = new Hospitalisation(redoine, urgence, room205, 1, null, null);
                Hospitalisation couture = new Hospitalisation(john, urgence, room205, 1, null, null);
                Hospitalisation dechirure = new Hospitalisation(hermione, urgence, room205, 1, null, null);

                hopital.insert("Malade", walid);
                hopital.insert("Malade", johan);
                hopital.insert("Malade", redoine);
                hopital.insert("Malade", john);
                hopital.insert("Malade", hermione);

                hopital.insert("infirmier", marec);

                hopital.insert("docteur", poulain);
                hopital.insert("docteur", bibiche);

                hopital.insert("soin", new Soin(poulain, redoine, null));

                hopital.insert("service", urgence);
                hopital.insert("service", pediatrie);
                hopital.insert("service", asile);
                hopital.insert("service", radiologie);

                hopital.insert("hospitalisation", greffe);
                hopital.insert("hospitalisation", couture);
                hopital.insert("hospitalisation", dechirure);
                hopital.insert("hospitalisation", piqure);
                hopital.insert("hospitalisation", platre);

                ArrayList<Pair<String, Object>> resultat = Utils.get(
                                "malades{*}&services{*}&docteurs{*}&infirmiers{*}&hospitalisations{*}&chambres{*}&employes{*}",
                                hopital);

                // Récupérer le nom des services

                ArrayList<Pair<String, Object>> intermed1 = Utils.get("services{nom}", hopital);
                ArrayList<Pair<String, Object>> intermed2 = (ArrayList<Pair<String, Object>>) intermed1.get(0)
                                .getRight();
                ArrayList<Pair<String, Object>> servicesName = (ArrayList<Pair<String, Object>>) intermed2.get(0)
                                .getRight();

                // Récupérer le nombre d'hospitalisation dans un service

                ArrayList<Pair<String, Object>> interm1 = Utils.get("hospitalisations{service{nom}}", hopital);
                ArrayList<Pair<String, Object>> interm2 = (ArrayList<Pair<String, Object>>) interm1.get(0).getRight();
                ArrayList<Pair<String, Object>> interm3 = (ArrayList<Pair<String, Object>>) interm2.get(0).getRight();
                ArrayList<Pair<String, Object>> interm4 = (ArrayList<Pair<String, Object>>) interm3.get(0).getRight();

                // Cette ligne affiche le nom du service de l'hospitalisation numéro 1 get(0)
                System.out.println(interm4.get(0).getRight());
                System.out.println(interm4.get(1).getRight());

                // Données : Nom des services
                DefaultPieDataset dataset = new DefaultPieDataset();

                for (int i = 0; i < servicesName.size(); i++) {
                        Random r = new Random();
                        double randomValue = 2 + (10 - 2) * r.nextDouble();
                        String tst = servicesName.get(i).getRight().toString();

                       // dataset.setValue(tst, randomValue);
                       dataset.setValue(tst, extractNumberHospitalisation(tst, interm4));
                }

                // création de mon chart
                PieChartTest chart1 = new PieChartTest("JABARI LEMESLE LAHDIRI", dataset);
                chart1.setSize(560, 360);
                RefineryUtilities.centerFrameOnScreen(chart1);
                chart1.setVisible(true);
                System.out.println("done");
        }

        public static int extractNumberHospitalisation(String nomservice, ArrayList<Pair<String, Object>> list) {
                int nb = 0;

                for (int i = 0; i < list.size(); i++) {
                        if (nomservice == list.get(i).getRight())
                                nb++;
                }

                return nb;
        }

        private static void confirmAndExit() {
                int choice = JOptionPane.showConfirmDialog(window, "Enregistrer les modifications avant de quitter?",
                                "Confirmez", JOptionPane.YES_NO_CANCEL_OPTION);
                switch (choice) {
                case JOptionPane.YES_OPTION:
                        try {
                                Utils.save(hopital, PATH_TO_DB_FILE);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                case JOptionPane.NO_OPTION:
                        window.dispose();
                        System.exit(0);
                default:
                        break;
                }
        }

}
