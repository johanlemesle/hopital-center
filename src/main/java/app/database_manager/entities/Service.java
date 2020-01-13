package app.database_manager.entities;

import java.util.HashMap;

public class Service {
    private String code;
    private String nom;
    private Character batiment;
    private Docteur directeur;

    // CODE, NOM, BATIMENT, DIRECTEUR
    private HashMap<Integer, Chambre> chambres = new HashMap<>();
    private HashMap<Integer, Hospitalisation> hospitalisations = new HashMap<>();
    private HashMap<Integer, Infirmier> infirmiers = new HashMap<>();

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public char getBatiment() {
        return batiment;
    }

    public void setBatiment(char batiment) {
        this.batiment = batiment;
    }

    public Docteur getDirecteur() {
        return directeur;
    }

    public void setDirecteur(Docteur directeur) {
        this.directeur = directeur;
    }

    public HashMap<Integer, Chambre> getChambres() {
        return chambres;
    }

    public void setChambres(HashMap<Integer, Chambre> chambres) {
        this.chambres = chambres;
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

    public void addChambre(Chambre c) {
        this.chambres.put(c.hashCode(), c);
    }

    public void addInfirmier(Infirmier i) {
        this.infirmiers.put(i.hashCode(), i);
    }

    public void setBatiment(Character batiment) {
        this.batiment = batiment;
    }

    public HashMap<Integer, Infirmier> getInfirmiers() {
        return infirmiers;
    }

    public void setInfirmiers(HashMap<Integer, Infirmier> infirmiers) {
        this.infirmiers = infirmiers;
    }

    public Service(String code, String nom, char batiment, Docteur directeur, HashMap<Integer, Chambre> chambres,
            HashMap<Integer, Hospitalisation> hospitalisations, HashMap<Integer, Infirmier> infirmiers) {
        this.code = code;
        this.nom = nom;
        this.batiment = batiment;
        this.directeur = directeur;
        this.chambres = chambres;
        this.hospitalisations = hospitalisations;
        this.infirmiers = infirmiers;

        if (this.directeur != null)
            this.directeur.addServicesGere(this);
    }

}