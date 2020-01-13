package app.database_manager.entities;

import java.util.HashMap;

import app.database_manager.Adresse;
import app.database_manager.Rotation;

public class Infirmier extends Employe {
    private Service service;
    private Rotation rotation;
    private Double salaire;

    // #NUMERO, #CODE_SERVICE, ROTATION, SALAIRE

    private HashMap<Integer, Chambre> chambresSurveillees = new HashMap<>();

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

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
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
            Double salaire, HashMap<Integer, Chambre> chambresSurveillees) {
        super(nom, prenom, tel, adresse);
        this.service = service;
        this.rotation = rotation;
        this.salaire = salaire;

        if (chambresSurveillees != null)
            this.chambresSurveillees = chambresSurveillees;

        if (this.service != null)
            this.service.addInfirmier(this);
    }

}
