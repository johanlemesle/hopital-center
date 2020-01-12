package app.database_manager.entities;

import java.util.HashMap;

import app.database_manager.Adresse;
import app.database_manager.Rotation;

public class Infirmier extends Employe {
    private Service service;
    private Rotation rotation;
    private Integer salaire;

    // #NUMERO, #CODE_SERVICE, ROTATION, SALAIRE

    private HashMap<Integer, Chambre> chambresSurveillees;

    // getters & setters
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }

    public HashMap<Integer, Chambre> getChambres() {
        return chambresSurveillees;
    }

    public void setChambres(HashMap<Integer, Chambre> chambres) {
        this.chambresSurveillees = chambres;
    }

    // adders
    public void addChambre(Chambre ch) {
        this.chambresSurveillees.put(ch.hashCode(), ch);
    }

    public Infirmier(String nom, String prenom, String tel, Adresse adresse, Service service, Rotation rotation,
            int salaire, HashMap<Integer, Chambre> chambres) {
        super(nom, prenom, tel, adresse);
        this.service = service;
        this.rotation = rotation;
        this.salaire = salaire;
        this.chambresSurveillees = chambres;

        this.service.addInfirmier(this);

    }

    public Infirmier(String nom, String prenom, String tel, Adresse adresse, Service service, Rotation rotation,
            Integer salaire, HashMap<Integer, Chambre> chambresSurveillees) {
        super(nom, prenom, tel, adresse);
        this.service = service;
        this.rotation = rotation;
        this.salaire = salaire;
        this.chambresSurveillees = chambresSurveillees;
    }

}
