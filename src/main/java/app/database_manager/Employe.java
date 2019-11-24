package app.database_manager;

public class Employe extends Personne {
    public Employe(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    protected int id;
}
