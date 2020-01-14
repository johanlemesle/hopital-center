package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.graphical_user_interface.input_modes.EntityBuilder;

/**
 * EntityUpdateWindow
 */
public class EntityUpdateWindow extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -4310958893306926653L;

    EntityBuilder builder;

    public EntityUpdateWindow(Object e) {
        super("Mise a jour lol");
        JButton button = new JButton("Ok");
        JPanel contentPane = new JPanel(new BorderLayout());
        this.add(button);
        this.add(contentPane);
        builder = new EntityBuilder(contentPane, e);
        this.setSize(300, 300);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}