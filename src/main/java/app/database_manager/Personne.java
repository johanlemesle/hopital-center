package app.database_manager;

/**
 * Personne
 */
public class Personne {

    protected String nom, prenom, tel;
    protected Adresse adresse;

    public Personne(String nom, String prenom, String tel, Adresse adresse) {
        this.nom = nom.toUpperCase();
        this.prenom = prenom;
        this.tel = tel.replace(" ", "");
        this.adresse = adresse;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Adresse getAdresse() {
        return this.adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public int hashCode() {
        return (nom + prenom + tel).hashCode();
    }

}