package app;

public class Infirmier extends Employe {
    public Infirmier(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
        // TODO Auto-generated constructor stub
    }

    private Service service;

    enum Rotation {
        Jour, Nuit
    };

    private Rotation rotation;
    private int salaire;
}
