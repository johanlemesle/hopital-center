package app.database_manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Hopital
 */
public class Hopital {

    private HashMap<Integer, Batiment> batiments = new HashMap<>();
    private HashMap<Integer, Infirmier> infirmiers = new HashMap<>();
    private HashMap<Integer, Docteur> docteurs = new HashMap<>();
    private HashMap<Integer, Patient> patients = new HashMap<>();

    // patientes
    public void addPatient(Patient p) {
        patients.put(p.getId(), p);
    }

    public Patient getPatient(Integer id) {
        return patients.get(id);
    }

    public HashMap<Integer, Patient> getPatients() {
        return patients;
    }

    // docteurs
    public void addDocteur(Docteur d) {
        docteurs.put(d.getId(), d);
    }

    public Docteur getDocteur(int id) {
        return docteurs.get(id);
    }

    // infirmiers
    public void addInfirmier(Infirmier i) {
        infirmiers.put(i.getId(), i);
    }

    public Infirmier getInfirmier(int id) {
        return infirmiers.get(id);
    }

    // batiments
    public void addBatiment(Batiment b) {
        batiments.put(b.getId(), b);
    }

    public Batiment getBatiment(int id) {
        return batiments.get(id);
    }

    // services
    public void addService(Service service) {
        batiments.get(service.getBatiment().getId()).addService(service);
    }

    public Service getService(int id) {
        for (Integer i : batiments.keySet()) {
            Service s = batiments.get(i).getService(id);
            if (s != null) {
                return s;
            }
        }
        return null;
    }

    // chambres
    public void addChambre(Chambre chambre) {
        batiments.get(chambre.getBatiment().getId()).addChambre(chambre);
    }

    public Chambre getChambre(int id) {
        for (Integer i : batiments.keySet()) {
            Chambre ch = batiments.get(i).getChambre(id);
            if (ch != null) {
                return ch;
            }
        }
        return null;

    }

    // hospitalisations
    public void addHospitalisation(Hospitalisation h) {
        batiments.get(h.getService().getBatiment().getId()).addHospitalisation(h);
    }

    public Hospitalisation getHospitalisation(int id) {
        for (Integer i : batiments.keySet()) {
            Hospitalisation h = batiments.get(i).getHospitalisation(id);
            if (h != null) {
                return h;
            }
        }
        return null;
    }

    public void addSoin(Docteur d, Patient p) {
        docteurs.get(d.getId()).addPatientSoigne(p);
        patients.get(p.getId()).addSoinRecu(d);
    }

    public ArrayList<Pair<String, Object>> get(String what) {
        return Utils.get(what, this);
    }

    public void insert(String what, Object... args) {
        Utils.insert(what, this, args);
    }

}