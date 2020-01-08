package app.database_manager.entities;

import app.database_manager.Adresse;
import app.database_manager.Rotation;

public class Infirmier extends Employe {
    public Infirmier(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    public Infirmier(String nom, String prenom, String tel, Adresse adresse, Service service, Rotation rotation,
            double salaire) {
        super(nom, prenom, tel, adresse);
        this.service = service;
        this.rotation = rotation;
        this.salaire = salaire;
    }
    
    private Service service;
    private Rotation rotation;
    private double salaire;

    public double getSalaire() {
        return salaire;
    }

    public Service getService() {
        return service;
    }

    public Rotation getRotation() {
        return rotation;
    }

    
}
