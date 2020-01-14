package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;

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

    public EntityInputHelper(Field field) {
        super("Saisir " + Utils.normalizeCamelCase(field.getName()));
        this.field = field;
        jButton.addActionListener(this);
    }

    public EntityInputHelper(Field field, Object o) {
        super("Modifier " + Utils.normalizeCamelCase(field.getName()));
        this.field = field;
        this.objet = o;
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
        euw = new EntityUpdateWindow(objet);
        eiw.add(jButton, BorderLayout.SOUTH);
        euw.setVisible(true);
    }

    public Object getEntity() {
        return value;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("set mode")) {
            int opt = ((JComboBox<?>) e.getSource()).getSelectedIndex();
            if (opt == 0) {
                try {
                    String tableName = field.getType().getSimpleName().toLowerCase() + "s";
                    value = TablePicker
                            .pickObject((HashMap<Integer, Object>) FieldUtils.readField(App.hopital, tableName, true));

                } catch (IllegalAccessException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else {
            if (eiw != null) {
                value = eiw.getEntity();
                eiw.setVisible(false);
            } else if (euw != null) {
                euw.setVisible(false);
            }
        }

    }
}