package app.graphical_user_interface;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JPanel;

import app.database_manager.Utils;

/**
 * InputDigester
 */
public class InputDigestor extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4206706321166972386L;

    public static final Class<?>[] entities;
    static {
        String entitiesPackagePath = "app.entities";
        Class<?>[] tmp = null;
        try {
            tmp = Utils.getClasses(entitiesPackagePath);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        entities = tmp;
    }

    public void addMode() {
        String possibleValues[] = new String[entities.length];
        HashMap<String, Class<?>> possibleVMap = new HashMap<>();
        int i = 0;
        for (Class<?> cl : entities) {
            possibleValues[i] = cl.getSimpleName();
            possibleVMap.put(possibleValues[i], cl);
            i++;
        }
        String value = ListDialog.showDialog(this, this, "Selectionnez le type d'entité que vous souhaitez ajouter",
                "Selection entité", possibleValues, null, null);

    }
}