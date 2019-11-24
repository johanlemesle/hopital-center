package app;

import java.util.ArrayList;

/**
 * Chambre
 */
public class Chambre {
    private int id;
    private Service service;
    private Infirmier surveillant;
    private ArrayList<Integer> lits;
    private Batiment batiment;

    public Batiment getBatiment() {
        return batiment;
    }
    public int getId(){
        return id;
    }
}