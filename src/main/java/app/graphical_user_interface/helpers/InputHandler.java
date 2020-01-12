package app.graphical_user_interface.helpers;

import java.awt.Component;
import java.awt.GridLayout;
import java.lang.reflect.Field;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * InputHandler
 */
public class InputHandler extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 6167551860222236504L;
    private Class<?> typeClass;
    private Component inputField;

    public InputHandler(Field field) {
        super(new GridLayout(1, 2));

        this.typeClass = field.getType();

        if (field.getType().isEnum()) {
            JComboBox<Object> jcb = new JComboBox<>();
            for (Object item : field.getType().getEnumConstants()) {
                jcb.addItem(item);
            }
            inputField = jcb;
        } else {
            inputField = new HintTextField("Saisir " + field.getName());
        }
        if (inputField != null) {
            this.add(new JLabel(field.getName()));
            this.add(inputField);
        }
    }

    public Object retrieveInputFieldContents() {
        if (typeClass.isEnum()) {
            return String.valueOf(((JComboBox<?>) inputField).getSelectedItem());
        } else if (typeClass.isAssignableFrom(String.class)) {
            return ((JTextField) inputField).getText();
        } else if (typeClass.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(((JTextField) inputField).getText());
        }
        return null;
    }
}