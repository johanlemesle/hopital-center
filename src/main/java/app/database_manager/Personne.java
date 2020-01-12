package app.database_manager;

import org.apache.commons.lang3.StringUtils;

/**
 * Personne
 */
public class Personne {

    protected EntityID numero;
    protected String nom, prenom, tel;
    protected Adresse adresse;

    public Personne(String nom, String prenom, String tel, Adresse adresse) {
        this.nom = nom.toUpperCase();
        this.prenom = StringUtils.capitalize(prenom);
        this.tel = tel.replace(" ", "");
        this.adresse = adresse;

        this.numero = new EntityID(hashCode());
    }

    @Override
    public int hashCode() {
        return (nom + prenom + tel).hashCode();
    }

    public EntityID getNumero() {
        return numero;
    }

    public void setNumero(EntityID numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

}
