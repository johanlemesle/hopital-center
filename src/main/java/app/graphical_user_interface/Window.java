package app.graphical_user_interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class Window extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -2641871501940426386L;
    private JPanel selection, saisie, resultats; // les 3 panels
    private JTextArea textArea = new JTextArea(); // truc pour test

    public Window() {
        // affecter les panels
        selection = new JPanel(new GridLayout(0, 1));
        saisie = new JPanel();
        resultats = new JPanel();

        saisie.add(textArea); // pour le test

        // pour separer les panels
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, selection, saisie);
        JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, sp, resultats);
        sp.setResizeWeight(0.5);
        sp2.setResizeWeight(0.5);

        this.add(sp2); // pour ajouter dans la frame

        // boutons
        JRadioButton b_afficher = new JRadioButton("Rechercher");
        JRadioButton b_ajouter = new JRadioButton("Ajouter");
        JRadioButton b_supprimer = new JRadioButton("Supprimer");
        JRadioButton b_modifier = new JRadioButton("Modifier");

        // Création d'un groupe de boutons pour garantir qu'il y ai qu'un seul bouton
        // selectionné
        ButtonGroup groupB = new ButtonGroup();

        groupB.add(b_afficher);
        groupB.add(b_ajouter);
        groupB.add(b_supprimer);
        groupB.add(b_modifier);

        selection.add(b_afficher);
        selection.add(b_ajouter);
        selection.add(b_supprimer);
        selection.add(b_modifier);

        // ajouter des evenements quand les boutons sont selectionnés
        b_afficher.addActionListener(this);
        b_ajouter.addActionListener(this);
        b_supprimer.addActionListener(this);
        b_modifier.addActionListener(this);

        // trivial
        this.setTitle("Gestion du centre hospitalier");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // changer le texte
        textArea.setText(e.getActionCommand());
    }

    public void createMenu() {
        JPanel menu = new JPanel(new GridLayout(0, 1));

        JRadioButton b_afficher = new JRadioButton("Chercher");
        JRadioButton b_ajouter = new JRadioButton("Ajouter");
        JRadioButton b_supprimer = new JRadioButton("Supprimer");
        JRadioButton b_modifier = new JRadioButton("Modifier");

        // Création d'un groupe de boutons pour garantir qu'il y ai qu'un seul bouton
        // selectionné
        ButtonGroup groupB = new ButtonGroup();

        groupB.add(b_afficher);
        groupB.add(b_ajouter);
        groupB.add(b_supprimer);
        groupB.add(b_modifier);

        menu.add(b_afficher);
        menu.add(b_ajouter);
        menu.add(b_supprimer);
        menu.add(b_modifier);

        // this.setContentPane(menu);
        this.setVisible(true);

    }

    public void createResult() {
        JPanel result = new JPanel();

    }

    public void createSaisie_search() {
        JPanel saisie = new JPanel(new GridLayout(0, 0));

        // Main buttons for search
        JCheckBox bt1 = new JCheckBox("Docteur");
        JCheckBox bt2 = new JCheckBox("Employe");
        JCheckBox bt3 = new JCheckBox("Malade");
        JCheckBox bt4 = new JCheckBox("Chambre");
        JCheckBox bt5 = new JCheckBox("Service");
        JCheckBox bt6 = new JCheckBox("Hospitalisation");

        // What do you want to search
        JCheckBox s1 = new JCheckBox("Nom");
        JCheckBox s2 = new JCheckBox("Prenom");
        JCheckBox s3 = new JCheckBox("Tel");
        JCheckBox s4 = new JCheckBox("Adresse");
        JCheckBox s5 = new JCheckBox("Mutuelle");
        JCheckBox s6 = new JCheckBox("Numero chambre");
        JCheckBox s7 = new JCheckBox("Code service");

    }

}
