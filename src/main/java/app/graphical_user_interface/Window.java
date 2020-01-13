package app.graphical_user_interface;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import app.graphical_user_interface.ActionSelector.ActionMode;

public class Window extends JFrame {

    /**
     *
     */
    public ActionMode ACTION_MODE;

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
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.setVisible(true);

    }

    public void toggleMode(String mode) {
        for (ActionMode actionMode : ActionMode.values()) {
            if (actionMode.toString().equals(mode)) {
                ACTION_MODE = actionMode;
                break;
            }
        }
        updateInputPane();
    }

    public void updateInputPane() {
        switch (ACTION_MODE) {
        case ADD_MODE:
            saisie.addMode();
            break;
        case DELETE_MODE:
            break;
        case UPDATE_MODE:
            break;
        case QUERY_MODE:
            break;
        default:
            break;
        }
    }

}
