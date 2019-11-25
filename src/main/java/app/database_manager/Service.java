package app.database_manager;

public class Service {
    private int id;
    private String nom;
    private Batiment batiment;
    private Docteur directeur;

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Batiment getBatiment() {
        return this.batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Docteur getDirecteur() {
        return this.directeur;
    }

    public void setDirecteur(Docteur directeur) {
        this.directeur = directeur;
    }

}