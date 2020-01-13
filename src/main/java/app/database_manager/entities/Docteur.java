package app.database_manager.entities;

import java.util.HashMap;

import app.database_manager.Adresse;
import app.database_manager.Specialite;;

/**
 * Docteur
 */
public class Docteur extends Employe {

    /**
     *
     */
    private static final long serialVersionUID = 5496168158738482984L;

    /**
     *
     */

    private Specialite specialite;
    // #NUMERO, SPECIALITE

    private HashMap<Integer, Service> servicesGeres = new HashMap<>();
    private HashMap<Integer, Soin> soinsDonnes = new HashMap<>();

    // getters & setters
    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public HashMap<Integer, Service> getServicesGeres() {
        return servicesGeres;
    }

    public void setServicesGeres(HashMap<Integer, Service> servicesGeres) {
        this.servicesGeres = servicesGeres;
    }

    public HashMap<Integer, Soin> getSoinsDonnes() {
        return soinsDonnes;
    }

    public void setSoinsDonnes(HashMap<Integer, Soin> soinsDonnes) {
        this.soinsDonnes = soinsDonnes;
    }

    // adders
    public void addServicesGere(Service s) {
        servicesGeres.put(s.hashCode(), s);
    }

    public void addSoin(Soin s) {
        soinsDonnes.put(s.hashCode(), s);
    }

    public Docteur(String nom, String prenom, String tel, Adresse adresse, Specialite specialite,
            HashMap<Integer, Service> servicesGeres, HashMap<Integer, Soin> soins) {
        super(nom, prenom, tel, adresse);
        this.specialite = specialite;

        if (servicesGeres != null)
            this.servicesGeres = servicesGeres;
        if (soins != null)
            this.soinsDonnes = soins;

    }

}