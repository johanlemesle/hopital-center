package app.graphical_user_interface;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

import app.Hopital;
import app.database_manager.Adresse;
import app.database_manager.Rotation;
import app.database_manager.Specialite;
import app.database_manager.TypeVoie;
import app.database_manager.Utils;
import app.database_manager.entities.Chambre;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Hospitalisation;
import app.database_manager.entities.Infirmier;
import app.database_manager.entities.Malade;
import app.database_manager.entities.Service;
import app.database_manager.entities.Soin;

/*

Idées de graphes : 

Nb d'hospitalisations par service : ok
Nb de maladie contractée par service ? 

Salaire par infirmier ? 
Nombre de chambre dans un service ? 

*/

public class PieChartTest extends JFrame {

      private static final long serialVersionUID = -7145470278665113308L;

      public PieChartTest(String title, PieDataset dataset1, String chartTitle) // Constructeur
      {
            super(title);
            setContentPane(createDemoPanel(dataset1, chartTitle));
            
      }

      public static JFreeChart createChart(PieDataset dataset, String chartTitle) {
            JFreeChart chart = ChartFactory.createPieChart(chartTitle, // chart title
                        dataset, // data
                        true, // include legend
                        true, false);

            return chart;
      }

      public JPanel createDemoPanel(PieDataset dataset, String chartTitle) {
            JFreeChart chart = createChart(dataset, chartTitle);
            return new ChartPanel(chart);
      }

