package app.entities;

import app.enums.Rotation;

public class Infirmier extends Employe {
    public Infirmier(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    private Service service;
    private Rotation rotation;
    private int salaire;

    public int getSalaire() {
        return salaire;
    }

    public Service getService() {
        return service;
    }

    public Rotation getRotation() {
        return rotation;
    }
}
