package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Utils;

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
        final String possibleValues[] = new String[Adder.entities.length];
        final HashMap<String, Class<?>> possibleVMap = new HashMap<>();
        int i = 0;
        for (final Class<?> cl : Adder.entities) {
            possibleValues[i] = cl.getSimpleName();
            possibleVMap.put(possibleValues[i], cl);
            i++;
        }
<<<<<<< HEAD
        adder = new Adder(contentPane,
                possibleVMap.get(
                        ListDialog.showDialog(this, this, "Selectionnez le type d'entité que vous souhaitez ajouter",
                                "Selection entité", possibleValues, null, null)));

||||||| merged common ancestors
        String value = ListDialog.showDialog(this, this, "Selectionnez le type d'entité que vous souhaitez ajouter",
                "Selection entité", possibleValues, null, null);
        List<Pair<Field, Class<?>>> fieldsNames = Utils.extractFieldNames(possibleVMap.get(value));
        contentPane.setLayout(new GridLayout(5, fieldsNames.size() / 5));
        for (final Pair<Field, Class<?>> pair : fieldsNames) {
            final JPanel jPanel = new JPanel(new GridLayout(1, 2));
            final JLabel label = new JLabel(pair.getLeft().getName());
            jPanel.add(label);
            jPanel.add(new JTextField());
            contentPane.add(jPanel);
        }
=======
        String value = ListDialog.showDialog(this, this, "Selectionnez le type d'entité que vous souhaitez ajouter",
                "Selection entité", possibleValues, null, null);
        List<Pair<Field, Class<?>>> fieldsNames = Utils.extractFieldNames(possibleVMap.get(value));
        contentPane.setLayout(new GridLayout(5, fieldsNames.size() / 5));
        for (final Pair<Field, Class<?>> pair : fieldsNames) {
            final JPanel jPanel = new JPanel(new GridLayout(1, 2));
            final JLabel label = new JLabel(pair.getLeft().getName());
            jPanel.add(label);
            if (pair.getRight().isArray()) {

            } else if (pair.getRight().isAssignableFrom(EntitiyID.class)) {

            } else if (pair.getRight().isAssignableFrom(Map.class)) {
                
            }
            jPanel.add(new JTextField());
            contentPane.add(jPanel);
        }
>>>>>>> d75fcea16b06c7b84fe7dd42b1fda154f2fa59de
    }

    public void reset() {
        adder = null;
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
