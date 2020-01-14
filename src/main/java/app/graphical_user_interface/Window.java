package app.graphical_user_interface;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import app.App;
import app.database_manager.Utils;
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
        saisie = new InputDigestor(this);
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

    // fenetre principale
    public void updateInputPane() {
        switch (ACTION_MODE) {
        case ADD_MODE:
            saisie.addMode();
            break;
        case DELETE_MODE:
            break;
        case UPDATE_MODE:
            saisie.updateMode();
            break;
        case QUERY_MODE:
            // saisie.searchMode();
            resultats.displayQueryOutput(
                    Utils.get("malades{nom&prenom&adresse&soins{docteur{nom&prenom}}}", App.hopital));
            break;
        case REPORT_MODE:
            saisie.reportMode();
            break;
        default:
            break;
        }
    }

    public void displayQueryResult(String command) {
        resultats.displayQueryOutput(Utils.get(command, App.hopital));
    }

    public void displayTable(HashMap<?, ?> hm) {
        resultats.displayTable(hm);
    }

}
