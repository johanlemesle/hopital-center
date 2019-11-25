package app.database_manager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hopital
 */
public class Hopital {

    private ArrayList<Batiment> batiments = new ArrayList<>();
    private HashMap<Integer, Infirmier> infirmiers = new HashMap<>();
    private HashMap<Integer, Docteur> docteurs = new HashMap<>();
    private HashMap<Integer, Patient> patients = new HashMap<>();

    // patientes
    public void ajouterPatient(Patient p) {
        patients.put(p.getId(), p);
    }

    public Patient getPatient(int id) {
        return patients.get(id);
    }

    // docteurs
    public void ajouterDocteur(Docteur d) {
        docteurs.put(d.getId(), d);
    }

    public Docteur getDocteur(int id) {
        return docteurs.get(id);
    }

    // infirmiers
    public void ajouterInfirmier(Infirmier i) {
        infirmiers.put(i.getId(), i);
    }

    public Infirmier getInfirmier(int id) {
        return infirmiers.get(id);
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
    public void ajouterChambre(Chambre chambre) {
        batiments.get(chambre.getBatiment().getId()).ajouterChambre(chambre);
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
    public void ajouterHospitalisation(Hospitalisation h) {
        batiments.get(h.getService().getBatiment().getId()).ajouterHospitalisation(h);
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