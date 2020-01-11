package app.graphical_user_interface.input_modes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import app.database_manager.EntityID;
import app.database_manager.Utils;
import app.graphical_user_interface.helpers.InputHandler;

/**
 * Add
 */
public class Adder implements ActionListener {

    /**
     *
     */

    private List<InputHandler> inputHandlers = new ArrayList<>();
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
        List<Field> fieldsNames = Utils.extractFieldNames(typeToAdd);
        workingPane.setLayout(new GridLayout(5, fieldsNames.size() / 5));
        for (final Field field : fieldsNames) {
            if (!field.getType().isAssignableFrom(EntityID.class)) {
                InputHandler ihHandler = new InputHandler(field);
                inputHandlers.add(ihHandler);
                workingPane.add(ihHandler);
            }
        }
    }

    public Object buildEntity()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object args[] = new Object[inputHandlers.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = inputHandlers.get(i).retrieveInputFieldContents();
        }
        return ConstructorUtils.invokeConstructor(typeToAdd, args);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}