package app.database_manager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import app.database_manager.Utils;

/**
 * Hopital
 */
public class Hopital {

    private HashMap<String, Batiment> batiments = new HashMap<>();
    private HashMap<String, Infirmier> infirmiers = new HashMap<>();
    private HashMap<String, Docteur> docteurs = new HashMap<>();
    private HashMap<String, Patient> patients = new HashMap<>();

    // patientes
    public void addPatient(Patient p) {
        patients.put(p.getTel(), p);
    }

    public Patient getPatient(Integer id) {
        return patients.get(id);
    }

    public HashMap<String, Patient> getPatients() {
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
        batiments.add(b);
    }

    public Batiment getBatiment(int id) {
        return batiments.get(id);
    }

    // services
    public void addService(Service service) {
        batiments.get(service.getBatiment().getId()).addService(service);
    }

    public Service getService(int id) {
        for (Batiment batiment : batiments) {
            Service s = batiment.getService(id);
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
        for (Batiment batiment : batiments) {
            Chambre ch = batiment.getChambre(id);
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
        for (Batiment batiment : batiments) {
            Hospitalisation h = batiment.getHospitalisation(id);
            if (h != null) {
                return h;
            }
        }
        return null;
    }

}