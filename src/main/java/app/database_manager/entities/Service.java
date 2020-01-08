package app.database_manager.entities;

import app.database_manager.EntitiyID;

public class Service {
    private EntitiyID id;
    private String nom;
    private int batiment;
    private Docteur directeur;

    public EntitiyID getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getBatiment() {
        return this.batiment;
    }

    public void setBatiment(int batiment) {
        this.batiment = batiment;
    }

    public Docteur getDirecteur() {
        return this.directeur;
    }

    public void setDirecteur(Docteur directeur) {
        this.directeur = directeur;
    }

}