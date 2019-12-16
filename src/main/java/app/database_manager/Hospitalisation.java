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

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public void setLit(int lit) {
        this.lit = lit;
    }

    public Hospitalisation(Patient patient, Service service, Chambre chambre, int lit) {
        this.patient = patient;
        this.service = service;
        this.chambre = chambre;
        this.lit = lit;
    }
}