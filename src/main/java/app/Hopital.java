package app;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import app.database_manager.EntitiyID;
import app.database_manager.Utils;
import app.database_manager.entities.Chambre;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Employe;
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
    private HashMap<EntitiyID, Infirmier> infirmiers = new HashMap<>();
    private HashMap<EntitiyID, Docteur> docteurs = new HashMap<>();
    private HashMap<EntitiyID, Patient> patients = new HashMap<>();
    private HashMap<EntitiyID, Employe> employes = new HashMap<>();
    // other
    private HashMap<EntitiyID, Service> services = new HashMap<>();
    private HashMap<EntitiyID, Chambre> chambres = new HashMap<>();
    private HashMap<EntitiyID, Hospitalisation> hospitalisations = new HashMap<>();

    public void insert(String what, Object... args) {
        Utils.invokeFunction("add" + StringUtils.capitalize(what), this, args);
    }

    public HashMap<EntitiyID, Infirmier> getInfirmiers() {
        return infirmiers;
    }

    public HashMap<EntitiyID, Docteur> getDocteurs() {
        return docteurs;
    }

    public HashMap<EntitiyID, Patient> getPatients() {
        return patients;
    }

    public HashMap<EntitiyID, Service> getServices() {
        return services;
    }

    public HashMap<EntitiyID, Chambre> getChambres() {
        return chambres;
    }

    public HashMap<EntitiyID, Hospitalisation> getHospitalisations() {
        return hospitalisations;
    }

    public void addPatient(Patient p) {
        this.patients.put(p.getId(), p);
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


    public void addSoin(Docteur d, Patient p) {
        p.addSoinRecu(d);
        d.addSoin(p);
    }

    public void addEmploye(Employe e) {
        employes.put(e.getId(), e);
    }


    public void setEmployes(HashMap<EntitiyID, Employe> employes) {
        this.employes = employes;
    }

    public HashMap<EntitiyID, Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(HashMap<EntitiyID, Employe> employes) {
        this.employes = employes;
    }
}