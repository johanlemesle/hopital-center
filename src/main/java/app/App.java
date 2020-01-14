package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import app.database_manager.Utils;
import app.graphical_user_interface.Login;
import app.graphical_user_interface.Window;

/**
 * menu
 *
 */

public class App {

        public static String PATH_TO_DB_FILE = "database.txt";
        public static Hopital hopital;
        public static Window window;

        public static void launch() {
                window = new Window();
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
                Login.initUsers();
                new Login();
        }

}
