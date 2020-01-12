package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import app.Hopital;
import app.database_manager.Utils;
import app.graphical_user_interface.helpers.ListDialog;
import app.graphical_user_interface.helpers.Toto;
import app.graphical_user_interface.input_modes.Adder;
import app.graphical_user_interface.input_modes.QueryMode;

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
    private QueryMode query;

    public InputDigestor() {
        super(new BorderLayout());
        this.add(contentPane);
        this.add(executeButton, BorderLayout.SOUTH);
        executeButton.addActionListener(this);
    }

    public void addMode() {
        final String possibleValues[] = new String[Adder.entities.length];
        final HashMap<String, Class<?>> possibleVMap = new HashMap<>();
        int i = 0;
        for (final Class<?> cl : Adder.entities) {
            possibleValues[i] = cl.getSimpleName();
            possibleVMap.put(possibleValues[i], cl);
            i++;
        }
        adder = new Adder(contentPane,
                possibleVMap.get(
                        ListDialog.showDialog(this, this, "Selectionnez le type d'entité que vous souhaitez ajouter",
                                "Selection entité", possibleValues, null, null)));

    }

    public void queryMode() {
        // query = new QueryMode(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        for (Field field : Utils.extractFieldNames(Hopital.class)) {
            Class<?> cls;
            if (field.getType().isAssignableFrom(HashMap.class)) {
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                cls = (Class<?>) pt.getActualTypeArguments()[1];
            } else {
                cls = field.getType();
            }
            contentPane.add(new Toto(new JCheckBox(), cls, field.getName()));
        }
    }

    public void reset() {
        adder = null;
        query = null;
        contentPane.removeAll();
        contentPane.revalidate();
    }

    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (adder != null) {
            try {
                adder.buildEntity();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | InstantiationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
