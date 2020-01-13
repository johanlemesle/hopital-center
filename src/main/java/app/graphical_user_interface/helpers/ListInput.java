package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import app.graphical_user_interface.input_modes.Adder;

/**
 * ListInput
 */
public class ListInput extends JPanel implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -1242654682815548515L;

    private ListDisplayer listDisplayer;
    private Adder adder;

    public ListInput(Class<?> type) {
        super(new BorderLayout());

        listDisplayer = new ListDisplayer(type);

        JPanel inputPanel = new JPanel();

        listDisplayer.setVisible(true);

        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, listDisplayer, inputPanel);
        jsp.setResizeWeight(0.5);
        this.add(jsp);

        {
            JButton button = new JButton("Ajouter");
            button.addActionListener(this);
            this.add(button, BorderLayout.SOUTH);
        }
        adder = new Adder(inputPanel, type);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            listDisplayer.addRow(adder.buildEntity());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
}