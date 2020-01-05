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
    public static final String ADD_MODE = "Ajouter";
    public static final String QUERY_MODE = "Selectionner";
    public static final String UPDATE_MODE = "Modifier";
    public static final String DELETE_MODE = "Supprimer";

    private Window parentWindow;

    public ActionSelector(Window parentWindow) {
        super(new GridLayout(0, 1));
        this.parentWindow = parentWindow;
        final JRadioButton add = new JRadioButton(ADD_MODE), update = new JRadioButton(UPDATE_MODE),
                delete = new JRadioButton(DELETE_MODE), query = new JRadioButton(QUERY_MODE);

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
        parentWindow.toggle(e.getActionCommand());
    }
}