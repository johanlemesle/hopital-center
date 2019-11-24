package app;

/**
 * Malade
 */
public class Patient extends Personne {
    public Patient(String nom, String prenom, String tel, Adresse adresse) {
        super(nom, prenom, tel, adresse);
    }

    private Mutuelle mutuelle;

}