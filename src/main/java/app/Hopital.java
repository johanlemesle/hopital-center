package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
<<<<<<< HEAD
    private HashMap<Integer, Infirmier> infirmiers = new HashMap<>();
    private HashMap<Integer, Docteur> docteurs = new HashMap<>();
    private HashMap<Integer, Patient> patients = new HashMap<>();
    private HashMap<Integer, Employe> employes = new HashMap<>();

||||||| merged common ancestors
    private HashMap<EntitiyID, Infirmier> infirmiers = new HashMap<>();
    private HashMap<EntitiyID, Docteur> docteurs = new HashMap<>();
    private HashMap<EntitiyID, Patient> patients = new HashMap<>();
    private HashMap<EntitiyID, Employe> employes = new HashMap<>();

=======
    private HashMap<EntitiyID, Infirmier> infirmiers = new HashMap<>();
    private HashMap<EntitiyID, Docteur> docteurs = new HashMap<>();
    private HashMap<EntitiyID, Patient> patients = new HashMap<>();
    private HashMap<EntitiyID, Employe> employes = new HashMap<>();
>>>>>>> d75fcea16b06c7b84fe7dd42b1fda154f2fa59de
    // other
    private List<Soin> soins = new ArrayList<>();
    private HashMap<Integer, Service> services = new HashMap<>();
    private HashMap<Integer, Chambre> chambres = new HashMap<>();
    private HashMap<Integer, Hospitalisation> hospitalisations = new HashMap<>();

    public void insert(String what, Object... args) {
        Utils.invokeFunction("add" + StringUtils.capitalize(what), this, args);
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

    public void addPatient(Patient p) {
        this.patients.put(p.hashCode(), p);
    }

    public void addInfirmier(Infirmier i) {
        this.infirmiers.put(i.hashCode(), i);
    }

    public void addDocteur(Docteur d) {
        this.docteurs.put(d.hashCode(), d);
    }

    public void updatePatient(Patient p) {
        this.patients.put(p.hashCode(), p);
    }

    public void addService(Service s) {
        this.services.put(s.hashCode(), s);
    }

    public void addChambre(Chambre c) {
        this.chambres.put(c.hashCode(), c);
    }

    public void addHospitalisation(Hospitalisation h) {
        this.hospitalisations.put(h.getPatient().hashCode(), h);
    }

    public void addSoin(Soin s) {
        soins.add(s);
    }

    public void addEmploye(Employe e) {
        employes.put(e.hashCode(), e);
    }

    public HashMap<Integer, Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(HashMap<Integer, Employe> employes) {
        this.employes = employes;
    }
}