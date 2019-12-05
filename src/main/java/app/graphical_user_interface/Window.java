package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

public class Window extends JFrame 
{
    private static final long serialVersionUID = 1L;
    
    //Création d'une liste de Jpanel identifiable par un nom 
    private HashMap <String,JPanel> panels = new HashMap<>();

    public Window ()
    {
        this.setTitle("Gestion du centre hospitalier");
        this.setSize(600, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setVisible(true);
    }
    
    public void createJPanel (String name , JPanel jpanel)
    {
        panels.put(name, jpanel);
        this.setContentPane(jpanel);
    }

    public void createMenu ()
    {
        JPanel menu = new JPanel(new GridLayout(0,1));

        JRadioButton b_afficher = new JRadioButton("Afficher");
        JRadioButton b_ajouter = new JRadioButton("Ajouter");
        JRadioButton b_supprimer = new JRadioButton("Supprimer");
        JRadioButton b_modifier = new JRadioButton("Modifier");

        //Création d'un groupe de boutons pour garantir qu'il y ai qu'un seul bouton selectionné 
        ButtonGroup groupB = new ButtonGroup();

        groupB.add(b_afficher);
        groupB.add(b_ajouter);
        groupB.add(b_supprimer);
        groupB.add(b_modifier);

        menu.add(b_afficher);
        menu.add(b_ajouter);
        menu.add(b_supprimer);
        menu.add(b_modifier);
        
        //this.setContentPane(menu);
        this.setVisible(true);
        
    }

    public void createResult ()
    {
        JPanel result = new JPanel();
        
    }

    public void createSaisie_search()
    {
        JPanel saisie = new JPanel(new GridLayout(0,0));
        
        //Main buttons for search 
        JCheckBox bt1 = new JCheckBox("Docteur");
        JCheckBox bt2 = new JCheckBox("Employe");
        JCheckBox bt3 = new JCheckBox("Malade");
        JCheckBox bt4 = new JCheckBox("Chambre");
        JCheckBox bt5 = new JCheckBox("Service");
        JCheckBox bt6 = new JCheckBox("Hospitalisation");

        //What do you want to search 
        JCheckBox s1 = new JCheckBox("Nom");
        JCheckBox s2 = new JCheckBox("Prenom");
        JCheckBox s3 = new JCheckBox("Tel");
        JCheckBox s4 = new JCheckBox("Adresse");
        JCheckBox s5 = new JCheckBox("Mutuelle");
        JCheckBox s6 = new JCheckBox("Numero chambre");
        JCheckBox s7 = new JCheckBox("Code service");

        
    }

    

}

