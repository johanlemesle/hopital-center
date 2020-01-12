package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import app.graphical_user_interface.input_modes.Adder;

/**
 * EntityInput
 */
public class EntityInputWindow extends JInternalFrame implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -19874262486299749L;
    private Adder adder;
    private Object value = null;

    public EntityInputWindow(Class<?> type, JPanel parent) {
        super("Saisie de " + type.getSimpleName());
        JButton okButton = new JButton("Ok");
        okButton.setActionCommand("ok");
        okButton.addActionListener(this);
        {
            JPanel contentPane = new JPanel(new BorderLayout());
            this.add(contentPane);
            this.add(okButton, BorderLayout.SOUTH);
            adder = new Adder(contentPane, type);
        }

        parent.add(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            value = adder.buildEntity();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        this.setVisible(false);
    }

    public Object getEntity() {
        return value;

    }
}