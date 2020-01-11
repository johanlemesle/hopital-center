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
import app.database_manager.entities.Chambre;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Hospitalisation;
import app.database_manager.entities.Infirmier;
import app.database_manager.entities.Patient;
import app.database_manager.entities.Service;

/**
 * menu
 *
 */

public class App {

        public static void main(String[] args) 
        {
                Hopital hopital = new Hopital();

                Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06",
                                new Adresse("12", "rue", "de la Bretonne", "78220", "Viroflay"));
                Patient redoine = new Patient("Lahdiri", "Redoine", "0708070982",
                                new Adresse("37", "quai", "de grenelle", "75015", "Juvisy sur Marne"));
                Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                                new Adresse("10", "Boulevard", "du Général Leclerc", "78140", "Vélizy Villacoublay"),
                                "Généraliste");
                Docteur bibiche = new Docteur("Bibiche", "Ichli", "0165345321",
                                new Adresse("9", "rue", "du Dassault Dassault", "78140", "Vélizy Villacoublay"),
                                "Généraliste");
                Patient walid = new Patient("jabari", "walid", "0767248491",
                                new Adresse("3", "rue", "Lazare Carnot", "78220", "Viroflay"));

                Infirmier marec = new Infirmier("Marec", "Affeli", "0670223410", new Adresse("12", "avenue", "Lazare Carnot", "78220", "Viroflay"));

                Service urgence = new Service(new EntitiyID(2), "Urgences", 1, poulain);
                Service pediatrie = new Service(new EntitiyID(1), "Pediatrie", 1, poulain);
                Service asile = new Service(new EntitiyID(3), "Asile", 1, poulain);
                Service radiologie = new Service(new EntitiyID(4), "Radiologie", 1, bibiche);

                Chambre room205 = new Chambre();



                Hospitalisation greffe = new Hospitalisation(walid, urgence, room205, 2);
                Hospitalisation platre = new Hospitalisation(johan, urgence, room205, 1);
                Hospitalisation piqure = new Hospitalisation(redoine, urgence, room205, 1);
                Hospitalisation couture = new Hospitalisation(johan, urgence, room205, 1);
                Hospitalisation dechirure = new Hospitalisation(walid, urgence, room205, 1);



                hopital.insert("patient", walid);
                hopital.insert("patient", johan);
                hopital.insert("patient", redoine);

                hopital.insert("infirmier", marec);

                hopital.insert("docteur", poulain);
                hopital.insert("docteur", bibiche);

                hopital.insert("soin", poulain, redoine);

                hopital.insert("service", urgence);
                hopital.insert("service", pediatrie);
                hopital.insert("service", asile);
                hopital.insert("service", radiologie);

                hopital.insert("hospitalisation",greffe);
                hopital.insert("hospitalisation",couture);
                hopital.insert("hospitalisation",dechirure);
                hopital.insert("hospitalisation",piqure);
                hopital.insert("hospitalisation",platre);

                ArrayList<Pair<String, Object>> resultat = Utils.get(
                                "patients{*}&services{*}&docteurs{*}&infirmiers{*}&hospitalisations{*}&chambres{*}&employes{*}",
                                hopital);

                // Récupérer le nom des services
                
                ArrayList<Pair<String, Object>> intermed1 = Utils.get("services{nom}", hopital);
                ArrayList<Pair<String, Object>> intermed2 = (ArrayList<Pair<String, Object>>) intermed1.get(0).getRight();
                ArrayList<Pair<String, Object>> servicesName = (ArrayList<Pair<String, Object>>) intermed2.get(0).getRight();

                //Récupérer le nombre d'hospitalisation dans un service
                
                ArrayList<Pair<String, Object>> list = Utils.get("hospitalisations{service{nom}}",hopital);

        
                // Données : Nom des services   
                DefaultPieDataset dataset = new DefaultPieDataset();
                
                for (int i = 0; i < servicesName.size(); i++)
                {
                        Random r = new Random();
                        double randomValue = 2 + (10 - 2) * r.nextDouble();
                        String tst = servicesName.get(i).getRight().toString();
                        dataset.setValue(tst, randomValue);
                }

                // création de mon chart
                PieChartTest chart1 = new PieChartTest("JABARI LEMESLE LAHDIRI", dataset);
                chart1.setSize(560, 360);
                RefineryUtilities.centerFrameOnScreen(chart1);
                chart1.setVisible(true);

                System.out.println("done");
		
                

        }

        public int extractNumberHospitalisation(String nomservice,ArrayList<Pair<String, Object>> list)
        {
            int nb = 0;

            for(int i = 0 ; i<list.size() ; i++)
            {
                if (nomservice == list.get(0).getRight())
                nb++;
            }
    
            return nb;
        }
        
}
