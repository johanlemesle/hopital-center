package app.database_manager.entities;

import java.util.HashMap;

/**
 * Chambre
 */
public class Chambre {
    private Integer noChambre;
    private Service service;
    private Infirmier surveillant;
    private Byte nbLits;

    // NO_CHAMBRE, #CODE_SERVICE, SURVEILLANT, NB_LITS
    private HashMap<Integer, Hospitalisation> hospitalisations;

    @Override
    public int hashCode() {
        return noChambre + service.hashCode();
    }

    // getters & setters
    public int getNoChambre() {
        return noChambre;
    }

    public void setNoChambre(int noChambre) {
        this.noChambre = noChambre;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Infirmier getSurveillant() {
        return surveillant;
    }

    public void setSurveillant(Infirmier surveillant) {
        this.surveillant = surveillant;
    }

    public byte getNbLits() {
        return nbLits;
    }

    public void setNbLits(byte nbLits) {
        this.nbLits = nbLits;
    }

    public HashMap<Integer, Hospitalisation> getHospitalisations() {
        return hospitalisations;
    }

    public void setHospitalisations(HashMap<Integer, Hospitalisation> hospitalisations) {
        this.hospitalisations = hospitalisations;
    }

    // adders
    public void addHospitalisation(Hospitalisation h) {
        this.hospitalisations.put(h.hashCode(), h);
    }

    public Chambre(Integer noChambre, Service service, Infirmier surveillant, Byte nbLits,
            HashMap<Integer, Hospitalisation> hospitalisations) {
        this.noChambre = noChambre;
        this.service = service;
        this.surveillant = surveillant;
        this.nbLits = nbLits;
        this.hospitalisations = hospitalisations;

        if (this.service != null)
            this.service.addChambre(this);
        if (this.surveillant != null)
            this.surveillant.addChambre(this);
    }
}