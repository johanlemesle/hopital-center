package app;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import app.database_manager.Utils;
import app.database_manager.entities.Chambre;
import app.database_manager.entities.Docteur;
import app.database_manager.entities.Employe;
import app.database_manager.entities.Hospitalisation;
import app.database_manager.entities.Infirmier;
import app.database_manager.entities.Malade;
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

    // les ble-ta
    private HashMap<Integer, Service> services = new HashMap<>();
    private HashMap<Integer, Infirmier> infirmiers = new HashMap<>();
    private HashMap<Integer, Docteur> docteurs = new HashMap<>();
    private HashMap<Integer, Malade> malades = new HashMap<>();
    private HashMap<Integer, Employe> employes = new HashMap<>();
    private HashMap<Integer, Chambre> chambres = new HashMap<>();
    private HashMap<Integer, Hospitalisation> hospitalisations = new HashMap<>();
    private HashMap<Integer, Soin> soins = new HashMap<>();

    public void insert(String what, Object... args) {
        Utils.invokeFunction("add" + StringUtils.capitalize(what), this, args);
    }

    public void delete(String what, Object... args) {
        Utils.invokeFunction("supp" + StringUtils.capitalize(what), this, args);
    }

    // adders
    public void addService(Service s) {
        services.put(s.hashCode(), s);

        hospitalisations.putAll(s.getHospitalisations());
        chambres.putAll(s.getChambres());
        infirmiers.putAll(s.getInfirmiers());
    }

    public void addInfirmier(Infirmier i) {
        infirmiers.put(i.hashCode(), i);

        chambres.putAll(i.getChambres());
    }

    public void addDocteur(Docteur d) {
        docteurs.put(d.hashCode(), d);

        services.putAll(d.getServicesGeres());
        soins.putAll(d.getSoinsDonnes());
    }

    public void addMalade(Malade m) {
        malades.put(m.hashCode(), m);

        hospitalisations.putAll(m.getHospitalisations());
        soins.putAll(m.getSoins());
    }

    public void addEmploye(Employe e) {
        employes.put(e.hashCode(), e);
    }

    public void addChambre(Chambre c) {
        chambres.put(c.hashCode(), c);

        hospitalisations.putAll(c.getHospitalisations());

    }

    public void addHospitalisation(Hospitalisation h) {
        hospitalisations.put(h.hashCode(), h);
    }

    public void addSoin(Soin s) {
        soins.put(s.hashCode(), s);
    }

    // getters
    public HashMap<Integer, Service> getServices() {
        return services;
    }

    public void setServices(HashMap<Integer, Service> services) {
        this.services = services;
    }

    public HashMap<Integer, Infirmier> getInfirmiers() {
        return infirmiers;
    }

    public void setInfirmiers(HashMap<Integer, Infirmier> infirmiers) {
        this.infirmiers = infirmiers;
    }

    public HashMap<Integer, Docteur> getDocteurs() {
        return docteurs;
    }

    public HashMap<Integer, Malade> getMalades() {
        return malades;
    }

    public HashMap<Integer, Employe> getEmployes() {
        return employes;
    }

    public HashMap<Integer, Chambre> getChambres() {
        return chambres;
    }

    public HashMap<Integer, Hospitalisation> getHospitalisations() {
        return hospitalisations;
    }

    public HashMap<Integer, Soin> getSoins() {
        return soins;
    }

    // supper

    public void suppService(Service s) {

        services.remove(s.hashCode(), s);

        hospitalisations.remove(s.getHospitalisations().hashCode());

        chambres.remove(s.getChambres().hashCode());
        infirmiers.remove(s.getInfirmiers().hashCode());
    }

    public void suppInfirmier(Infirmier i) {
        infirmiers.remove(i.hashCode(), i);

        chambres.remove(i.getChambres().hashCode());
    }

    public void suppDocteur(Docteur d) {
        docteurs.remove(d.hashCode(), d);

        services.remove(d.getServicesGeres().hashCode());
        soins.remove(d.getSoinsDonnes().hashCode());
    }

    public void suppMalade(Malade m) {
        malades.remove(m.hashCode(), m);

        hospitalisations.remove(m.getHospitalisations().hashCode());
        soins.remove(m.getSoins().hashCode());
    }

    public void suppEmploye(Employe e) {
        employes.remove(e.hashCode(), e);
    }

    public void suppChambre(Chambre c) {
        chambres.remove(c.hashCode(), c);

        hospitalisations.remove(c.getHospitalisations().hashCode());

    }

    public void suppHospitalisation(Hospitalisation h) {
        hospitalisations.remove(h.hashCode(), h);
    }

    public void suppSoin(Soin s) {
        soins.remove(s.hashCode(), s);
    }
}