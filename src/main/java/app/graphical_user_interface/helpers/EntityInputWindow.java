package app.graphical_user_interface.helpers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.lang3.reflect.FieldUtils;

import app.App;
import app.database_manager.Utils;
import app.graphical_user_interface.input_modes.EntityBuilder;

/**
 * EntityInput
 */
public class EntityInputWindow extends JFrame {

    /**
     *
     */

    private static final long serialVersionUID = -19874262486299749L;
    private EntityBuilder adder;
    private Object value = null;
    private ListInput lInput;

    public EntityInputWindow(Field field) {
        super("Prompt");

        JPanel contentPane = new JPanel(new BorderLayout());

        this.add(contentPane);

        if (field.getType().isAssignableFrom(HashMap.class)) {
            Class<?> cls = Utils.getTypeFromMap(field);
            lInput = new ListInput(cls);
            lInput.setVisible(true);
            this.setContentPane(lInput);
        } else {
            adder = new EntityBuilder(contentPane, field.getType());
        }

        this.pack();
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
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