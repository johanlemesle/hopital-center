package app.database_manager;

/**
 * Chambre
 */
public class Chambre {
    private int id;
    private Service service;
    private Infirmier surveillant;
    private byte nbLits;
    private Batiment batiment;

    public Batiment getBatiment() {
        return batiment;
    }

    public int getId() {
        return id;
    }

    public void ajouterLit() {
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