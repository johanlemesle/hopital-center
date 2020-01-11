package app;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import app.database_manager.EntityID;
import app.database_manager.Utils;
import app.database_manager.entities.Chambre;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Employe;
import app.database_manager.entities.Hospitalisation;
import app.database_manager.entities.Infirmier;
import app.database_manager.entities.Patient;
import app.database_manager.entities.Service;
import app.database_manager.entities.Soin;

/**
 * Hopital
 */
public class Hopital implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 9055425408285489736L;
    // personnes
    private HashMap<EntityID, Infirmier> infirmiers = new HashMap<>();
    private HashMap<EntityID, Docteur> docteurs = new HashMap<>();
    private HashMap<EntityID, Patient> patients = new HashMap<>();
    private HashMap<EntityID, Employe> employes = new HashMap<>();

    // other
    private List<Soin> soins = new ArrayList<>();
    private HashMap<EntityID, Service> services = new HashMap<>();
    private HashMap<EntityID, Chambre> chambres = new HashMap<>();
    private HashMap<EntityID, Hospitalisation> hospitalisations = new HashMap<>();

    public void insert(String what, Object... args) {
        Utils.invokeFunction("add" + StringUtils.capitalize(what), this, args);
    }

    public HashMap<EntityID, Infirmier> getInfirmiers() {
        return infirmiers;
    }

    public HashMap<EntityID, Docteur> getDocteurs() {
        return docteurs;
    }

    public HashMap<EntityID, Patient> getPatients() {
        return patients;
    }

    public HashMap<EntityID, Service> getServices() {
        return services;
    }

    public HashMap<EntityID, Chambre> getChambres() {
        return chambres;
    }

    public HashMap<EntityID, Hospitalisation> getHospitalisations() {
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

    public void addSoin(Soin s) {
        soins.add(s);
    }

    public void addEmploye(Employe e) {
        employes.put(e.getId(), e);
    }

    public HashMap<EntityID, Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(HashMap<EntityID, Employe> employes) {
        this.employes = employes;
    }
}