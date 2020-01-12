package app.graphical_user_interface.input_modes;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.Utils;
import app.graphical_user_interface.input_modes.Adder;

/**
 * QueryMode
 */
public class QueryMode implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -1968404454403827274L;

    public static Class<?>[] first_entites;
    // private HashMap<JLabel, Class<?>> hash_lab = new HashMap<>();

    private final HashMap<JCheckBox, Class<?>> checkBoxEntity = new HashMap<>();
    public static Class<?> courant;
    private JPanel panel;

    public QueryMode(final JPanel workingPane) {
        panel = workingPane;
        first_entites = Adder.entities;
        for (int i = 0; i < first_entites.length; i++) {
            final JPanel jPanel = new JPanel(new GridLayout(1, 2));
            final JLabel jl = new JLabel(first_entites[i].getSimpleName());
            final JCheckBox jcb = new JCheckBox();
            jcb.addActionListener(this);
            // Pair<JCheckBox, Class<?>> p = Pair.of(jcb, first_entites[i]);
            jPanel.add(jcb);
            jPanel.add(jl);
            workingPane.add(jPanel);
            checkBoxEntity.put(jcb, first_entites[i]);
        }

        // for (int k = 0; k < list_pair.size(); k++) {
        // if (list_pair.get(k).getLeft().isSelected()) {

        // List<Field> fieldsnem = Utils.extractFieldNames(list_pair.get(k).getRight());

        // for (int j = 0; j < fieldsnem.size(); j++) {
        // JLabel jl2 = new JLabel(fieldsnem.get(j).getName());
        // JCheckBox jcb2 = new JCheckBox();
        // JPanel jPanel2 = new JPanel(new GridLayout(1, 2));
        // jPanel2.add(jl2);
        // jPanel2.add(jcb2);
        // workingPane.add(jPanel2);
        // }

        // }

        // }

        // final List<Field> fieldsNames = Utils.extractFieldNames(typeToAdd);
        // workingPane.setLayout(new GridLayout(5, fieldsNames.size() / 5));

    }

    public void Parsing(final List<Field> f, final JPanel p) {
        for (int j = 0; j < f.size(); j++) {
            final JLabel jl2 = new JLabel(f.get(j).getName());
            final JCheckBox jcb2 = new JCheckBox();
            final JPanel jPanel2 = new JPanel(new GridLayout(1, 2));
            jPanel2.add(jl2);
            jPanel2.add(jcb2);
            p.add(jPanel2);
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // TODO Auto-generated method stub

        System.out.println("la classe : " + checkBoxEntity.get(e.getSource()));
        final JCheckBox temp = (JCheckBox) e.getSource();
        if (temp.isSelected()) {
            System.out.println("selected " + checkBoxEntity.get(temp).getSimpleName());
            courant = checkBoxEntity.get(temp);
            System.out.println("courant : " + courant.getSimpleName());
            System.out.println("courantQuerry : " + courant.getSimpleName());
            final List<Field> fieldsnem = Utils.extractFieldNames(courant);
            if (!fieldsnem.isEmpty()) {
                for (int j = 0; j < fieldsnem.size(); j++) {
                    final JLabel jl2 = new JLabel(fieldsnem.get(j).getName());
                    final JCheckBox jcb2 = new JCheckBox();
                    final JPanel jPanel2 = new JPanel(new GridLayout(1, 2));
                    jPanel2.add(jl2);
                    jPanel2.add(jcb2);
                    panel.add(jPanel2);
                }
            }

            // List<Field> fieldsnem = Utils.extractFieldNames(checkBoxEntity.get(temp));

        } else {
            System.out.println("unselected " + checkBoxEntity.get(temp).getSimpleName());
        }

    }

}