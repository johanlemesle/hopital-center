package app.graphical_user_interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * ActionSelector
 */
public class ActionSelector extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -1349720485385763778L;

    enum ActionMode {
        ADD_MODE("Ajouter"), QUERY_MODE("Rechercher"), UPDATE_MODE("Modifier"), DELETE_MODE("Supprimer");

        private String action;

        ActionMode(String action) {
            this.action = action;
        }

        @Override
        public String toString() {
            return action;
        }
    };

    private Window parentWindow;

    public ActionSelector(Window parentWindow) {
        super(new GridLayout(0, 1));
        this.parentWindow = parentWindow;
        final JRadioButton add = new JRadioButton(ActionMode.ADD_MODE.toString()),
                update = new JRadioButton(ActionMode.UPDATE_MODE.toString()),
                delete = new JRadioButton(ActionMode.DELETE_MODE.toString()),
                query = new JRadioButton(ActionMode.QUERY_MODE.toString());

        ButtonGroup optionsGroup = new ButtonGroup();

        optionsGroup.add(add);
        optionsGroup.add(update);
        optionsGroup.add(delete);
        optionsGroup.add(query);

        this.add(add);
        this.add(update);
        this.add(delete);
        this.add(query);

        add.addActionListener(this);
        update.addActionListener(this);
        delete.addActionListener(this);
        query.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentWindow.toggleMode(e.getActionCommand());
    }
}