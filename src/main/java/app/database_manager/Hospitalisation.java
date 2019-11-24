package app.database_manager;

/**
 * Hospitalisation
 */
public class Hospitalisation {
    private Patient patient;
    private Service service;
    private Chambre chambre;
    private int lit;

    public Patient getPatient() {
        return patient;
    }

    public Service getService() {
        return service;
    }

    public int getLit() {
        return lit;
    }

    public Chambre getChambre() {
        return chambre;
    }
}