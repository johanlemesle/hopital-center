package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang3.reflect.FieldUtils;

import app.App;
import app.database_manager.Utils;
import app.graphical_user_interface.input_modes.EntityBuilder;

/**
 * EntityInputHelper
 */
public class EntityInputHelper extends JButton implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 9127452720622343546L;
    private Field field;
    private Object objet;
    private EntityInputWindow eiw = null;
    private EntityUpdateWindow euw = null;
    private Object value;
    private JButton jButton = new JButton("ok");
    private TablePicker tp;

    public EntityInputHelper(Field field) {
        super("Saisir " + Utils.normalizeCamelCase(field.getName()));
        this.field = field;
        jButton.addActionListener(this);
    }

    public EntityInputHelper(Field field, Object o) {
        super("Modifier " + Utils.normalizeCamelCase(field.getName()));
        this.field = field;
        this.objet = o;
        this.value = o;
        jButton.addActionListener(this);
    }

    public void buildEntity() {
        eiw = new EntityInputWindow(field);
        eiw.add(jButton, BorderLayout.SOUTH);

        if (field.getType().getCanonicalName().startsWith(EntityBuilder.ENTITIES_PACKAGES_PATH)) {
            JComboBox<String> jcbx = new JComboBox<>(
                    new String[] { "Selectionner depuis les entites existantes", "Creer une nouvelle entite" });
            jcbx.setSelectedIndex(1);
            jcbx.setActionCommand("set mode");
            jcbx.addActionListener(this);
            eiw.add(jcbx, BorderLayout.NORTH);
        }
        eiw.setVisible(true);
    }

    public void updateEntity() {
        if (objet == null) {
            buildEntity();
            return;
        }
        euw = new EntityUpdateWindow(objet);
        euw.add(jButton, BorderLayout.SOUTH);
        euw.setVisible(true);
    }

    public Object getEntity() {
        if (tp != null)
            return tp.getObject();
        return value;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("set mode")) {
            int opt = ((JComboBox<?>) e.getSource()).getSelectedIndex();
            if (opt == 0) {
                try {
                    String tableName = field.getType().getSimpleName().toLowerCase() + "s";
                    HashMap<?, ?> hm = (HashMap<Integer, Object>) FieldUtils.readField(App.hopital, tableName, true);
                    JPanel jp = new JPanel();
                    if (!hm.isEmpty()) {
                        Class<?> type = hm.entrySet().iterator().next().getValue().getClass();

                        Object laList[] = new Object[hm.size()];

                        int i = 0;
                        for (Object object : hm.values()) {
                            laList[i] = object;
                            ++i;
                        }

                        tp = new TablePicker(jp, type, laList);
                        tp.setVisible(true);
                        eiw.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "La liste selectionnée est vide", "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IllegalAccessException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else {
            System.out.println();
            if (eiw != null) {
                value = eiw.getEntity();
                eiw.setVisible(false);
            } else if (euw != null) {
                value = euw.getEntity();
                euw.setVisible(false);
            }
        }

    }
}