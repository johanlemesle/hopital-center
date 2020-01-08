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

        public static void main(String[] args) {
                Hopital h = new Hopital();

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