      public static void tst(Hopital hopital) {
            // Malade johan = new Malade("Lemesle", "Johan", "06 06 06 06 06",
            //             new Adresse("12", "de la Bretonne", "78220", "Viroflay", TypeVoie.rue), "MU", null, null);
            // Malade redoine = new Malade("Lahdiri", "Redoine", "0708070982",
            //             new Adresse("37", "de grenelle", "75015", "Juvisy sur Marne", TypeVoie.avenue), "MU", null,
            //             null);

            // Malade john = new Malade("Dark", "John", "0353426153",
            //             new Adresse("26", "de grounelle", "75003", "Paris sur Orge", TypeVoie.boulevard), "MU", null,
            //             null);

            // Malade hermione = new Malade("Granger", "Hermione", "0023212322",
            //             new Adresse("21", "de poudlard", "91200", "Marseille sur Orge", TypeVoie.place), "MU", null,
            //             null);
            // Malade walid = new Malade("jabari", "walid", "0767248491",
            //             new Adresse("3", "Lazare Carnot", "78220", "Viroflay", TypeVoie.boulevard), "MU", null, null);
            // Malade jacques = new Malade("chirac", "jacques", "0767123491",
            //             new Adresse("2", "Laurent Blanc", "78220", "Viroflay", TypeVoie.rue), "MU", null, null);

            // Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
            //             new Adresse("10", "du Général Leclerc", "78140", "Vélizy Villacoublay", TypeVoie.avenue),
            //             Specialite.generaliste, null, null);
            // Docteur bibiche = new Docteur("Bibiche", "Ichli", "0165345321",
            //             new Adresse("9", "du Dassault Dassault", "78140", "Vélizy Villacoublay", TypeVoie.avenue),
            //             Specialite.generaliste, null, null);

            // Service urgence = new Service("URG", "Urgences", 'B', poulain, null, null, null);
            // Service pediatrie = new Service("PED", "Pediatrie", 'B', poulain, null, null, null);
            // Service asile = new Service("ASL", "Asile", 'B', poulain, null, null, null);
            // Service radiologie = new Service("RAD", "Radiologie", 'A', bibiche, null, null, null);

            // Infirmier marec = new Infirmier("Marec", "Affeli", "0670223410",
            //             new Adresse("12", "Lazare Carnot", "78220", "Viroflay", TypeVoie.avenue), asile, Rotation.Jour,
            //             1540.4, null);

            // Infirmier jamel = new Infirmier("Debbouze", "Jamel", "0654237612",
            //             new Adresse("11", "Lazare ", "78220", "Luciole", TypeVoie.avenue), asile, Rotation.Jour,
            //             1520.09, null);
            // Infirmier ramzi = new Infirmier("Kika", "Ramzi", "0654237612",
            //             new Adresse("11", "Lazare ", "78220", "Luciole", TypeVoie.boulevard), radiologie, Rotation.Jour,
            //             1520.1, null);

            // Infirmier diane = new Infirmier("Vilandrau", "Diane", "0654237612",
            //             new Adresse("11", "Bleuet ", "78220", "Luciole", TypeVoie.avenue), pediatrie, Rotation.Jour,
            //             1520.4, null);

            // Chambre room205 = new Chambre(205, asile, marec, (byte) 5, null);

            // // On ne peut pas faire deux hospitalisation différente sur un même Malade car
            // // même clé

            // Hospitalisation greffe = new Hospitalisation(walid, urgence, room205, 2, null, null);
            // Hospitalisation platre = new Hospitalisation(johan, urgence, room205, 1, null, null);
            // Hospitalisation piqure = new Hospitalisation(redoine, urgence, room205, 1, null, null);
            // Hospitalisation couture = new Hospitalisation(john, urgence, room205, 1, null, null);
            // Hospitalisation dechirure = new Hospitalisation(hermione, urgence, room205, 1, null, null);
            // Hospitalisation coupure = new Hospitalisation(jacques, radiologie, room205, 1, null, null);

            // hopital.insert("Malade", walid);
            // hopital.insert("Malade", johan);
            // hopital.insert("Malade", redoine);
            // hopital.insert("Malade", john);
            // hopital.insert("Malade", hermione);
            // hopital.insert("Malade", jacques);
            // hopital.insert("infirmier", marec);
            // hopital.insert("infirmier", jamel);
            // hopital.insert("infirmier", ramzi);
            // hopital.insert("infirmier", diane);
            // hopital.insert("docteur", poulain);
            // hopital.insert("docteur", bibiche);
            // hopital.insert("soin", new Soin(poulain, redoine, null));
            // hopital.insert("service", urgence);
            // hopital.insert("service", pediatrie);
            // hopital.insert("service", asile);
            // hopital.insert("service", radiologie);
            // hopital.insert("hospitalisation", greffe);
            // hopital.insert("hospitalisation", couture);
            // hopital.insert("hospitalisation", dechirure);
            // hopital.insert("hospitalisation", piqure);
            // hopital.insert("hospitalisation", platre);
            // hopital.insert("hospitalisation", coupure);

            // Ici, on get toutes les informations de l'hopital pour les stocker dans
            // résultat
            ArrayList<Pair<String, Object>> resultat = Utils.get(
                        "malades{*}&services{*}&docteurs{*}&infirmiers{*}&hospitalisations{*}&chambres{*}&employes{*}",
                        hopital);

            // Récupérer le nom des services
            ArrayList<Pair<String, Object>> intermed1 = Utils.get("services{nom}", hopital);
            ArrayList<Pair<String, Object>> intermed2 = (ArrayList<Pair<String, Object>>) intermed1.get(0).getRight();
            ArrayList<Pair<String, Object>> servicesName = (ArrayList<Pair<String, Object>>) intermed2.get(0)
                        .getRight();

            // Récupérer le nom des services pour les hospitalisations
            ArrayList<Pair<String, Object>> interm1 = Utils.get("hospitalisations{service{nom}}", hopital);
            ArrayList<Pair<String, Object>> interm2 = (ArrayList<Pair<String, Object>>) interm1.get(0).getRight();
            ArrayList<Pair<String, Object>> interm3 = (ArrayList<Pair<String, Object>>) interm2.get(0).getRight();
            ArrayList<Pair<String, Object>> interm4 = (ArrayList<Pair<String, Object>>) interm3.get(0).getRight();

            // Récupérer le nom des srrvices pour les infirmiers
            ArrayList<Pair<String, Object>> int1 = Utils.get("infirmiers{service{nom}}", hopital);
            ArrayList<Pair<String, Object>> int2 = (ArrayList<Pair<String, Object>>) int1.get(0).getRight();
            ArrayList<Pair<String, Object>> int3 = (ArrayList<Pair<String, Object>>) int2.get(0).getRight();
            ArrayList<Pair<String, Object>> int4 = (ArrayList<Pair<String, Object>>) int3.get(0).getRight();

            // Test : Cette ligne affiche le nom du service de l'hospitalisation numéro 1
            // get(0)
            System.out.println(interm4.get(0).getRight());
            System.out.println(int4.get(0).getRight());

            // Données
            DefaultPieDataset dataset = new DefaultPieDataset();
            DefaultPieDataset dataset1 = new DefaultPieDataset();

            for (int i = 0; i < servicesName.size(); i++) {
                  Random r = new Random();
                  double randomValue = 2 + (10 - 2) * r.nextDouble();
                  String tst = servicesName.get(i).getRight().toString();

                  // dataset.setValue(tst, randomValue);
                  dataset.setValue(tst, extractNumberHospitalisation(tst, interm4));
                  dataset1.setValue(tst, extractNumberInfirmiers(tst, int4));
            }

            // création de mon chart
            PieChartTest chart1 = new PieChartTest("JABARI LEMESLE LAHDIRI", dataset,
                        "Nb hospitalisations par service");
            chart1.setSize(560, 360);
            chart1.setDefaultCloseOperation(HIDE_ON_CLOSE);
            RefineryUtilities.centerFrameOnScreen(chart1);
            chart1.setVisible(true);

            PieChartTest chart2 = new PieChartTest("JABARI LEMESLE LAHDIRI", dataset1, "Nb infirmiers par service");
            chart2.setSize(560, 360);
            chart2.setDefaultCloseOperation(HIDE_ON_CLOSE);
            RefineryUtilities.centerFrameOnScreen(chart2);
            chart2.setVisible(true);

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

      public static int extractNumberInfirmiers(String nomservice, ArrayList<Pair<String, Object>> list) {
            int nb = 0;

            for (int i = 0; i < list.size(); i++) {
                  if (nomservice == list.get(i).getRight())
                        nb++;
            }

            return nb;
      }

}
