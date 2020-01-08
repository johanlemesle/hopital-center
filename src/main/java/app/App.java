package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import app.database_manager.Utils;
import app.graphical_user_interface.Window;

/**
 * menu
 */

public class App {
        public static final String PATH_TO_DB_FILE = "database.txt";
        private static final Hopital hopital;
        private static final Window window = new Window();
        static {
                Object tmp = null;
                try {
                        tmp = Utils.load(PATH_TO_DB_FILE);
                } catch (Exception e1) {
                        e1.printStackTrace();
                }

                hopital = (Hopital) tmp;
                window.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                                confirmAndExit();
                        }
                });
        }

        private static void confirmAndExit() {
                int conf = JOptionPane.showConfirmDialog(window, "Enregistrer les modifications avant de quitter?",
                                "Confirmez", JOptionPane.YES_NO_CANCEL_OPTION);
                switch (conf) {
                case JOptionPane.YES_OPTION:
                        try {
                                Utils.save(hopital, PATH_TO_DB_FILE);
                        } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                case JOptionPane.NO_OPTION:
                        window.dispose();
                        System.exit(0);
                default:
                        break;
                }
        }

        public static void main(String[] args) {

                // Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06",
                // new Adresse("12", "rue", "de la Bretonne", "78220", "Viroflay"));
                // Patient redoine = new Patient("Lahdiri", "Redoine", "0708070982",
                // new Adresse("37", "quai", "de grenelle", "75015", "Juvisy sur Marne"));
                // Docteur poulain = new Docteur("Poulain", "Philippe", "0102030405",
                // new Adresse("10", "Boulevard", "du Général Leclerc", "78140", "Vélizy
                // Villacoublay"),
                // "Généraliste");
                // Patient walid = new Patient("jabari", "walid", "0767248491",
                // new Adresse("3", "rue", "Lazare Carnot", "78220", "Viroflay"));
                // hopital.insert("patient", walid);
                // hopital.insert("patient", johan);
                // hopital.insert("patient", redoine);
                // hopital.insert("docteur", poulain);
                // hopital.insert("soin", poulain, redoine);
                // ArrayList<Pair<String, Object>> obj = Utils
                // .get("patients{nom&prenom}&docteurs{patientsSoignes{nom&prenom}}", hopital);
        }
}
