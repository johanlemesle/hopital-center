package app;

import java.util.HashMap;

/**
 * Batiment
 */
public class Batiment {
    private int id;
    private HashMap<Integer, Service> services;
    private HashMap<Integer, Chambre> chambres;

    public void ajouterService(Service s) {
        services.put(s.getId(), s);
    }

    public void ajouterChambre(Chambre c) {
        chambres.put(c.getId(), c);
    }

    public int getId() {
        return id;
    }
}