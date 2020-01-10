package app.graphical_user_interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.EntitiyID;
import app.database_manager.Utils;
import app.graphical_user_interface.prompts.ListDialog;

/**
 * InputDigester
 */
public class InputDigestor extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4206706321166972386L;
    private JPanel contentPane = new JPanel();
    private JButton executeButton = new JButton("Executer");

    public static final Class<?>[] entities;
    static {
        String entitiesPackagePath = "app.database_manager.entities";
        Class<?>[] tmp = null;
        try {
            tmp = Utils.getClasses(entitiesPackagePath);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        entities = tmp;
    }

    public InputDigestor() {
        super(new BorderLayout());
        this.add(contentPane);
        this.add(executeButton, BorderLayout.SOUTH);
    }

    public void addMode() {
        String possibleValues[] = new String[entities.length];
        HashMap<String, Class<?>> possibleVMap = new HashMap<>();
        int i = 0;
        for (Class<?> cl : entities) {
            possibleValues[i] = cl.getSimpleName();
            possibleVMap.put(possibleValues[i], cl);
            i++;
        }
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
    }

    public void reset() {
        contentPane.removeAll();
        contentPane.revalidate();
    }

    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }
}
