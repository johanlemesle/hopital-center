package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Adresse;
import app.database_manager.Specialite;
import app.database_manager.Utils;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Patient;
import app.graphical_user_interface.Window;

/**
 * menu
 */

public class App {
        public static final String PATH_TO_DB_FILE = "database.txt";
        public static final Hopital hopital;
        private static final Window window = new Window();
        static {
                Hopital tmp = null;
                try {
                        tmp = (Hopital) Utils.load(PATH_TO_DB_FILE);
                } catch (Exception e1) {
                        e1.printStackTrace();
                        tmp = new Hopital();
                }
                hopital = tmp;

                window.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                                confirmAndExit();
                        }
                });
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

        public static void main(String[] args) {

                
        }
}
