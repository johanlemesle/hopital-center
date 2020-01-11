package app.database_manager.entities;

import java.util.Date;

/**
 * Hospitalisation
 */
public class Hospitalisation {
    private Malade malade;
    private Service service;
    private Chambre chambre;
    private Integer lit;

    // NO_MALADE, #CODE_SERVICE, #NO_CHAMBRE, LIT

    private Date dateEntree, dateSortie;

    @Override
    public int hashCode() {
        return malade.hashCode() + service.hashCode() + chambre.hashCode() + lit;
    }

    public Malade getMalade() {
        return malade;
    }

    public void setMalade(Malade malade) {
        this.malade = malade;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public int getLit() {
        return lit;
    }

    public void setLit(int lit) {
        this.lit = lit;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Hospitalisation(Malade malade, Service service, Chambre chambre, int lit, Date dateEntree, Date dateSortie) {
        this.malade = malade;
        this.service = service;
        this.chambre = chambre;
        this.lit = lit;
        this.dateEntree = dateEntree;
        this.dateSortie = dateSortie;

        this.service.addHospitalisation(this);
        this.chambre.addHospitalisation(this);
        this.malade.addHospitalisation(this);
    }

}