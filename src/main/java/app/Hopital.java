package app;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import app.database_manager.Utils;
import app.database_manager.entities.Chambre;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Hospitalisation;
import app.database_manager.entities.Infirmier;
import app.database_manager.entities.Patient;
import app.database_manager.entities.Service;

/**
 * Hopital
 */
public class Hopital implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9055425408285489736L;
    // personnes
    private HashMap<Integer, Infirmier> infirmiers = new HashMap<>();
    private HashMap<Integer, Docteur> docteurs = new HashMap<>();
    private HashMap<Integer, Patient> patients = new HashMap<>();

    // other
    private HashMap<Integer, Service> services = new HashMap<>();
    private HashMap<Integer, Chambre> chambres = new HashMap<>();
    private HashMap<Integer, Hospitalisation> hospitalisations = new HashMap<>();

    public void insert(String what, Object... args) {
        Utils.invokeFunction("add " + StringUtils.capitalize(what), this, args);
    }

    public HashMap<Integer, Infirmier> getInfirmiers() {
        return infirmiers;
    }

    public HashMap<Integer, Docteur> getDocteurs() {
        return docteurs;
    }

    public HashMap<Integer, Patient> getPatients() {
        return patients;
    }

    public HashMap<Integer, Service> getServices() {
        return services;
    }

    public HashMap<Integer, Chambre> getChambres() {
        return chambres;
    }

    public HashMap<Integer, Hospitalisation> getHospitalisations() {
        return hospitalisations;
    }

    public void addInfirmier(Infirmier i) {
        this.infirmiers.put(i.getId(), i);
    }

    public void addDocteur(Docteur d) {
        this.docteurs.put(d.getId(), d);
    }

    public void updatePatient(Patient p) {
        this.patients.put(p.getId(), p);
    }

    public void addService(Service s) {
        this.services.put(s.getId(), s);
    }

    public void addChambre(Chambre c) {
        this.chambres.put(c.getId(), c);
    }

    public void addHospitalisation(Hospitalisation h) {
        this.hospitalisations.put(h.getPatient().getId(), h);
    }
}