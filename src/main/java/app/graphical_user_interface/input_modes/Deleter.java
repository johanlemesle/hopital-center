package app.graphical_user_interface.input_modes;

import java.awt.GridLayout;
import java.awt.LayoutManager;
//import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;

import app.App;
import app.Hopital;
import app.database_manager.Utils;
import app.graphical_user_interface.Window;

/**
 * Deleter
 */
public class Deleter implements ActionListener {

    // private Window parentWindow;
    // public HashMap<String, Object> registre;
    // public List<Object> target=new ArrayList<>();

    public Class<?> target;
    private HashMap<?, ?> laMap;
    private Class<?> courant;
    private List<Pair<String, Class<?>>> lpsc = new ArrayList<Pair<String, Class<?>>>();

    public Deleter(final JPanel jp) {
        // registre = null;
        jp.setBorder(BorderFactory.createTitledBorder("Dans quelle table souhaitez-vous Supprimer?"));
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        ButtonGroup optionsGroup = new ButtonGroup();
        for (Field field : Utils.extractFields(Hopital.class)) {
            // registre.put(field.getName(), field);
            final JRadioButton rb = new JRadioButton(field.getName());
            rb.setActionCommand(field.getName());
            optionsGroup.add(rb);
            jp.add(rb);
            rb.addActionListener(this);
            courant = Utils.getTypeFromMap(field);
            Pair<String, Class<?>> p = Pair.of(field.getName(), courant);
            lpsc.add(p);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        try {
            laMap = (HashMap<?, ?>) FieldUtils.readField(App.hopital, s, true);
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // System.out.println("YY : " + c.getName());

        // target = FieldUtils.readField(, s, true);

        // Field f = registre.get(s);
    }

    public HashMap<?, ?> getLaMap() {
        return laMap;
    }
}