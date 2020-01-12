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

}
