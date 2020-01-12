package app.database_manager.entities;

/**
 * Soin
 */
public class Soin {

    private Docteur docteur;
    private Patient patient;

    public Docteur getDocteur() {
        return docteur;
    }

    public void setDocteur(Docteur docteur) {
        this.docteur = docteur;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Soin(Docteur docteur, Patient patient) {
        this.docteur = docteur;
        this.patient = patient;
    }

}