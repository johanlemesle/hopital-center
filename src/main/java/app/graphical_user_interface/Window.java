package app.graphical_user_interface;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Window extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -2641871501940426386L;
    private ActionSelector selection;
    private InputDigestor saisie;
    private ResultDisplayer resultats;

    public Window() {
        super("Gestionnaire de centre hospitallier");
        selection = new ActionSelector(this);
        saisie = new InputDigestor();
        resultats = new ResultDisplayer();

        // pour separer les panels
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, selection, saisie);
        JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, sp, resultats);

        sp.setResizeWeight(0.5);
        sp2.setResizeWeight(0.5);

        this.add(sp2); // pour ajouter dans la frame

        // trivial
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);

     
    }

    public void toggle(String mode) {
        switch (mode) {
        case ActionSelector.ADD_MODE:
            saisie.addMode();
            break;
        case ActionSelector.DELETE_MODE:
            break;
        case ActionSelector.UPDATE_MODE:
            break;
        case ActionSelector.QUERY_MODE:
            break;
        default:
            break;
        }
    }

}
