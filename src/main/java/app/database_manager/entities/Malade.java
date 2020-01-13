package app.database_manager.entities;

import java.util.HashMap;

import app.database_manager.Adresse;
import app.database_manager.Personne;

/**
 * Malade
 */
public class Malade extends Personne {

    /**
     *
     */
    private static final long serialVersionUID = 2147153715399729811L;

    private String mutuelle;

    // NUMERO, NOM, PRENOM, TEL, ADRESSE, MUTUELLE

    private HashMap<Integer, Hospitalisation> hospitalisations = new HashMap<>();
    private HashMap<Integer, Soin> soins = new HashMap<>();

    public String getMutuelle() {
        return mutuelle;
    }

    public void setMutuelle(String mutuelle) {
        this.mutuelle = mutuelle;
    }

    public HashMap<Integer, Hospitalisation> getHospitalisations() {
        return hospitalisations;
    }

    public void setHospitalisations(HashMap<Integer, Hospitalisation> hospitalisations) {
        this.hospitalisations = hospitalisations;
    }

    public HashMap<Integer, Soin> getSoins() {
        return soins;
    }

    // adders
    public void addHospitalisation(Hospitalisation h) {
        this.hospitalisations.put(h.hashCode(), h);
    }

    public void addSoin(Soin s) {
        this.soins.put(s.hashCode(), s);
    }

    public Malade(String nom, String prenom, String tel, Adresse adresse, String mutuelle,
            HashMap<Integer, Hospitalisation> hospitalisations, HashMap<Integer, Soin> soins) {
        super(nom, prenom, tel, adresse);
        this.mutuelle = mutuelle;

        if (hospitalisations != null)
            this.hospitalisations = hospitalisations;
        if (soins != null)
            this.soins = soins;
    }
}