package app.graphical_user_interface.helpers;

import java.lang.reflect.Field;

import javax.swing.JButton;

import app.database_manager.Utils;

/**
 * EntityInputHelper
 */
public class EntityInputHelper extends JButton {

    /**
     *
     */
    private static final long serialVersionUID = 9127452720622343546L;
    private EntityInputWindow eiw;
    private Field field;

    public EntityInputHelper(Field field) {
        super("Saisir " + Utils.normalizeCamelCase(field.getName()));
        this.field = field;
    }

    public Object getEntity() {
        eiw = new EntityInputWindow(field);
        eiw.setVisible(true);
        return eiw.getEntity();
    }

}