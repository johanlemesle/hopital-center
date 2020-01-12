package app.graphical_user_interface.helpers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang3.reflect.FieldUtils;

import app.database_manager.Utils;

/**
 * Toto
 */
public class Toto extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -405109346423065534L;

    private JCheckBox parent;
    private Class<?> type;
    private String name;

    private List<Toto> children = new ArrayList<>();

    public Toto(JCheckBox parent, Class<?> type, String name) {
        this.parent = parent;
        this.type = type;
        this.name = name;

        final JLabel jl2 = new JLabel(this.name);
        final JPanel jPanel2 = new JPanel(new GridLayout(1, 2));
        jPanel2.add(jl2);
        jPanel2.add(parent);
        this.parent.addActionListener(this);

        this.add(jPanel2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox cbx = (JCheckBox) e.getSource();

        System.out.println("je suis la");
        if (cbx.isSelected()) {
            for (Field f : Utils.extractFieldNames(type)) {
                Class<?> cls;
                String name = f.getName();
                if (f.getType().isAssignableFrom(HashMap.class)) {
                    ParameterizedType pt = (ParameterizedType) f.getGenericType();
                    cls = (Class<?>) pt.getActualTypeArguments()[1];
                } else {
                    cls = f.getType();
                }
                Toto t = new Toto(new JCheckBox(), cls, name);
                children.add(t);
                this.add(t);
            }
        } else {
        }
    }

}