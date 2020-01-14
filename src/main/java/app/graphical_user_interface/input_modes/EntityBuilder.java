package app.graphical_user_interface.input_modes;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;

import app.database_manager.EntityID;
import app.database_manager.Utils;
import app.graphical_user_interface.helpers.EntityInputHelper;
import app.graphical_user_interface.helpers.HintTextField;
import app.graphical_user_interface.helpers.JTextFieldLimit;

/**
 * Add
 */
public class EntityBuilder implements ActionListener {

    /**
     *
     */

    private Object theEntity = null;
    private JTextField idTextField = new JTextField();

    private List<Pair<Class<?>, Component>> inputFields = new ArrayList<>();
    private Class<?> typeToAdd;
    public static final Class<?>[] ENTITIES;
    public static final String ENTITIES_PACKAGES_PATH = "app.database_manager.entities";

    static {
        Class<?>[] tmp = null;
        try {
            tmp = Utils.getClasses(ENTITIES_PACKAGES_PATH);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ENTITIES = tmp;

    }

    /*
     * public EntityBuilder(final JPanel workingPane, Object e) {
     * 
     * this.typeToAdd = e.getClass();
     * workingPane.setBorder(BorderFactory.createTitledBorder("Saisie de : " +
     * typeToAdd.getSimpleName())); workingPane.setLayout(new BoxLayout(workingPane,
     * BoxLayout.Y_AXIS)); if
     * (typeToAdd.getCanonicalName().startsWith(EntityBuilder.ENTITIES_PACKAGES_PATH
     * )) { idTextField.setEditable(false); JPanel jPanel = new JPanel(new
     * GridLayout(1, 2));
     * 
     * jPanel.add(new JLabel("id de l'entite")); jPanel.add(idTextField);
     * 
     * workingPane.add(jPanel); } int i = 0; for (Field field :
     * Utils.extractFields(e.getClass())) { Component inputField = null; try {
     * Object obj = FieldUtils.readField(field, e, true); if
     * (field.getType().isEnum()) { JComboBox<Object> jcb = new JComboBox<>(); for
     * (Object item : field.getType().getEnumConstants()) { jcb.addItem(item); }
     * inputField = jcb; } else{ inputField = new JTextField(obj.toString()); }
     * inputFields.add(i, Pair.of(field.getType(), inputField)); JPanel jPanel = new
     * JPanel(new GridLayout(1, 2)); jPanel.add(new
     * JLabel(Utils.normalizeCamelCase(field.getName()))); jPanel.add(inputField);
     * 
     * workingPane.add(jPanel); i++; } catch (IllegalAccessException e1) { // TODO
     * Auto-generated catch block e1.printStackTrace(); } } }
     */
    public EntityBuilder(final JPanel workingPane, Object e) {
        
        this.typeToAdd = e.getClass();
        workingPane.setBorder(BorderFactory.createTitledBorder("Saisie de : " + typeToAdd.getSimpleName()));
        List<Field> fields = Utils.extractFields(typeToAdd);
        workingPane.setLayout(new BoxLayout(workingPane, BoxLayout.Y_AXIS));
        if (typeToAdd.getCanonicalName().startsWith(EntityBuilder.ENTITIES_PACKAGES_PATH)) {
            idTextField.setEditable(false);
            JPanel jPanel = new JPanel(new GridLayout(1, 2));

            jPanel.add(new JLabel("id de l'entite"));
            jPanel.add(idTextField);

            workingPane.add(jPanel);
        }

        for (Field field : fields) {
            try {
                Object obj = FieldUtils.readField(field, e, true);
                if (field.getType().isAssignableFrom(EntityID.class))
                    continue;
                Component inputField = null;
                if (field.getType().isEnum()) {
                    JComboBox<Object> jcb = new JComboBox<>();
                    for (Object item : field.getType().getEnumConstants()) {
                        jcb.addItem(item);
                    }
                    inputField = jcb;
                } else if (field.getType().isAssignableFrom(Character.class)) {
                    inputField = new JTextField(obj.toString());
                } else if (field.getType().isAssignableFrom(String.class)
                        || Number.class.isAssignableFrom(field.getType())) {
                    inputField = new JTextField(obj.toString());
                } else {
                    EntityInputHelper eih = new EntityInputHelper(field, obj);
                    eih.addActionListener(this);
                    eih.setActionCommand("update");
                    inputField = eih;
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
            } catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        } 
    }

    public EntityBuilder(final JPanel workingPane, Class<?> typeToAdd) {

        this.typeToAdd = typeToAdd;
        workingPane.setBorder(BorderFactory.createTitledBorder("Saisie de : " + typeToAdd.getSimpleName()));
        List<Field> fields = Utils.extractFields(typeToAdd);
        workingPane.setLayout(new BoxLayout(workingPane, BoxLayout.Y_AXIS));
        if (typeToAdd.getCanonicalName().startsWith(EntityBuilder.ENTITIES_PACKAGES_PATH)) {
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
            } else if (field.getType().isAssignableFrom(Character.class)) {
                inputField = new JTextFieldLimit(1, "Saisir " + Utils.normalizeCamelCase(field.getName()) + " (type : "
                        + field.getType().getSimpleName() + ")");
            } else if (field.getType().isAssignableFrom(String.class)
                    || Number.class.isAssignableFrom(field.getType())) {
                inputField = new HintTextField("Saisir " + Utils.normalizeCamelCase(field.getName()) + " (type : "
                        + field.getType().getSimpleName() + ")");
            } else {
                EntityInputHelper eih = new EntityInputHelper(field);
                eih.addActionListener(this);
                inputField = eih;
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
                } else if (pair.getLeft().isAssignableFrom(Character.class)) {
                    args[i] = (Character) ((JTextField) pair.getRight()).getText().charAt(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ConstructorUtils.invokeConstructor(typeToAdd, args);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand()=="update")
        {
            ((EntityInputHelper) e.getSource()).getEntityUpdate();
        }
        else
        ((EntityInputHelper) e.getSource()).getEntity();
    }

}