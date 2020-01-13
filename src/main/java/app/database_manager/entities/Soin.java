package app.database_manager.entities;

import java.io.Serializable;

import app.database_manager.Date;

/**
 * Soin
 */
public class Soin implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8276439793583415702L;
    
    private Docteur docteur;
    private Malade malade;
    private Date date;

    // #NO_DOCTEUR, #NO_MALADE
    public Docteur getDocteur() {
        return docteur;
    }

    public void setDocteur(Docteur docteur) {
        this.docteur = docteur;
    }

    public Malade getPatient() {
        return malade;
    }

    public void setPatient(Malade patient) {
        this.malade = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Soin(Docteur docteur, Malade patient, Date date) {
        this.docteur = docteur;
        this.malade = patient;
        this.date = date;

        this.docteur.addSoin(this);
        this.malade.addSoin(this);
    }

}