package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.database_manager.Utils;
import app.graphical_user_interface.input_modes.Adder;

/**
 * EntityInput
 */
public class EntityUpdateWindow extends JFrame implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -19874262486299749L;
    private Adder adder;
    private Object value = null;

    public EntityUpdateWindow(Field field) {
        super("Prompt");

        JButton okButton = new JButton("Ok");
        okButton.setActionCommand("ok");
        okButton.addActionListener(this);

        JPanel contentPane = new JPanel(new BorderLayout());

        this.add(contentPane);
        this.add(okButton, BorderLayout.SOUTH);

        if (field.getType().isAssignableFrom(HashMap.class)) {
            ListInput lInput = new ListInput(Utils.getTypeFromMap(field));
            lInput.setVisible(true);
            this.setContentPane(lInput);
        } else {
            adder = new Adder(contentPane, field.getType());
        }

        this.pack();
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            try {
                value = adder.buildEntity();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | InstantiationException e1) {
                e1.printStackTrace();
            }
            this.setVisible(false);
        }
    }

    public Object getEntity() {
        return value;
    }
}