package app.database_manager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hopital
 */
public class Hopital {

    private ArrayList<Batiment> batiments = new ArrayList<>();
    private HashMap<Integer, Employe> employes = new HashMap<>();
    private HashMap<Integer, Patient> patients = new HashMap<>();

    // patientes
    public void ajouterPatient(Patient p) {
        patients.put(p.getId(), p);
    }

    public Patient getPatient(int id) {
        return patients.get(id);
    }

    // employés
    public void ajouterEmploye(Employe e) {
        employes.put(e.getId(), e);
    }

    public Employe getEmploye(int id) {
        return employes.get(id);
    }

    // batiments
    public void ajouterBatiment(Batiment b) {
        batiments.add(b);
    }

    public Batiment getBatiment(int id) {
        return batiments.get(id);
    }

    // services
    public void ajouterService(Service service) {
        batiments.get(service.getBatiment().getId()).ajouterService(service);
    }

    // chambres
    public void ajouterChambre(Chambre chambre) {
        batiments.get(chambre.getBatiment().getId()).ajouterChambre(chambre);
    }
}