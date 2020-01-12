package app.graphical_user_interface.input_modes;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.EntityID;
import app.database_manager.Utils;
import app.graphical_user_interface.helpers.HintTextField;
import app.graphical_user_interface.helpers.JTextFieldLimit;

/**
 * Add
 */
public class Adder {

    /**
     *
     */
    private Object theEntity = null;
    private JTextField idTextField = new JTextField();

    private List<Pair<Class<?>, Component>> inputFields = new ArrayList<>();
    private Class<?> typeToAdd;
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

    public Adder(final JPanel workingPane, Class<?> typeToAdd) {

        this.typeToAdd = typeToAdd;
        workingPane.setBorder(BorderFactory.createTitledBorder("Saisie de : " + typeToAdd.getSimpleName()));
        List<Field> fields = Utils.extractFields(typeToAdd);
        workingPane.setLayout(new BoxLayout(workingPane, BoxLayout.Y_AXIS));

        {
            idTextField.setEditable(false);
            JPanel jPanel = new JPanel(new GridLayout(1, 2));

            jPanel.add(new JLabel("id de l'entite"));
            jPanel.add(idTextField);

            workingPane.add(jPanel);
        }

        for (final Field field : fields) {
            if (field.getType().isAssignableFrom(EntityID.class))
                continue;
            Component inputField = null;
            if (field.getType().isEnum()) {
                JComboBox<Object> jcb = new JComboBox<>();
                for (Object item : field.getType().getEnumConstants()) {
                    jcb.addItem(item);
                }
                inputField = jcb;
            } else if (field.getType().isArray()) {
                Object obj = TypeUtils.getArrayComponentType(field.getType());
            } else if (field.getType().isAssignableFrom(Character.class)) {
                JTextFieldLimit jtx = new JTextFieldLimit(1);
            } else {
                inputField = new HintTextField("Saisir " + Utils.normalizeCamelCase(field.getName()));
            }

            if (inputField.getClass().isAssignableFrom(JTextField.class)) {
                inputField.addKeyListener(new KeyListener() {

                    @Override
                    public void keyTyped(KeyEvent ke) {
                        try {
                            theEntity = buildEntity();
                            idTextField.setText(String.valueOf(theEntity.hashCode()));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                                | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent arg0) {

                    }

                    @Override
                    public void keyPressed(KeyEvent arg0) {

                    }
                });

            }
            if (inputField != null) {
                inputFields.add(Pair.of(field.getType(), inputField));

                JPanel jPanel = new JPanel(new GridLayout(1, 2));
                jPanel.add(new JLabel(Utils.normalizeCamelCase(field.getName())));
                jPanel.add(inputField);

                workingPane.add(jPanel);
            }
        }

    }

    public Object buildEntity()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object args[] = new Object[inputFields.size()];
        for (int i = 0; i < args.length; i++) {
            try {
                Pair<Class<?>, Component> pair = inputFields.get(i);
                if (pair.getLeft().isEnum()) {
                    args[i] = ((JComboBox<?>) pair.getRight()).getSelectedItem();
                } else if (pair.getLeft().isAssignableFrom(String.class)) {
                    args[i] = ((JTextField) pair.getRight()).getText();
                } else if (pair.getLeft().isAssignableFrom(Integer.class)) {
                    args[i] = NumberUtils.createInteger(((JTextField) pair.getRight()).getText());
                } else if (pair.getLeft().isAssignableFrom(Byte.class)) {
                    args[i] = Byte.parseByte(((JTextField) pair.getRight()).getText());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ConstructorUtils.invokeConstructor(typeToAdd, args);
    }

}