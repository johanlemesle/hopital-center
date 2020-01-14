package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.App;
import app.graphical_user_interface.ResultDisplayer;
import app.graphical_user_interface.input_modes.EntityBuilder;

/**
 * EntityInput
 */
public class EntityUpdateWindow extends JFrame implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -19874262486299749L;
    private EntityBuilder adder;
    private Object toUpdate;

    public EntityUpdateWindow(Object o) {
        super("Prompt");

        toUpdate = o;

        JButton okButton = new JButton("Ok");
        okButton.setActionCommand("ok");
        okButton.addActionListener(this);

        JPanel contentPane = new JPanel(new BorderLayout());

        this.add(contentPane);
        this.add(okButton, BorderLayout.SOUTH);

        if (o.getClass().isAssignableFrom(HashMap.class)) {
            HashMap<?, ?> hm = (HashMap<?, ?>) o;
            ResultDisplayer rd = new ResultDisplayer();
            rd.displayTable(hm);
            this.setContentPane(rd);
        } else {
            adder = new EntityBuilder(contentPane, o);
        }

        this.pack();
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            try {
                Object obj = adder.buildEntity();
                App.hopital.update(obj.getClass().getSimpleName(), toUpdate, obj);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | InstantiationException e1) {
                e1.printStackTrace();
            }
            this.setVisible(false);
        }
    }

}
