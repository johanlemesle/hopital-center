package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.reflect.FieldUtils;

import app.App;
import app.database_manager.Utils;
import app.graphical_user_interface.ResultDisplayer;
import app.graphical_user_interface.input_modes.EntityBuilder;

/**
 * EntityInput
 */
public class EntityInputWindow extends JFrame implements ActionListener {

    /**
     *
     */

    private static final long serialVersionUID = -19874262486299749L;
    private EntityBuilder adder;
    private Object value = null;
    private String tableName = "";
    private ListInput lInput;

    public EntityInputWindow(Field field) {
        super("Prompt");

        JPanel contentPane = new JPanel(new BorderLayout());

        this.add(contentPane);

        if (field.getType().isAssignableFrom(HashMap.class)) {
            Class<?> cls = Utils.getTypeFromMap(field);
            lInput.setVisible(true);
            this.setContentPane(lInput);
            lInput = new ListInput(cls);
        } else {
            adder = new EntityBuilder(contentPane, field.getType());
        }

        this.pack();
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int opt = ((JComboBox<?>) e.getSource()).getSelectedIndex();
        if (opt == 0) {
            try {
                value = TablePicker
                        .pickObject((HashMap<Integer, Object>) FieldUtils.readField(App.hopital, tableName, true));
                this.setVisible(false);
                adder = null;
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public Object getEntity() {
        if (adder != null)
            try {
                return adder.buildEntity();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                    | InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        else if (value != null)
            return value;
        else if (lInput != null)
            return lInput.getValue();
        return null;
    }
}