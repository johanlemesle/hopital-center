package app.database_manager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Batiment
 */
public class Batiment {
    private int id;
    private HashMap<Integer, Service> services;
    private HashMap<Integer, Chambre> chambres;
    private ArrayList<Hospitalisation> hospitalisations;

    public void addService(Service s) {
        services.put(s.getId(), s);
    }

    public Service getService(int id) {
        return services.get(id);
    }

    public void addChambre(Chambre c) {
        chambres.put(c.getId(), c);
    }

    public Chambre getChambre(int id) {
        return chambres.get(id);
    }

    public void addHospitalisation(Hospitalisation h) {
        hospitalisations.add(h);
    }

    public Hospitalisation getHospitalisation(int id) {
        return hospitalisations.get(id);
    }

    public int getId() {
        return id;
    }
}