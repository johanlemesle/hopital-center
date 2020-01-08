package app.database_manager.entities;

import app.database_manager.EntitiyID;

/**
 * Chambre
 */
public class Chambre {
    private EntitiyID id;
    private Service service;
    private Infirmier surveillant;
    private byte nbLits;
    private int batiment;

    public int getBatiment() {
        return batiment;
    }

    public EntitiyID getId() {
        return id;
    }

    public void addLit() {
        nbLits++;
    }

    public void enleverLit() {
        if (nbLits > 0)
            nbLits--;
    }

    public void setService(Service s) {
        this.service = s;
    }

    public Service getService() {
        return service;
    }

    public void setSurveillant(Infirmier inf) {
        this.surveillant = inf;
    }

    public Infirmier getInfirmier() {
        return surveillant;
    }

}