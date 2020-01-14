package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import app.graphical_user_interface.input_modes.EntityBuilder;

/**
 * ListInput
 */
public class ListInput extends JPanel implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -1242654682815548515L;

    private TableDeleter listDisplayer;
    private EntityBuilder adder;

    public ListInput(Class<?> type) {
        this(type, new Object[] {});
    }

    public ListInput(Class<?> type, Object initial[]) {
        super(new BorderLayout());

        JPanel listDisplayerPane = new JPanel();
        listDisplayer = new TableDeleter(listDisplayerPane, type, initial);

        JPanel inputPanel = new JPanel();
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, listDisplayerPane, inputPanel);
        jsp.setResizeWeight(0.5);
        this.add(jsp);

        {
            JButton button = new JButton("Ajouter");
            button.addActionListener(this);
            inputPanel.add(button);
        }
        adder = new EntityBuilder(inputPanel, type);
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