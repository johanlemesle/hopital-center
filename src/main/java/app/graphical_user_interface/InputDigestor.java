package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import app.App;
import app.graphical_user_interface.helpers.ListDialog;
import app.graphical_user_interface.input_modes.Adder;

/**
 * InputDigester
 */
public class InputDigestor extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -4206706321166972386L;
    private final JPanel contentPane = new JPanel();
    private final JButton executeButton = new JButton("Executer");

    private Adder adder;

    public InputDigestor() {
        super(new BorderLayout());
        this.add(contentPane);
        this.add(executeButton, BorderLayout.SOUTH);
        executeButton.addActionListener(this);
    }

    public void addMode() {
        final HashMap<String, Class<?>> possibleVMap = new HashMap<>();
        final String possibleValues[] = new String[Adder.entities.length];
        int i = 0;
        for (final Class<?> cl : Adder.entities) {
            possibleValues[i] = cl.getSimpleName();
            possibleVMap.put(possibleValues[i], cl);
            i++;
        }
        String input = ListDialog.showDialog(this, this, "Selectionnez le type d'entité que vous souhaitez ajouter",
                "Selection entité", possibleValues, null, null);
        if (input != null) {
            contentPane.removeAll();
            contentPane.revalidate();
            adder = new Adder(contentPane, possibleVMap.get(input));
            contentPane.repaint();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (App.window.ACTION_MODE) {
        case ADD_MODE:
            try {
                adder.buildEntity();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | InstantiationException e1) {
                e1.printStackTrace();
            }
            break;
        default:
            break;
        }
    }
}
