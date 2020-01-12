package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.graphical_user_interface.input_modes.Adder;

/**
 * AddMapButton
 */
public class HashMapInput extends JButton implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 929372023748744910L;
    Class<?> valueType;
    Adder adder;
    JFrame frame;

    public HashMapInput(Class<?> value) {
        this.valueType = value;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public HashMap<Integer, Object> buildMap() {
        HashMap<Integer, Object> hm = new HashMap<>();

        frame = new JFrame("Saisie de " + valueType.getSimpleName());

        JButton ok = new JButton("Ok");
        ok.setActionCommand("ok");
        ok.addActionListener(this);
        JPanel adderPane = new JPanel();
        adder = new Adder(adderPane, valueType);

        frame.add(adderPane);
        frame.add(ok, BorderLayout.SOUTH);
        frame.setVisible(true);
        return hm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        frame.setVisible(false);
    }
}
