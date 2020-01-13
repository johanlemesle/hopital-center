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
import app.graphical_user_interface.helpers.Query;

/**
 * Modifier
 */
public class Modifier implements ActionListener {

    // private Window parentWindow;
    // public HashMap<String, Object> registre;
    // public List<Object> target=new ArrayList<>();

    public Class<?> target;
    private HashMap<?, ?> laMap;
    private Class<?> courant;
    private List<Pair<String, Class<?>>> lpsc = new ArrayList<Pair<String, Class<?>>>();

    public Modifier(final JPanel jp) {
        // registre = null;
        jp.setLayout((LayoutManager) new BoxLayout(jp, BoxLayout.X_AXIS));
        ButtonGroup optionsGroup = new ButtonGroup();
        for (Field field : FieldUtils.getAllFields(Hopital.class)) {
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
        System.out.println("XX : " + s);
        for (int i = 0; i < lpsc.size(); i++) {
            if (lpsc.get(i).getLeft() == s) {
                System.out.println("YY : " + lpsc.get(i).getRight().getName());
                target = lpsc.get(i).getRight();

            }
        }
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