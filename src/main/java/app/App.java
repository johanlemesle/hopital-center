package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
                // if()
                try {
                        tmp = Utils.load(PATH_TO_DB_FILE);
                } catch (Exception e1) {
                        // TODO Auto-generated catch block
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
                if (JOptionPane.showConfirmDialog(window, "Are you sure you want to quit?", "Please confirm",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                }
        }

        public static void main(String[] args) {
                /*
                 * Hopital h = new Hopital();
                 * 
                 * Patient johan = new Patient("Lemesle", "Johan", "06 06 06 06 06", new
                 * Adresse("12", "rue", "de la Bretonne", 78220, "Viroflay")); Patient redoine =
                 * new Patient("Lahdiri", "Redoine", "0708070982", new Adresse("37", "quai",
                 * "de grenelle", 75015, "Juvisy sur Marne")); Docteur poulain = new
                 * Docteur("Poulain", "Philippe", "0102030405", new Adresse("10", "Boulevard",
                 * "du Général Leclerc", 78140, "Vélizy Villacoublay"), "Généraliste"); Patient
                 * walid = new Patient("jabari", "walid", "0767248491", new Adresse("3", "rue",
                 * "Lazare Carnot", 78220, "Viroflay")); h.insert("patient", johan);
                 * h.insert("patient", redoine); h.insert("docteur", poulain); h.insert("soin",
                 * poulain, redoine); ArrayList<Pair<String, Object>> obj = Utils
                 * .get("patients{nom&prenom}&docteurs{patientsSoignes{nom&prenom}}", h);
                 */
                Window w = new Window();
        }
}
