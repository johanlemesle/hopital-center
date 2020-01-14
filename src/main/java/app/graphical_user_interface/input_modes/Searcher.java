package app.graphical_user_interface.input_modes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Hopital;
import app.database_manager.Utils;
import app.graphical_user_interface.helpers.RechercheChoix;

/**
 * Searcher
 */
public class Searcher {

    // private HashMap<JLabel, Class<?>> hash_lab = new HashMap<>();
    private List<RechercheChoix> lesTotos = new ArrayList<>();

    public Searcher(final JPanel workingPane) {
        workingPane.setLayout(new BoxLayout(workingPane, BoxLayout.Y_AXIS));
        for (Field field : Utils.extractFields(Hopital.class)) {
            Class<?> cls;
            cls = Utils.getTypeFromMap(field);
            RechercheChoix leToto = new RechercheChoix(new JCheckBox(), cls, field.getName());
            leToto.setVisible(true);
            workingPane.add(leToto);
            lesTotos.add(leToto);
        }

    }

    public String parse() {
        List<String> lesRequetes = new ArrayList<>();
        for (RechercheChoix toto : lesTotos) {
            if (toto.isSelected())
                lesRequetes.add(toto.buildQuery());
        }
        return String.join("&", lesRequetes);
    }

}